package model.handlers;

import model.repository.dao.CategoriesDao;
import model.repository.dao.ItemsDao;
import org.junit.Test;
import view.viewmodels.Category;
import view.viewmodels.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ItemsHandlerTest {

    @Test
    public void getItemsByCategories() throws Exception {
        List<String> access = new ArrayList<>();
        access.add("Admin");
        String name1 = "Hello123", name2 = "There123";
        int count = CategoriesDao.findAll().size();
        CategoriesHandler.newCategory("Hello123", access);
        CategoriesHandler.newCategory("Hello321", access);
        assertEquals(count + 2, CategoriesDao.findAll().size());
        List<Category> categories = CategoriesHandler.getCategories();
        ItemsHandler.newItem(new Item(0, 0, "i1", 0, 0, categories), access);
        ItemsHandler.newItem(new Item(0, 0, "i2", 0, 0, categories), access);
        ItemsHandler.newItem(new Item(0, 0, "i3", 0, 0, categories), access);

        System.out.println("\n QUERY STARTS HERE \n");
        List<Item> items = ItemsHandler.getItemsByCategories(name1, name2);
        System.out.println(items.toString());

        assertEquals(3, items.size());

        for (Category c : categories) {
            CategoriesDao.delete(c.getId());
        }
        for (Item i : items) {
            ItemsDao.delete(i.getId());
        }
    }
}