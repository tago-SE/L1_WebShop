package model.repository.dao;

import model.handlers.exceptions.DatabaseException;
import model.handlers.exceptions.InvalidRequestException;
import model.handlers.exceptions.OutOfStockException;
import model.repository.entities.*;
import org.hibernate.dialect.Database;
import view.viewmodels.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static utils.LogManager.getLogger;

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
            getLogger().info("order saved: " + order.id);
            em.getTransaction().commit();
            return order;
        } catch (Exception e) {
            em.getTransaction().rollback();
            getLogger().severe(e.getCause() + " :" + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            em.close();
        }
    }


    public static void deliverOrder(int orderId) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            OrderEntity order = em.find(OrderEntity.class, orderId);
            if (order.isDelivered())
                throw new InvalidRequestException("Already delivered");
            for (OrderItemEntity orderItem : order.orderItems) {
                if (orderItem.getItem().quantity >= orderItem.amount && orderItem.amount >= 0) {
                    orderItem.getItem().quantity -= orderItem.amount;
                } else {
                    throw new OutOfStockException("Item out of stock");
                }
            }
            order.deliver();
            getLogger().info("order delivered: " + orderId);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            getLogger().severe(e.getCause() + " :" + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    public static List<OrderEntity> findAll() throws DatabaseException {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            List<OrderEntity> found = em.createNamedQuery("Order.findAll").getResultList();
            getLogger().info("found: " + found.size());
            return found;
        } catch (Exception e) {
            getLogger().severe(e.getCause() + " :" + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            em.close();
        }
    }

}
