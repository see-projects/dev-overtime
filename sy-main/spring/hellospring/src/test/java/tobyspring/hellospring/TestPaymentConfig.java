package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.clock.ClockProvider;
import tobyspring.hellospring.payment.ExRateProviderInterface;
import tobyspring.hellospring.payment.ExRateProviderStub;
import tobyspring.hellospring.payment.PaymentService;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class TestPaymentConfig {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(provider(), clockProvider());
    }

    @Bean
    public ExRateProviderInterface provider() {
        return new ExRateProviderStub(BigDecimal.valueOf(1_000));
    }

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Bean
    public ClockProvider clockProvider() {
        return new ClockProvider(clock());
    }
}
