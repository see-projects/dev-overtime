package tobyspring.splearn.application.member.required;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.Profile;
import tobyspring.splearn.domain.shared.Email;

import java.util.Optional;

/**
 *
 */
public interface MemberRepository extends Repository<Member, Integer> {
    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);

    @Query("select m from Member m where m.memberDetail.profile = :profile")
    Optional<Member> findByProfile(Profile profile);
}
