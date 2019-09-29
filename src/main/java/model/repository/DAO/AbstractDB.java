package model.repository.DAO;

import model.repository.entities.EntityInt;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public abstract class AbstractDB {

    private static final String PERSISTENCE_UNIT_NAME = "org.hibernate.lab1_web_shop.jpa";

    public static EntityManagerFactory getEntityManagerFactory() {
        return  Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    /*
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
     */

    public static boolean insert(EntityInt entity) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Query isUniqueQuery = entity.createVerifyIsUniqueQuery(em);
            if (isUniqueQuery != null) {
                List<EntityInt> resultList = isUniqueQuery.getResultList();
                if (resultList.size() == 0) {
                    em.persist(entity);
                } else {
                    entity = null;
                }
            }
            em.getTransaction().commit();
            return entity != null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    public static boolean delete(EntityInt entity) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Query isUniqueQuery = entity.createVerifyIsUniqueQuery(em);
            if (isUniqueQuery != null) {
                List<EntityInt> resultList = isUniqueQuery.getResultList();
                if (resultList.size() == 1) {
                    em.remove(resultList.get(0));
                } else {
                    entity = null;
                }
            } else {
                entity = null;
            }
            em.getTransaction().commit();
            return entity != null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

}
