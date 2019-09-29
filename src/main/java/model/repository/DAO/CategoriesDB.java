package model.repository.DAO;

import model.repository.entities.CategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.List;

public class CategoriesDB extends AbstractDB {

    private static final String QUERY_FIND_ALL = "Category.findAll";
    private static final String QUERY_FIND_BY_NAME = "Category.findByName";
    private static final String QUERY_FIND_BY_ID = "Category.findById";
    private static final String QUERY_DELETE_BY_ID = "Category.deleteById";

    /*
    public static boolean insert(CategoryEntity entity) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<CategoryEntity> resultList = em.createNamedQuery(QUERY_FIND_BY_NAME)
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
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }
    */

    /*
    public static boolean delete(int id)  throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            int result = em.createNamedQuery(QUERY_DELETE_BY_ID).setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            return result == 1; // success
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

     */

    public static List<CategoryEntity> findAll() throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            return (List<CategoryEntity>) em.createNamedQuery(QUERY_FIND_ALL).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    public static boolean update(CategoryEntity entity) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<CategoryEntity> resultList =
                    em.createNamedQuery(QUERY_FIND_BY_ID).setParameter("id", entity.id).getResultList();
            if (resultList.size() == 1) {
                CategoryEntity persistentEntity = resultList.get(0);
                // Verify timestamp of the persistent entity is older than the session timestamp
               if (entity.version == persistentEntity.version) {
                    persistentEntity.name = entity.name;
                    em.getTransaction().commit();
                    return true;
                }
                em.getTransaction().rollback();
                return false;
            }
            throw new IllegalStateException(CategoryEntity.class.getName() + " no longer exists.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception(e);
        }
    }
}
