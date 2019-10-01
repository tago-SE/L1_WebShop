package model.repository.dao;

import model.repository.entities.CategoryEntity;
import model.repository.entities.ItemEntity;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CategoriesDaoTest {

    // @Test
    public void insert() throws Exception {
        String name0 = "" + Math.random();
        // Should be able to insert a entity with a unique name
        CategoryEntity inserted = (CategoryEntity) CategoriesDao.insert(new CategoryEntity(name0));
        assertTrue(inserted != null);

        // Should not be able to insert a entity with the same name
        assertFalse(CategoriesDao.insert(new CategoryEntity(name0)) != null);

        CategoriesDao.delete(new CategoryEntity(inserted.id));
        // Should not be able to insert an entity with a id
        assertThrows(Exception.class, ()-> {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.name = "hello";
            categoryEntity.id = 5;
            CategoriesDao.insert(categoryEntity);
        });
    }

   // @Test
    public void update() throws Exception {
        String name0 = "" + Math.random();
        CategoryEntity category = new CategoryEntity(name0);
        category = (CategoryEntity) CategoriesDao.insert(category);
        name0 = "" + Math.random();
        category.name = name0;
        category = (CategoryEntity) CategoriesDao.update(category);
        // Successful update
        assertNotNull(category);
        assertEquals(1, category.version);
        int id = category.id;

        category.version = 0;
        String name1 = "" + Math.random();
        category.name = name1;
        // Failed update due to version being out of date
        category = (CategoryEntity) CategoriesDao.update(category);
        assertNull(category);
        category = CategoriesDao.findById(id);
        // Delete test entity
        assertTrue(CategoriesDao.delete(category));

        // Assert that version can start from any number
        name0 = "" + Math.random();
        category = new CategoryEntity(name0);
        category.version = 5;
        category = (CategoryEntity) CategoriesDao.insert(category);
        name0 = "" + Math.random();
        category.name = name0;
        category = (CategoryEntity) CategoriesDao.update(category);
        assertNotNull(category);
        assertEquals(6, category.version);
        CategoriesDao.delete(category);
    }

   // @Test
    public void delete() throws Exception {
        String name0 = "" + Math.random();
        CategoryEntity category = new CategoryEntity(name0);
        CategoryEntity inserted = (CategoryEntity) CategoriesDao.insert(category);
        List<CategoryEntity> resultList = CategoriesDao.findAll();

        CategoryEntity toDelete = new CategoryEntity(inserted.id);
        CategoriesDao.delete(toDelete);
        boolean foundDeleted = false;
        resultList = CategoriesDao.findAll();
        for (CategoryEntity c : resultList) {
            if (c.name.equals(category.name)) {
                foundDeleted = true;
                break;
            }
        }
        assertFalse(foundDeleted);
        // Double deletions
    }

    @Test
    public void categoryItemsMapping() throws Exception {
        String cName = "" + Math.random();
        CategoryEntity category = new CategoryEntity(cName);
        String iName = "" + Math.random();
        category = (CategoryEntity) CategoriesDao.insert(category); // returns the persistent entity

        ItemEntity item = new ItemEntity(0, 0, iName, 0, 0);

        category.items.add(item);

        CategoriesDao.insert(category);

        assertNotNull(category);

    }

    //@Test
    public void findAll() throws Exception {
    }
}