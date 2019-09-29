package model.repository.DAO;

import model.repository.entities.CategoryEntity;
import model.repository.entities.UserEntity;
import org.junit.Test;
import org.junit.experimental.categories.Categories;
import view.viewmodels.Category;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.junit.Assert.*;

public class CategoriesDBTest {

    @Test
    public void insert() throws Exception {
        String name0 = "" + Math.random();
        // Should be able to insert a entity with a unique name
        CategoryEntity category = new CategoryEntity(name0);
        assertTrue(CategoriesDB.insert(category) != null);
        // Should not be able to insert a entity with the same name
        category = new CategoryEntity(name0);
        assertFalse(CategoriesDB.insert(category) != null);
        CategoriesDB.delete(new CategoryEntity(name0));
        // Should not be able to insert an entity with a id
        assertThrows(Exception.class, ()-> {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.name = "hello";
            categoryEntity.id = 5;
            CategoriesDB.insert(categoryEntity);
        });
    }

    @Test
    public void update() throws Exception {
        String name0 = "" + Math.random();
        CategoryEntity category = new CategoryEntity(name0);
        category = (CategoryEntity) CategoriesDB.insert(category);
        name0 = "" + Math.random();
        category.name = name0;
        category = (CategoryEntity) CategoriesDB.update(category);
        // Successful update
        assertNotNull(category);
        assertEquals(1, category.version);
        int id = category.id;

        category.version = 0;
        String name1 = "" + Math.random();
        category.name = name1;
        // Failed update due to version being out of date
        category = (CategoryEntity) CategoriesDB.update(category);
        assertNull(category);
        category = CategoriesDB.findById(id);
        // Delete test entity
        assertTrue(CategoriesDB.delete(category));

        // Assert that version can start from any number
        name0 = "" + Math.random();
        category = new CategoryEntity(name0);
        category.version = 5;
        category = (CategoryEntity) CategoriesDB.insert(category);
        name0 = "" + Math.random();
        category.name = name0;
        category = (CategoryEntity) CategoriesDB.update(category);
        assertNotNull(category);
        assertEquals(6, category.version);
        CategoriesDB.delete(category);
    }

    @Test
    public void delete() throws Exception {
        String name0 = "" + Math.random();
        CategoryEntity category = new CategoryEntity(name0);
        CategoriesDB.insert(category);
        List<CategoryEntity> resultList = CategoriesDB.findAll();
        boolean foundInserted = false;
        for (CategoryEntity c : resultList) {
            if (c.name.equals(category.name)) {
                foundInserted = true;
                break;
            }
        }
        // Precondition insertion must work
        assertTrue(foundInserted);
        CategoryEntity toDelete = new CategoryEntity(name0);
        CategoriesDB.delete(toDelete);
        boolean foundDeleted = false;
        resultList = CategoriesDB.findAll();
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
    public void findAll() throws Exception {
    }
}