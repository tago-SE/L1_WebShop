package model.repository.dao;

import model.handlers.exceptions.DatabaseException;
import model.repository.entities.ItemEntity;
import model.repository.entities.UserEntity;
import org.apache.log4j.Logger;
import utils.LogManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

import static utils.LogManager.getLogger;

public class UsersDao extends BasicDao {

    public static List<UserEntity> findAll() throws DatabaseException {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            List<UserEntity> found = em.createNamedQuery("User.findAll").getResultList();
            getLogger().info("users found: " + found.size());
            return found;
        } catch (Exception e) {
            getLogger().severe(e.getCause() + " :" + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            em.close();
        }
    }

    public static UserEntity findUserByCredentials(String username, String password) throws DatabaseException {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<UserEntity> resultList = em.createNamedQuery("User.validateCredentials")
                    .setParameter("name", username)
                    .setParameter("password", password)
                    .getResultList();
            em.getTransaction().commit();
            if (resultList.size() == 1) {
                getLogger().info("login successful: " + username);
                return resultList.get(0);
            }
            if (resultList.size() < 1) {
                getLogger().info("login failure: " + username);
                return null;
            }
            throw new IllegalStateException("Found {" + resultList.size() + "} matching credentials.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            getLogger().severe(e.getCause() + " :" + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            em.close();
        }
    }

}
