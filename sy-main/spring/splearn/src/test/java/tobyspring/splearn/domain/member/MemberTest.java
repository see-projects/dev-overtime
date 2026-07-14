package tobyspring.splearn.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.member.MemberFixture.createMemberRequest;
import static tobyspring.splearn.domain.member.MemberFixture.createPasswordEncoder;

public class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = createPasswordEncoder();
        member = Member.register(createMemberRequest(), passwordEncoder);
    }


    @Test
    public void createMember() {
        assertThat(member.getNickname()).isEqualTo("nick123");
    }

    @Test
    public void createMemberfail() {
        assertThatThrownBy(() -> {
            Member.register(new MemberRegisterRequest("qwe123@splearn.app", null, "secret"), passwordEncoder);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void memberActive() {
        member.active();

        assertThat(member.getStatus()).isEqualTo(Status.ACTIVE);
    }

    @Test
    public void memberDeactivated() {
        member.active();

        member.deactive();
        assertThat(member.getStatus()).isEqualTo(Status.DEACTIVATED);
    }

    @Test
    public void memberPasswordCheck() {
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
    }

    @Test
    public void memberPasswordChange() {
        member.changePassword("pppp", passwordEncoder);
        assertThat(member.verifyPassword("pppp", passwordEncoder)).isTrue();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() ->
                Member.register(createMemberRequest("invalid email"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRequest(), passwordEncoder);
    }
}
