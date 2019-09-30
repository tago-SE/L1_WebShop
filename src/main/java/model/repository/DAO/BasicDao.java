package model.repository.DAO;

import model.repository.entities.CategoryEntity;
import model.repository.entities.EntityInt;

import javax.persistence.*;
import java.util.List;

/**
 * Abstract class containing basic CRUD operators and some queries
 */
public abstract class BasicDao {

    private static final String PERSISTENCE_UNIT_NAME = "org.hibernate.lab1_web_shop.jpa";

    public static EntityManagerFactory getEntityManagerFactory() {
        return  Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static EntityInt insert(EntityInt entity) throws Exception {
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
            } else {
                // There is no uniqueness filter so we insert it as is
                em.persist(entity);
            }
            em.getTransaction().commit();
            // Returns the persistent entity along with any database modified attributes
            return entity;
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
            Query query = entity.createFindByIdQuery(em);
            if (query != null) {
                List<EntityInt> resultList = query.getResultList();
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

    public static EntityInt update(EntityInt entity) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = entity.createFindByIdQuery(em);
            if (query != null) {
                List<EntityInt> resultList = query.getResultList();
                if (resultList.size() == 1) {
                    EntityInt persistentEntity = resultList.get(0);
                    // Confirms that the update version is not out of date
                    if (persistentEntity.getVersion() == entity.getVersion()) {
                        // Transfer entity data to the persistent entity
                        entity.transferTo(persistentEntity);
                        em.getTransaction().commit();
                        return persistentEntity;
                    }
                }
            }
            em.getTransaction().commit();
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    /**
     * Takes a token entity object and performs a query based on the id of that entity.
     * @param entity, Token entity
     * @return found persistent entity
     * @throws Exception
     */
    public static EntityInt findById(EntityInt entity) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = entity.createFindByIdQuery(em);
            List<CategoryEntity> resultList = query.getResultList();
            if (resultList.size() > 0) {
                em.getTransaction().commit();
                return resultList.get(0);
            }
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }
}
