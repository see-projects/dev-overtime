package tobyspring.hellospring;

public class ObjectFactory {

    public PaymentService paymentService() {
        return new PaymentService(provider());
    }

    public ExRateProviderInterface provider() {
        return new WebApiExRateProvider();
    }
}
