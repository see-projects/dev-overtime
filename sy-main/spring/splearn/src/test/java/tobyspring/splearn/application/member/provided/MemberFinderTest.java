package tobyspring.splearn.application.member.provided;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberFinderTest(tobyspring.splearn.application.member.provided.MemberFinder finder, tobyspring.splearn.application.member.provided.MemberRegister memberRegister, EntityManager entityManager) {
    @Test
    void findById() {
        Member member = memberRegister.register(MemberFixture.createMemberRequest());
        entityManager.flush();
        entityManager.clear();

        Member found = finder.findById(member.getId());

        Assertions.assertThat(member.getId()).isEqualTo(found.getId());
    }

    @Test
    void findByIdFail() {
        Assertions.assertThatThrownBy(() -> {
            finder.findById(123456L);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}