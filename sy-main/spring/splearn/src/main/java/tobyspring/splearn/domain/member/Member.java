package tobyspring.splearn.domain.member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import tobyspring.splearn.domain.AbstractEntity;
import tobyspring.splearn.domain.shared.Email;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache
public class Member extends AbstractEntity {
    @NaturalId
    private Email email;

//    @Basic(optional = false)
    private String nickname;

    private String passwordHash;

    private Status status;

    // 케스케이드를 통해서 Member에 대한 작업 시 MemberDetail에도 항상 적용되도록
    @OneToOne(cascade = CascadeType.ALL)
    private MemberDetail memberDetail;

    public static Member register(MemberRegisterRequest memberCreateRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.email = new Email(memberCreateRequest.email());
        member.nickname = requireNonNull(memberCreateRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(memberCreateRequest.password()));
        member.status = Status.PENDING;
        member.memberDetail = MemberDetail.create();

        return member;
    }

    public void active() {
        state(this.status == Status.PENDING, "대기 상태가 아닙니다");

        this.status = Status.ACTIVE;
        this.memberDetail.setActivatedAt();
    }

    public void deactive() {
        state(this.status == Status.ACTIVE, "Member is already deactivated");

        this.status = Status.DEACTIVATED;
        this.memberDetail.deactivate();
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void updateInfo(MemberInfoUpdateRequest updateRequest) {
        this.nickname = Objects.requireNonNull(updateRequest.nickname());

        this.memberDetail.updateInfo(updateRequest);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }


}
