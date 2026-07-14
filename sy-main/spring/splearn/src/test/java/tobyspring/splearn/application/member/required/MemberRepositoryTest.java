package tobyspring.splearn.application.member.required;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.member.MemberFixture.createMemberRequest;
import static tobyspring.splearn.domain.member.MemberFixture.createPasswordEncoder;

@DataJpaTest
class MemberRepositoryTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @Autowired
    tobyspring.splearn.application.member.required.MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = createPasswordEncoder();
        this.member = Member.register(createMemberRequest("toby@splearn.app"),  passwordEncoder);
    }

    @Test
    void createMember() {
        assertThat(member.getId()).isNull();

        Member member = memberRepository.save(this.member);

        assertThat(member.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        var found = memberRepository.findById(member.getId()).orElseThrow();
        assertThat(found.getMemberDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void duplicateEmailFail() {
//        memberRepository.save(this.member);

//        Member member = Member.register(createMemberRequest(), createPasswordEncoder());
//        memberRepository.save(member);

        memberRepository.save(member);

        Member member2 = Member.register(createMemberRequest(), createPasswordEncoder());
        assertThatThrownBy(() -> memberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}