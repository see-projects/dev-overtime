package tobyspring.hellospring.data;

import jakarta.persistence.EntityManager;
import tobyspring.hellospring.order.Order;

public class OrderRepository {

    public void save(Order order) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

    }
}
