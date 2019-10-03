package model.repository.dao;

import model.handlers.exceptions.DatabaseException;
import model.repository.entities.ItemEntity;
import model.repository.entities.OrderEntity;
import model.repository.entities.OrderItemEntity;
import model.repository.entities.UserEntity;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class OrderItemEntityTest {

    //@Test
    public void testItemOrderEntity() throws Exception {
        EntityManagerFactory factory = BasicDao.getEntityManagerFactory();
        EntityManager em = factory.createEntityManager();

        String itemName = Math.random() + "";
        ItemEntity item1 = new ItemEntity(itemName, 0, 0);
        item1 = ItemsDao.insert(item1);
        ItemEntity item2 = new ItemEntity(itemName + "__2", 0, 0);
        item2 = ItemsDao.insert(item2);

        String uname = "" + Math.random();
        UserEntity user = new UserEntity(uname, uname);
        user = (UserEntity) UsersDao.insert(user);


        em.getTransaction().begin();
        OrderEntity order = new OrderEntity(user);

        OrderItemEntity oi1 = new OrderItemEntity(item1, order, 1, 55);
        OrderItemEntity oi2 = new OrderItemEntity(item2, order, 2, 77);
        em.persist(order);
        em.persist(oi1);
        em.persist(oi2);
        em.getTransaction().commit();

        System.out.println(order.toString());
    }

    @Test
    public void testFindAll() throws DatabaseException {
        List<OrderEntity> orders = OrdersDao.findAll();
        for (int i = 0 ; i < 3 && i < orders.size(); i++)
            System.out.println(orders.get(i).toString());
    }
}
