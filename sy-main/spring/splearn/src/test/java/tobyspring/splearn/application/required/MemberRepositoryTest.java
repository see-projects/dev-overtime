package tobyspring.splearn.application.required;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberFixture;
import tobyspring.splearn.domain.MemberRegisterRequest;
import tobyspring.splearn.domain.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static tobyspring.splearn.domain.MemberFixture.createMemberRequest;
import static tobyspring.splearn.domain.MemberFixture.createPasswordEncoder;

@DataJpaTest
class MemberRepositoryTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

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