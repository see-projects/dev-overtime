package tobyspring.splearn.adapter.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;
import tobyspring.splearn.domain.Email;

import static org.junit.jupiter.api.Assertions.*;

class DummyEmailSenderTest {
    @Test
    @StdIo
    void sendEmail(StdOut out) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();

        dummyEmailSender.send(new Email("toby@splearn.app"), "subject", "body");

        Assertions.assertThat(out.capturedLines()[0]).isEqualTo("Sending email...");
    }

}