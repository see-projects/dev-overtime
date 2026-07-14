package tobyspring.splearn.application.member.provided;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import tobyspring.splearn.application.member.MemberModifyService;
import tobyspring.splearn.application.member.required.EmailSender;
import tobyspring.splearn.application.member.required.MemberRepository;
import tobyspring.splearn.domain.shared.Email;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.domain.member.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class MemberRegisterManualTest {
    @Test
    void registertestStub() {
        MemberRegister register = new MemberModifyService(
                new MemberFinderStub(),
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

        MemberRegister register = new MemberModifyService(
                new MemberFinderStub(),
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

        MemberRegister register = new MemberModifyService(
                new MemberFinderStub(),
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

        @Override
        public Optional<Member> findByEmail(Email email) {
            return Optional.empty();
        }

        @Override
        public Optional<Member> findById(Long memberId) {
            return Optional.empty();
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

    static class MemberFinderStub implements MemberFinder {
        @Override
        public Member findById(Long memberId) {
            return null;
        }
    }
}
