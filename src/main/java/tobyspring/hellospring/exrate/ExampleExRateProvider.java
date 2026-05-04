package tobyspring.hellospring.exrate;

import java.io.IOException;
import java.math.BigDecimal;

public class ExampleExRateProvider implements ExRateProviderInterface{

    @Override
     public BigDecimal getExRate(String currency) throws IOException {

        if(currency.equals("USD")) return BigDecimal.valueOf(1100);
        return null;
    }
}
