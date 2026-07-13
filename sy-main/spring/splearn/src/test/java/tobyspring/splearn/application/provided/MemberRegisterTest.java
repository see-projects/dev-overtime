package tobyspring.splearn.application.provided;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.domain.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(SplearnTestConfiguration.class)
@Transactional
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {
//    @Autowired
//    private MemberRegister register;

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(Status.PENDING);
    }

    @Test
    void activate() {
        Member member = memberRegister.register(MemberFixture.createMemberRequest());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());

        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(Status.ACTIVE);
    }

    @Test
    void duplicateEmail() {
        memberRegister.register(MemberFixture.createMemberRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRequest())).isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void memberRegisterRequestFail() {
        checkValidation(new MemberRegisterRequest("toby@splearn.app", "Goby", "secret"));
        checkValidation(new MemberRegisterRequest("toby@splearn.app", "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG"));
        checkValidation(new MemberRegisterRequest("toby@splearn.app", "GGobyG", "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG"));
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void memberRegisterRequestSuccess() {
        var member = new MemberRegisterRequest("toby@splearn.app", "Goby123", "secret");
        Member member1 = memberRegister.register(member);
        assertThat(member1.getId()).isNotNull();
    }

}
