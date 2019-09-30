package model.repository.DAO;

import model.repository.entities.EntityInt;
import model.repository.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class UsersDao extends BasicDao {

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

    public static List<UserEntity> findAll() throws Exception{
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            UserEntity entity = new UserEntity();
            Query query = entity.createFindAll(em);
            List<UserEntity> resultList = query.getResultList();
            em.getTransaction().commit();
            return resultList;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception(e);
        }
    }
}
