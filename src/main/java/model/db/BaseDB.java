package model.db;

import model.db.entities.EntityInt;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class BaseDB<E> {

    private static final String PERSISTENCE_UNIT_NAME = "org.hibernate.lab1_web_shop.jpa";

    public static EntityManagerFactory getEntityManagerFactory() {
        return  Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    /*
    public static boolean insert(UserEntity newUser) throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<UserEntity> users = (List<UserEntity>) em.createNamedQuery("User.findByName")
                    .setParameter("name", newUser.name)
                    .getResultList();
            if (users.size() == 0) {
                em.persist(newUser);
            } else {
                newUser = null;
            }
            em.getTransaction().commit();
            return newUser != null;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    public static void insert(EntityInt entity) {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {

        } catch (Exception e) {
            em.getTransaction().begin();
        }
    }

    public void update(EntityInt entity) {

    }

    public void delete(EntityInt entity) {

    }
   */
}
