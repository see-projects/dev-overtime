package tobyspring.splearn.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Member {

    private Email email;

    private String nickname;

    private String passwordHash;

    private Status status;

    public static Member create(MemberCreateRequest memberCreateRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.email = new Email(memberCreateRequest.email());
        member.nickname = Objects.requireNonNull(memberCreateRequest.nickname());
        member.passwordHash = Objects.requireNonNull(passwordEncoder.encode(memberCreateRequest.passwordHash()));
        member.status = Status.PENDING;

        return member;
    }

    public void active() {
        this.status = Status.ACTIVE;
    }

    public void deactive() {
        if(this.status == Status.ACTIVE) {
            this.status = Status.DEACTIVATED;
        }
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public String changePassword(String password, PasswordEncoder passwordEncoder) {
        return this.passwordHash = passwordEncoder.encode(password);
    }


}
