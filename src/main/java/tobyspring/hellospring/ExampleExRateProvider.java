package tobyspring.hellospring;

import java.math.BigDecimal;

public class ExampleExRateProvider{

     BigDecimal getExRate(String currency) {

        if(currency.equals("USD")) return BigDecimal.valueOf(1100);
        return null;
    }
}
