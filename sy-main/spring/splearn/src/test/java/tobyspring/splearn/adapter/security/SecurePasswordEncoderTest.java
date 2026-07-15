package tobyspring.splearn.adapter.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurePasswordEncoderTest {
    @Test
    void securePasswordEncoder() {
        SecurePasswordEncoder encoder = new SecurePasswordEncoder();

        String passwordHash = encoder.encode("password");
        assertTrue(encoder.matches("password", passwordHash));
        assertFalse(encoder.matches("password2", passwordHash));
    }

}