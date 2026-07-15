package tobyspring.splearn.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProfileTest {
    @Test
    void profile() {
        Profile profile = new Profile("qwe123");

        Assertions.assertThat(profile.address()).isEqualTo("qwe123");

    }

    @Test
    void profileFail() {
        Assertions.assertThatThrownBy(() -> new Profile("")).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new Profile("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        var profile = new Profile("qwe123");

        Assertions.assertThat(profile.url()).isEqualTo("@qwe123");
    }

}