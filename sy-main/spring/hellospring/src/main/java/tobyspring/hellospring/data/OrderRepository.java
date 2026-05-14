package tobyspring.hellospring.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import tobyspring.hellospring.order.Order;

import java.math.BigDecimal;

public class OrderRepository {
    private EntityManagerFactory emf;

    public OrderRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Order order) {
        // em
        EntityManager em = emf.createEntityManager();

        // transaction
        // transaction-start
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
        //em.persist
        em.persist(order);
        em.flush();
        System.out.println(order);
        transaction.commit();

        } catch (RuntimeException e) {
            if(transaction.isActive()) transaction.rollback();
            throw e;
        } finally {
            // transaction-close
            if (em.isOpen()) em.close();
        }
    }
}
