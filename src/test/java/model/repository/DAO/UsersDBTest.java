package model.repository.DAO;

import model.repository.entities.CategoryEntity;
import model.repository.entities.UserEntity;
import org.junit.After;
import org.junit.Test;

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
        System.out.println(getClass().getName() + ": insert: " + user.toString());
        assertTrue(UsersDB.insert(user));
        // Should not be able to insert a user with the same name
        user = new UserEntity(name0,  name0);
        assertFalse(UsersDB.insert(user));


    }

}