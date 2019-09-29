package model.repository.DAO;

import model.repository.entities.EntityInt;
import model.repository.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

public class UsersDB extends AbstractDB {

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
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    /*
    public static boolean insert(EntityInt entity) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        UserEntity newUser = (UserEntity) entity;
        try {
            em.getTransaction().begin();

            Query query = newUser.createVerifyIsUniqueQuery(em);
            List<UserEntity> users = (List<UserEntity>) query.getResultList();
            if (users.size() == 0) {
                em.persist(newUser);
            } else {
                newUser = null;
            }
            em.getTransaction().commit();
            return newUser != null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }
    */

}
