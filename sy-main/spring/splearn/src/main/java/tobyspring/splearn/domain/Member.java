package tobyspring.splearn.domain;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Objects;

import static org.springframework.util.Assert.*;

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
        state(this.status == Status.PENDING, "대기 상태가 아닙니다");

        this.status = Status.ACTIVE;
    }

    public void deactive() {
        state(this.status == Status.ACTIVE, "Member is already deactivated");
            this.status = Status.DEACTIVATED;

    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(password);
    }


}
