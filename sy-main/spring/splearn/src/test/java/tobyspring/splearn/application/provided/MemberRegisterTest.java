package tobyspring.splearn.application.provided;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.domain.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Import(SplearnTestConfiguration.class)
@Transactional
public record MemberRegisterTest(MemberRegister memberRegister) {
//    @Autowired
//    private MemberRegister register;

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(Status.PENDING);
    }

    @Test
    void duplicateEmail() {
        memberRegister.register(MemberFixture.createMemberRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRequest())).isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void memberRegisterRequestFail() {
        extracted(new MemberRegisterRequest("toby@splearn.app", "Goby", "secret"));
        extracted(new MemberRegisterRequest("toby@splearn.app", "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG"));
        extracted(new MemberRegisterRequest("toby@splearn.app", "GGobyG", "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG"));
    }

    private void extracted(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void memberRegisterRequestSuccess() {
        var member = new MemberRegisterRequest("toby@splearn.app", "Goby123", "secret");
        Member member1 = memberRegister.register(member);
        assertThat(member1.getId()).isNotNull();
    }

}
