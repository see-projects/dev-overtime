package tobyspring.hellospring.payment;

import org.springframework.stereotype.Component;
import tobyspring.hellospring.exrate.ExRateProviderInterface;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
 public class PaymentService {

     private ExRateProviderInterface exRateProvider;

     public PaymentService(ExRateProviderInterface providerInterface) {
         this.exRateProvider = providerInterface;
     }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        // 환율 가져오기
        BigDecimal exRate = exRateProvider.getExRate(currency);

        // 금액 계산
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);

        // 유효 시간 계산
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }


}
