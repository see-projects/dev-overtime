package tobyspring.hellospring;

import java.math.BigDecimal;

public class ExampleExRatePaymentService extends PaymentService{

    @Override
     BigDecimal getExRate(String currency) {

        if(currency.equals("USD")) return BigDecimal.valueOf(1100);
        return null;
    }
}
