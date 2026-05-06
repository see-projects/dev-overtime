package tobyspring.hellospring.payment;

import jakarta.annotation.Nonnull;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

class PaymentServiceTest {

    @Test
    @DisplayName("prepare 메서드가 요구사항 3가지를 잘 충족하는 지 검증")
    void prepare() throws IOException {
     getPayment(BigDecimal.valueOf(500), BigDecimal.valueOf(5000));
 getPayment(BigDecimal.valueOf(1000), BigDecimal.valueOf(10000));
getPayment(BigDecimal.valueOf(3000), BigDecimal.valueOf(30000));



        // 원화환산금액의 유효시간 계산

//        Assertions.assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
//        Assertions.assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }

    @Nonnull
    private static void getPayment(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate));

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // 환율 정보

        Assertions.assertThat(payment.getExRate()).isEqualByComparingTo(exRate);

        // 원화환산금액 계산

        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
    }
}