package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import tobyspring.hellospring.api.*;
import tobyspring.hellospring.payment.ExRateProviderInterface;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class WebApiExRateProvider implements ExRateProviderInterface {
    // 재사용성 좋게 상위에 위치시키기(템플릿이라 변경 가능성이 적어 멀티 스레드 환경에서도 문제없이 동작할 확률이 높음)
    private final ApiTemplate apiTemplate = new ApiTemplate();

    public BigDecimal getExRate(String currency){
        String url = "https://open.er-api.com/v6/latest/" + currency;

        // 재사용 가능성이 없는 경우 해당 객체는 람다로 표현하는 것도 낫뱃?
        return apiTemplate.getExRate(url);
    }

}
