package tobyspring.hellospring.payment;

import org.springframework.stereotype.Component;
import tobyspring.hellospring.clock.ClockProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

@Component
 public class PaymentService {

     private ExRateProviderInterface exRateProvider;
     private ClockProvider clock;

     public PaymentService(ExRateProviderInterface providerInterface, ClockProvider clock) {
         this.exRateProvider = providerInterface;
         this.clock = clock;
     }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        // 환율 가져오기
        BigDecimal exRate = exRateProvider.getExRate(currency);

        // 금액 계산
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);

        // 유효 시간 계산
        LocalDateTime validUntil = LocalDateTime.now(clock.clock()).plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }


}
