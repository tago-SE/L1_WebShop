package model.handlers;

import model.handlers.exceptions.DatabaseException;
import model.repository.dao.CategoriesDao;
import model.repository.dao.ItemsDao;
import model.repository.entities.CategoryEntity;
import model.repository.entities.ItemEntity;
import utils.Converter;
import view.viewmodels.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemsHandler {

    private static final String[] accessRoles = {"Admin", "Worker"};
    private static final AccessControl accessControl = new AccessControl(accessRoles);

    public static void newItem(Item item, List<String> access) throws Exception, IllegalAccessException {
        if (!accessControl.validateAccess(null, access))
            throw new IllegalAccessException();
        ItemEntity entity = Converter.toItemEntity(item);
        ItemsDao.insert(entity);
    }

    public static void deleteItem(int id, List<String> access) throws Exception {
        if (!accessControl.validateAccess(null, access))
            throw new IllegalAccessException();
        ItemsDao.delete(id);
    }

    public static Item getItemById(int id) throws Exception {
        return Converter.toItem((ItemEntity) ItemsDao.findById(new ItemEntity(id)));
    }


    public static boolean updateItem(Item item, List<String> access) throws Exception {
        if (!accessControl.validateAccess(null, access))
            throw new IllegalAccessException();
        ItemEntity toUpdate = Converter.toItemEntity(item);
        return ItemsDao.update(toUpdate) != null;
    }

    public static List<Item> getItems() {
        try {
            return Converter.toItems(ItemsDao.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateItemStock(int itemId, int amount, List<String> access) throws IllegalAccessException, DatabaseException {
        if (!accessControl.validateAccess(null, access))
            throw new IllegalAccessException();
        return ItemsDao.setItemQuantity(itemId, amount) != null;
    }

    public static List<Item> getItemsByCategories(String... categories) throws Exception {
        List<ItemEntity> resultList = new ArrayList<>();
        for (String name : categories) {
            CategoryEntity category = CategoriesDao.findByName(name);
            if (category != null)
                category.items.forEach(item -> {
                    if (!resultList.contains(item))
                        resultList.add(item);
                });
        }
        return Converter.toItems(resultList);
    }
}
