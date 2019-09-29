package model.repository.DAO;

import model.repository.entities.UserEntity;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UsersDBTest {
    
    @Test
    public void getUserByCredentials() {
    }

    @Test
    public void insert() throws Exception {
        String name0 = "" + Math.random();
        // Should be able to insert a user with a unique name
        UserEntity user = new UserEntity( name0,  name0);
        UserEntity oldUser = user;
        assertTrue(UsersDB.insert(user) != null);
        // Should not be able to insert a user with the same name
        user = new UserEntity(name0,  name0);
        assertFalse(UsersDB.insert(user) != null);
        UsersDB.delete(oldUser);
    }

    @Test
    public void findAll() throws Exception {
        String name1 = "" +  Math.random();
        String name2 = name1 + "asd";
        List<UserEntity> resultList = UsersDB.findAll();
        int count = resultList.size();
        UserEntity u1 = (UserEntity) UsersDB.insert(new UserEntity(name1, name1));
        UserEntity u2 = (UserEntity) UsersDB.insert(new UserEntity(name2, name2));
        resultList = UsersDB.findAll();
        assertEquals(count + 2, resultList.size());
        UsersDB.delete(u1);
        UsersDB.delete(u2);
    }

}