package model.repository.dao;

import model.repository.entities.ItemEntity;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ItemsDaoTest {

    @Test
    public void findAll() throws Exception {
        List<ItemEntity> items = ItemsDao.findAll();
        System.out.println(items);
        System.out.println(items.size());
    }
}