package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestPaymentConfig;
import tobyspring.hellospring.clock.ClockProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPaymentConfig.class)
class PaymentServiceSpringTest {

    @Autowired PaymentService paymentService;
//    @Autowired ExRateProviderInterface exRateProviderStub;
    @Autowired ExRateProviderStub exRateProviderStub;
    @Autowired
    ClockProvider clockProvider;

    @Test
    @DisplayName("prepare 메서드가 요구사항 3가지를 잘 충족하는 지 검증")
    void convertedAmount() {
        // exRate : 1000
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // 환율 정보

        Assertions.assertThat(payment.getExRate()).isEqualByComparingTo(BigDecimal.valueOf(1_000));

        // 원화환산금액 계산

        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(10_000));

        System.out.println("prepare 실행");
    }

    @Test
    @DisplayName("prepare 메서드의 유효시간 검증")
    void validUntil() {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(BigDecimal.valueOf(1_000)), clockProvider);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        LocalDateTime now = LocalDateTime.now(clockProvider.clock());
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        Assertions.assertThat(expectedValidUntil).isEqualTo(payment.getValidUntil());
    }

//    @Test
//    @DisplayName("prepare 메서드가 요구사항 3가지를 잘 충족하는 지 검증 2번째")
//    void prepare2() throws IOException {
//        // exRate : 500
//
//        exRateProviderStub.setExRate(BigDecimal.valueOf(500));
//        Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);
//
//        // 환율 정보
//
//        Assertions.assertThat(payment2.getExRate()).isEqualByComparingTo(BigDecimal.valueOf(500));
//
//        // 원화환산금액 계산
//
//        Assertions.assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(5_000));
//
//        System.out.println("prepare2 실행");
//    }
}