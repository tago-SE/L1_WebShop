package model.repository.dao;


import model.repository.entities.ItemEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ItemsDao extends BasicDao {

    public static boolean delete(int id) throws Exception {
        return delete(new ItemEntity(id));
    }

    public static List<ItemEntity> findAll() throws Exception {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<ItemEntity> found = em.createNamedQuery("Item.findAll").getResultList();
            em.getTransaction().commit();
            return found;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            em.close();
        }
    }

}
