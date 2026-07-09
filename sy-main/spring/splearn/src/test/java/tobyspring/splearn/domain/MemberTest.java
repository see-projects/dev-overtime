package tobyspring.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
        member = Member.create(new MemberCreateRequest("toby@splearn.app", "nick", "secret"), passwordEncoder);
    }

    @Test
    public void createMember() {
        assertThat(member.getNickname()).isEqualTo("nick");
    }

    @Test
    public void createMemberfail() {
        assertThatThrownBy(() -> {
            Member.create(new MemberCreateRequest("qwe123@splearn.app", null, "secret"), passwordEncoder);
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
                Member.create(new MemberCreateRequest("invalid email", "Toby", "secret"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.create(new MemberCreateRequest("tobyilee@gmail.com", "Toby", "secret"), passwordEncoder);
    }
}
