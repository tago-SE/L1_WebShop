package utils;

import model.repository.entities.ItemEntity;
import model.repository.entities.UserEntity;
import view.viewmodels.Category;
import model.repository.entities.CategoryEntity;
import view.viewmodels.Item;
import view.viewmodels.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Converts Entity objects into Model objects and vice versa.
 */
public class Converter {

    // ~~~~~~~~~~
    //  Category
    // ~~~~~~~~~~

    public static Category toCategory(CategoryEntity entity) {
       return new Category(entity.id, entity.name, entity.version);
    }

    public static List<Category> toCategories(List<CategoryEntity> entities) {
        List<Category> resultList = new ArrayList<>();
        for (CategoryEntity e : entities) {
            resultList.add(toCategory(e));
        }
        return resultList;
    }

    public static CategoryEntity toCategoryEntity(Category model) {
        CategoryEntity e = new CategoryEntity();
        e.name = model.getName();
        e.id = model.getId();
        e.version = model.getVersion();
        return e;
    }

    public static List<CategoryEntity> toCategoryEntities(List<Category> models) {
        List<CategoryEntity> resultList = new ArrayList<>();
        for (Category m : models) {
            resultList.add(toCategoryEntity(m));
        }
        return resultList;
    }

    // ~~~~~~~
    //  Item
    // ~~~~~~~

    public static ItemEntity toItemEntity(Item model) {
        ItemEntity e = new ItemEntity();
        e.id = model.getId();
        e.version = model.getVersion();
        e.name = model.getName();
        e.price = model.getPrice();
        e.quantity = model.getQuantity();
        List<Category> categories = (List<Category>) model.getCategories();
        List<String> categoriesAsStr = new ArrayList<>();
        for (Category c : categories) {
            categoriesAsStr.add(c.getName());
        }
        e.categories = new HashSet<String>(categoriesAsStr);
        return e;
    }

    // ~~~~~~~
    //  User
    // ~~~~~~~

    public static User toUser(UserEntity entity) {
        return new User(entity.id, entity.version, entity.name, new ArrayList<>(entity.accessRoles));
    }

    public static List<User> toUsers(List<UserEntity> entities) {
        List<User> resultList = new ArrayList<>();
        for (UserEntity e : entities) {
            resultList.add(toUser(e));
        }
        return resultList;
    }

}
