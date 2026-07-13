package tobyspring.splearn.application.provided;

import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import tobyspring.splearn.application.MemberService;
import tobyspring.splearn.application.required.EmailSender;
import tobyspring.splearn.application.required.MemberRepository;
import tobyspring.splearn.domain.Email;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberFixture;
import tobyspring.splearn.domain.Status;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class MemberRegisterTest {
    @Test
    void registertestStub() {
        MemberRegister register = new MemberService(
                new MemberRegisterStub(),
                MemberFixture.createPasswordEncoder(),
                new EmailSenderStub()
        );

        Member member = register.register(MemberFixture.createMemberRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(Status.PENDING);
    }

    @Test
    void registertestMock() {
        EmailSenderMock emailSenderMock = new EmailSenderMock();

        MemberRegister register = new MemberService(
                new MemberRegisterStub(),
                MemberFixture.createPasswordEncoder(),
                emailSenderMock
        );

        Member member = register.register(MemberFixture.createMemberRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(Status.PENDING);

        assertThat(emailSenderMock.getTos()).hasSize(1);
        assertThat(emailSenderMock.getTos().getFirst()).isEqualTo(member.getEmail());
    }

    @Test
    void registertestMockito() {
        EmailSender emailSenderMock = Mockito.mock(EmailSender.class);

        MemberRegister register = new MemberService(
                new MemberRegisterStub(),
                MemberFixture.createPasswordEncoder(),
                emailSenderMock
        );

        Member member = register.register(MemberFixture.createMemberRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(Status.PENDING);

        Mockito.verify(emailSenderMock).send(eq(member.getEmail()), any(), any());
    }

    static class MemberRegisterStub implements MemberRepository {
        @Override
        public Member save(Member member) {
            ReflectionTestUtils.setField(member, "id", 1L);
            return member;
        }
    }

    static class EmailSenderStub implements EmailSender {
        @Override
        public void send(Email email, String subject, String body) {

        }
    }

    @Getter
    static class EmailSenderMock implements EmailSender {
        List<Email> tos = new ArrayList<>();

        @Override
        public void send(Email email, String subject, String body) {
            tos.add(email);
        }
    }
}
