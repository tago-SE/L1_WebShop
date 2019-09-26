package db;

import db.entities.UserEntity;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UsersDaoTest {

    @Test
    public void insert() {
        String random = Math.random() + "";
        UsersService.register(random, random);
    }
}