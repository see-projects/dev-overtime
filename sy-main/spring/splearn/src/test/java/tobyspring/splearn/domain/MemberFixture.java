package tobyspring.splearn.domain;

import org.jspecify.annotations.NonNull;

public class MemberFixture {
    public static @NonNull MemberRegisterRequest createMemberRequest() {
        return createMemberRequest("toby@splearn.app");
    }

    public static MemberRegisterRequest createMemberRequest(String email) {
        return new MemberRegisterRequest(email, "nick", "secret");
    }

    public static @NonNull PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }
}
