package tobyspring.hellospring.order;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    // 해당 리포지토리가 JPA 기술에 의존
    private OrderRepository orderRepository;


    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(String no, BigDecimal total) {
        Order order = new Order(no, total);

        this.orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> createOrders(List<OrderReq> reqs) {
        return reqs.stream().map(orderReq ->
                createOrder(orderReq.no(), orderReq.total())
        ).toList();
    }
}
