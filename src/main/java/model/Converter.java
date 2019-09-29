package model;

import view.viewmodels.Category;
import model.repository.entities.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts Entity objects into Model objects and vice versa.
 */
public class Converter {

    public static Category toModel(CategoryEntity entity) {
       return new Category(entity.id, entity.name, entity.version);
    }

    public static List<Category> toModel(List<CategoryEntity> entities) {
        List<Category> resultList = new ArrayList<>();
        for (CategoryEntity e : entities) {
            resultList.add(toModel(e));
        }
        return resultList;
    }

    public static CategoryEntity toEntity(Category model) {
        CategoryEntity entity = new CategoryEntity();
        entity.name = model.getName();
        entity.id = model.getId();
        entity.version = model.getVersion();
        return entity;
    }

    public static List<CategoryEntity> toEntity(List<Category> models) {
        List<CategoryEntity> resultList = new ArrayList<>();
        for (Category m : models) {
            resultList.add(toEntity(m));
        }
        return resultList;
    }

}
