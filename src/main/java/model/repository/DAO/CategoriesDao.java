package model.repository.DAO;

import model.repository.entities.CategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class CategoriesDao extends BasicDao {

    private static final String QUERY_FIND_ALL = "Category.findAll";

    public static CategoryEntity findById(int id) throws Exception {
        return (CategoryEntity) findById(new CategoryEntity(id));
    }

    public static boolean delete(int id) throws Exception {
        return delete(new CategoryEntity(id));
    }

    public static List<CategoryEntity> findAll() throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<CategoryEntity> resultList = em.createNamedQuery(QUERY_FIND_ALL).getResultList();
            em.getTransaction().commit();
            return resultList;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }
}
