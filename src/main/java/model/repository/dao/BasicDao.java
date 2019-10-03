package model.repository.dao;

import model.handlers.exceptions.DatabaseException;
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
                    entity.beforeInsert(em);
                    em.persist(entity);
                } else {
                    entity = null;
                }
            } else {
                // There is no uniqueness filter so we insert it as is
                entity.beforeInsert(em);
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
        if (entity.getId() == 0)
            throw new IllegalArgumentException("Entity id must be defined: id=" + entity.getId());
        try {
            em.getTransaction().begin();
            EntityInt foundEntity = em.find(entity.getClass(), entity.getId());
            if (foundEntity != null) {
                foundEntity.beforeDelete(em);
                em.remove(foundEntity);
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

    public static EntityInt update(EntityInt entity) throws DatabaseException {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            if (entity.getId() == 0)
                throw new IllegalArgumentException("Entity id must be defined: id=" + entity.getId());
            em.getTransaction().begin();
            EntityInt persistentEntity = em.find(entity.getClass(), entity.getId());
            if (persistentEntity != null && persistentEntity.getVersion() == entity.getVersion()) {
                Query isUniqueQuery = entity.createVerifyIsUniqueQuery(em);
                boolean performUpdate = true;
                if (isUniqueQuery != null) {
                    List<EntityInt> resultList = isUniqueQuery.getResultList();
                    if ((resultList.size() == 1 && resultList.get(0).getId() != persistentEntity.getId()) || resultList.size() > 1) {
                        performUpdate = false;
                    }
                }
                if (performUpdate) {
                    persistentEntity.update(em, entity);
                    em.getTransaction().commit();
                    return persistentEntity;
                }
            }
            em.getTransaction().commit();
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DatabaseException(e);
        } finally {
            em.close();
        }
    }

    public static EntityInt findById(EntityInt entity) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            EntityInt foundEntity = em.find(entity.getClass(), entity.getId());
            em.getTransaction().commit();
            return foundEntity;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }


}
