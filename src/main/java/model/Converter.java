package model;

import model.repository.entities.UserEntity;
import view.viewmodels.Category;
import model.repository.entities.CategoryEntity;
import view.viewmodels.User;

import java.util.ArrayList;
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
        CategoryEntity entity = new CategoryEntity();
        entity.name = model.getName();
        entity.id = model.getId();
        entity.version = model.getVersion();
        return entity;
    }

    public static List<CategoryEntity> toCategoryEntities(List<Category> models) {
        List<CategoryEntity> resultList = new ArrayList<>();
        for (Category m : models) {
            resultList.add(toCategoryEntity(m));
        }
        return resultList;
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
