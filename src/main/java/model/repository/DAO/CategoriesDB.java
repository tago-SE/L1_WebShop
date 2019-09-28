package model.repository.DAO;

import model.repository.entities.CategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.List;

public class CategoriesDB extends AbstractDB {

    public static boolean insert(CategoryEntity entity) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<CategoryEntity> resultList = em.createNamedQuery("Category.findByName")
                    .setParameter("name", entity.name)
                    .getResultList();
            if (resultList.size() == 0) {
                em.persist(entity);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().commit();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    public static boolean update(CategoryEntity updateEntity, Date sessionTimestamp) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<CategoryEntity> resultList =
                    em.createNamedQuery("Category.findById").setParameter("id", updateEntity.id).getResultList();
            if (resultList.size() == 1) {
                CategoryEntity persistentEntity = resultList.get(0);
                // Verify timestamp of the persistent entity is older than the session timestamp
                if (sessionTimestamp.after(persistentEntity.timestamp)) {
                    persistentEntity.timestamp = updateEntity.timestamp;
                    persistentEntity.name = updateEntity.name;
                    em.getTransaction().commit();
                    return true;
                }
                em.getTransaction().rollback();
                return false;
            }
            throw new IllegalStateException(CategoryEntity.class.getName() + " no longer exists.");
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new Exception(e);
        }
    }

    public static boolean delete(int id)  throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            int result = em.createNamedQuery("Category.deleteById").setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            return result == 1; // success
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }


    public static List<CategoryEntity> findAll() throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            return (List<CategoryEntity>) em.createNamedQuery("Category.findAll").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

}
