package model.repository.dao;

import model.repository.entities.ItemEntity;
import model.repository.entities.UserEntity;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UsersDaoTest {
    
    //@Test
    public void getUserByCredentials() {
    }

    //@Test
    public void insert() throws Exception {
        String name0 = "" + Math.random();
        // Should be able to insert a user with a unique name
        UserEntity user = new UserEntity( name0,  name0);
        UserEntity oldUser = user;
        assertTrue(UsersDao.insert(user) != null);
        // Should not be able to insert a user with the same name
        user = new UserEntity(name0,  name0);
        assertFalse(UsersDao.insert(user) != null);
        UsersDao.delete(oldUser);
    }

    //@Test
    public void findAll() throws Exception {
        String name1 = "" +  Math.random();
        String name2 = name1 + "asd";
        List<UserEntity> resultList = UsersDao.findAll();
        int count = resultList.size();
        UserEntity u1 = (UserEntity) UsersDao.insert(new UserEntity(name1, name1));
        UserEntity u2 = (UserEntity) UsersDao.insert(new UserEntity(name2, name2));
        resultList = UsersDao.findAll();
        assertEquals(count + 2, resultList.size());
        UsersDao.delete(u1);
        UsersDao.delete(u2);
    }

    //@Test
    public void delete() throws Exception {
        String name1 = "" +  Math.random();
        UserEntity u1 = (UserEntity) UsersDao.insert(new UserEntity(name1, name1));
        UserEntity toDelete = new UserEntity();
        toDelete.id = u1.id;
        UsersDao.delete(toDelete);
        assertNull(UsersDao.findUserByCredentials(name1, name1));
    }

    //@Test
    public void findById() throws Exception {
        String name1 = "" +  Math.random();
        UserEntity u1 = (UserEntity) UsersDao.insert(new UserEntity(name1, name1));
        UserEntity query = new UserEntity();
        query.id = u1.id;
        UserEntity found = (UserEntity) UsersDao.findById(query);
        assertNotNull(found);
        assertEquals(u1.id, found.id);
        assertEquals(u1.name, found.name);
        UsersDao.delete(found);
    }

    //@Test
    public void deleteChildEntities() throws Exception {
        String name1 = "" +  Math.random();
        UserEntity u = new UserEntity(name1, name1);
        u.addAccess("Admin");
        u.addAccess("Customer");
        u.addAccess("Worker");
        u = (UserEntity) UsersDao.insert(u);

        UserEntity toDelete = new UserEntity();
        toDelete.id = u.id;
        UsersDao.delete(u);
    }

    public void addItemToCart() throws Exception {
        String uname = "" + Math.random();
        UserEntity u1 = new UserEntity(uname, uname);
        UsersDao.insert(u1);

        String iname = "" + Math.random();
        ItemEntity i = new ItemEntity( iname, 0, 0);
        i = (ItemEntity) ItemsDao.insert(i);

        System.out.println("ITEM: " + i.toString());
        System.out.println("USER: " + u1.toString());

        System.out.println("DONE\n\n" + u1.toString());

    }

}