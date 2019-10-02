package model.repository.dao;

import model.repository.entities.ItemEntity;
import model.repository.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class UsersDao extends BasicDao {

    public static List<UserEntity> findAll() throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<UserEntity> found = em.createNamedQuery("User.findAll").getResultList();
            em.getTransaction().commit();
            return found;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    public static UserEntity findUserByCredentials(String username, String password) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<UserEntity> resultList = em.createNamedQuery("User.validateCredentials")
                    .setParameter("name", username)
                    .setParameter("password", password)
                    .getResultList();
            em.getTransaction().commit();
            if (resultList.size() == 1)
                return resultList.get(0);
            if (resultList.size() < 1)
                return null;
            throw new IllegalStateException("Found {" + resultList.size() + "} matching credentials.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    /*
    public static void addItemToCart(int userId, int itemId, int amount) {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        UserEntity user = em.find(UserEntity.class, userId);
        final ItemEntity item = em.find(ItemEntity.class, itemId);

        System.out.println("DATA\n\n" + user.toString() + "\n" + item.toString());

        boolean wasInCart = false;
        for (CartItemEntity cartItem : user.cart.cartItems) {
            if (cartItem.item.id == item.id) {
                wasInCart = true;
                cartItem.amount += amount;
                if (cartItem.amount < 0)
                    cartItem.amount = 0;
            }
        }
        if (!wasInCart) {
            CartItemEntity cartItem = new CartItemEntity(item, amount);
            em.persist(cartItem);
            user.cart.cartItems.add(cartItem);
        }

        em.getTransaction().commit();
    }
     */
}
