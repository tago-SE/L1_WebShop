package model.repository.DAO;

import model.repository.entities.CategoryEntity;
import model.repository.entities.UserEntity;
import org.junit.Test;
import org.junit.experimental.categories.Categories;

import java.util.List;

import static org.junit.Assert.*;

public class CategoriesDBTest {

    @Test
    public void insert() throws Exception {
        String name0 = "" + Math.random();
        // Should be able to insert a entity with a unique name
        CategoryEntity category = new CategoryEntity(name0);
        assertTrue(CategoriesDB.insert(category));
        // Should not be able to insert a entity with the same name
        category = new CategoryEntity(name0);
        assertFalse(CategoriesDB.insert(category));
        CategoriesDB.delete(new CategoryEntity(name0));
    }

    @Test
    public void update() throws Exception {
        String name0 = "" + Math.random();
        CategoryEntity category = new CategoryEntity(name0);
        CategoriesDB.insert(category);
        // TODO: Verify timestamp session errors and normal operations
    }

    @Test
    public void delete() throws Exception {
        String name0 = "" + Math.random();
        CategoryEntity category = new CategoryEntity(name0);
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
        for (CategoryEntity c : resultList) {
            if (c.name.equals(category.name)) {
                foundDeleted = true;
                break;
            }
        }
        assertFalse(foundDeleted);
    }

    @Test
    public void findAll() {
    }
}