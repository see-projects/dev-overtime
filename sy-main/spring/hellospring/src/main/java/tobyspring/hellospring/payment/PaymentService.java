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

        return Payment.createPrepared(orderId, currency, foreignCurrencyAmount, exRate, LocalDateTime.now(clock.clock()));
    }


}
