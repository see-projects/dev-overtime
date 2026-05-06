package tobyspring.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;

public interface ExRateProviderInterface {

    BigDecimal getExRate(String currency) throws IOException;
}
