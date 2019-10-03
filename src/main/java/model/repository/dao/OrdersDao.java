package model.repository.dao;

import model.handlers.exceptions.DatabaseException;
import model.repository.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class OrdersDao extends BasicDao {

    public static OrderEntity insert(int userId, OrderEntity order) throws DatabaseException {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            UserEntity user = em.find(UserEntity.class, userId);
            if (user == null) {
                throw new DatabaseException("userId {" + userId + "} is not in use.");
            }
            order.setUser(user);
            em.persist(order);
            for (OrderItemEntity orderItem : order.orderItems) {
                em.persist(orderItem);
            }
            em.getTransaction().commit();
            return order;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DatabaseException(e);
        } finally {
            em.close();
        }
    }

    public static List<OrderEntity> findAll() throws DatabaseException {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<OrderEntity> found = em.createNamedQuery("Order.findAll").getResultList();
            em.getTransaction().commit();
            return found;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DatabaseException(e);
        } finally {
            em.close();
        }
    }

}
