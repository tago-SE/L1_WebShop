package model;

import view.viewmodels.Category;
import model.repository.entities.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts Entity objects into Model objects.
 */
public class Converter {

    public static Category toModel(CategoryEntity entity) {
        return new Category(entity.id, entity.name);
    }

    public static List<Category> toModel(List<CategoryEntity> entities) {
        List<Category> resultList = new ArrayList<>();
        for (CategoryEntity e : entities) {
            resultList.add(toModel(e));
        }
        return resultList;
    }

}
