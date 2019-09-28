package model.db;

import model.Result;
import model.db.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.logging.Logger;

public class UsersDB extends BaseDB {

    private static final Logger LOGGER = Logger.getLogger(UsersDB.class.getName());

    public static UserEntity getUserByCredentials(String username, String password) throws Exception {
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
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    public static boolean insert(UserEntity newUser) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<UserEntity> users = (List<UserEntity>) em.createNamedQuery("User.findByName")
                    .setParameter("name", newUser.name)
                    .getResultList();
            if (users.size() == 0) {
                em.persist(newUser);
            } else {
                newUser = null;
            }
            em.getTransaction().commit();
            return newUser != null;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

}
