package tobyspring.hellospring;

import java.io.IOException;
import java.math.BigDecimal;

public class Service {
    public static void main(String[] args) throws IOException {
        ObjectFactory factory = new ObjectFactory();
        PaymentService paymentService = factory.paymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}
