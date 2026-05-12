package tobyspring.hellospring.exrate;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.payment.ExRateProviderInterface;

import java.math.BigDecimal;

@Component
public class RestTemplateExRateProvider implements ExRateProviderInterface {

    private final RestTemplate restTemplate;

    public RestTemplateExRateProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return restTemplate.getForObject(url, ExRateData.class).rates().get("KRW");
    }
}
