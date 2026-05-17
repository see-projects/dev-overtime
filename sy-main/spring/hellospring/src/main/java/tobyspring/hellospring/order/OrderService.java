package tobyspring.hellospring.order;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

@Service
public class OrderService {
    // 해당 리포지토리가 JPA 기술에 의존
    private OrderRepository orderRepository;
    // 트랜잭션도 JPA 기술에 의존
    private JpaTransactionManager transactionManager;


    public OrderService(OrderRepository orderRepository, JpaTransactionManager  transactionManager) {
        this.orderRepository = orderRepository;
        this.transactionManager = transactionManager;
    }

    public Order createOrder(String no, BigDecimal total) {
        Order order = new Order(no, total);

        return new TransactionTemplate(transactionManager).execute(status -> {

            this.orderRepository.save(order);
            return order;
        });
    }
}
