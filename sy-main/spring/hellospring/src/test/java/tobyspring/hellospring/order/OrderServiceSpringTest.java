package tobyspring.hellospring.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.OrderConfig;
import tobyspring.hellospring.TestPaymentConfig;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
public class OrderServiceSpringTest {
    @Autowired
    OrderService orderService;

    @Autowired
    DataSource dataSource;

    @Test
    void createOrder() {
      var order = orderService.createOrder("0100", BigDecimal.TEN);

        Assertions.assertThat(order.getId()).isGreaterThan(0);
    }

    @Test
    void createOrders() {
       List<OrderReq> orderReqs = List.of(new OrderReq("0400", BigDecimal.TEN), new OrderReq("0500", BigDecimal.TEN));

        var orders = orderService.createOrders(orderReqs);

        Assertions.assertThat(orders).hasSize(2);
        orders.forEach(order -> {
            Assertions.assertThat(order.getId()).isGreaterThan(0);
        });
    }

    @Test
    void createDuplicatedOrders() {
        List<OrderReq> orderReqs = List.of(new OrderReq("0300", BigDecimal.ONE), new OrderReq("0300", BigDecimal.TEN));

        Assertions.assertThatThrownBy(() -> orderService.createOrders(orderReqs)).isInstanceOf(DataIntegrityViolationException.class);
        JdbcClient client = JdbcClient.create(dataSource);
        var count = client.sql("select count(*) from orders where no = '0300'").query(Long.class).single();
        Assertions.assertThat(count).isEqualTo(0);
    }
}
