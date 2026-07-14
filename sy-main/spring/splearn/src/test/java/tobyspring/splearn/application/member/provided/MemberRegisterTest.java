package tobyspring.splearn.application.member.provided;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.domain.member.*;

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
    void deactivate() {
        Member member = memberRegister.register(MemberFixture.createMemberRequest());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());

        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(Status.DEACTIVATED);
        assertThat(member.getMemberDetail().getDeactivatedAt()).isNotNull();
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

    @Test
    void updateInfo() {
        Member member = memberRegister.register(MemberFixture.createMemberRequest());

        assertThat(member.getId()).isNotNull();

        memberRegister.activate((member.getId()));

        var memberInfoUpdateRequest = new MemberInfoUpdateRequest("toby123", "qwe123", "it's me Mario");
        Member member1 = memberRegister.updateInfo(member.getId(), memberInfoUpdateRequest);

        assertThat(member1.getNickname()).isEqualTo("toby123");
        assertThat(member1.getMemberDetail().getProfile().address()).isEqualTo("qwe123");
        assertThat(member1.getMemberDetail().getIntroduction()).isEqualTo("it's me Mario");

    }

}
