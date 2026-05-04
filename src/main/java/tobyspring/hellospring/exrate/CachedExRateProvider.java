package tobyspring.hellospring.exrate;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@Primary
public class CachedExRateProvider implements ExRateProviderInterface{

    private ExRateProviderInterface exRateProvider;

    private BigDecimal cachedExRate;
    private LocalDateTime cachedExpiryTime;

    public CachedExRateProvider( @Qualifier("webApiExRateProvider")ExRateProviderInterface exRateProvider) {
        this.exRateProvider = exRateProvider;
    }

    @Override
    public BigDecimal getExRate(String currency) throws IOException {
        if(cachedExRate == null || cachedExpiryTime.isBefore(LocalDateTime.now())) {
            cachedExRate = exRateProvider.getExRate(currency);
            cachedExpiryTime = LocalDateTime.now().plusSeconds(3);

            System.out.println("cachedExRate Update");
        }
        return cachedExRate;
    }
}
