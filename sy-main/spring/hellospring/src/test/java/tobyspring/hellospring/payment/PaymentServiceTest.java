package tobyspring.hellospring.payment;

import jakarta.annotation.Nonnull;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.clock.ClockProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

class PaymentServiceTest {

    @Test
    @DisplayName("prepare 메서드가 요구사항 3가지를 잘 충족하는 지 검증")
    void prepare() throws IOException {

        ClockProvider clockProvider = new ClockProvider(Clock.fixed(Instant.now(), ZoneId.systemDefault()));

        getPayment(BigDecimal.valueOf(500), BigDecimal.valueOf(5000), clockProvider);
        getPayment(BigDecimal.valueOf(1000), BigDecimal.valueOf(10000), clockProvider);
        getPayment(BigDecimal.valueOf(3000), BigDecimal.valueOf(30000), clockProvider);

    }

    @Test
    void validUntil() throws IOException {
        ClockProvider clockProvider = new ClockProvider(Clock.fixed(Instant.now(), ZoneId.systemDefault()));

        PaymentService paymentService = new PaymentService(new ExRateProviderStub(BigDecimal.valueOf(1_000)), clockProvider);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        LocalDateTime now = LocalDateTime.now(clockProvider.clock());
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);

    }


    private static void getPayment(BigDecimal exRate, BigDecimal convertedAmount, ClockProvider clock) throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // 환율 정보

        Assertions.assertThat(payment.getExRate()).isEqualByComparingTo(exRate);

        // 원화환산금액 계산

        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
    }
}