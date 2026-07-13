package tobyspring.splearn.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache
public class Member extends AbstractEntity {
    @NaturalId
    private Email email;

//    @Basic(optional = false)
    private String nickname;

    private String passwordHash;

    private Status status;

    public static Member register(MemberRegisterRequest memberCreateRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.email = new Email(memberCreateRequest.email());
        member.nickname = requireNonNull(memberCreateRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(memberCreateRequest.password()));
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
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }


}
