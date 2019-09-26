package db;

import db.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersService extends BaseService {

    public static final int SUCCESS                 = 0;
    public static final int USER_NAME_TAKEN         = 1;
    public static final int INVALID_CREDENTIALS     = 1;
    public static final int TRANSACTION_FAILED      = -1;

    private static final Logger LOGGER = Logger.getLogger(UsersService.class.getName());

    public static int login(String username, String password) {
        if (username == null || password == null)
            throw new IllegalArgumentException(username + " || " + password);
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            try {
                UserEntity user = (UserEntity) em.createNamedQuery("User.validateCredentials").setParameter("name", username).setParameter("password", password).getSingleResult();
                em.getTransaction().commit();
                return SUCCESS;
            }  catch (NoResultException e) {

            }
            em.getTransaction().commit();
            return INVALID_CREDENTIALS;
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.log(Level.SEVERE, "" + e.getClass().getName() + ":" + e.getMessage());
            return TRANSACTION_FAILED;
        } finally {
            factory.close();
        }
    }

    public static int register(String username, String password) {
        if (username == null || password == null)
            throw new IllegalArgumentException(username + " || " + password);

        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<UserEntity> users = (List<UserEntity>) em.createNamedQuery("User.findByName").setParameter("name", username).getResultList();
            if (users.size() == 0) {
                em.persist(new UserEntity(username, password));
                em.getTransaction().commit();
                return SUCCESS;
            } else {
                return USER_NAME_TAKEN;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.log(Level.SEVERE, "" + e.getClass().getName());
            return TRANSACTION_FAILED;
        } finally {
            factory.close();
        }
    }

}
