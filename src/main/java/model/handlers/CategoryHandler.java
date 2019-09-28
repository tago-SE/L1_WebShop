package model.handlers;

import model.Converter;
import view.viewmodels.Category;
import model.repository.DAO.CategoriesDB;
import model.repository.entities.CategoryEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CategoryHandler {

    // Response codes
    public static final int EXCEPTION                   = 0;
    public static final int INSERT_OK                   = 1;
    public static final int INSERT_FAILURE              = 2;
    public static final int DELETE_OK                   = 4;
    public static final int DELETE_FAILURE              = 5;
    public static final int UPDATE_OK                   = 6;
    public static final int UPDATE_FAILURE              = 7;

    public static int newCategory(String name) {
        CategoryEntity newCategory = new CategoryEntity(name);
        try {
            if (CategoriesDB.insert(newCategory)) {
                return INSERT_OK;
            }
            return INSERT_FAILURE;
        } catch (Exception e) {
            return EXCEPTION;
        }
    }

    public static int updateCategoryName(int id, String name, Date sessionTimestamp) {
        CategoryEntity toUpdate = new CategoryEntity(name);
        toUpdate.id = id;
            try {
                if (CategoriesDB.update(toUpdate, sessionTimestamp)) {
                    return UPDATE_OK;
                }
            return UPDATE_FAILURE;
        } catch (Exception e) {
            return EXCEPTION;
        }
    }

    public static int deleteCategory(int id) {
        try {
            if (CategoriesDB.delete(id)) {
                return DELETE_OK;
            }
            return DELETE_FAILURE;
        } catch (Exception e) {
            return EXCEPTION;
        }
    }

    public static List<Category> getCategories() {
        try {
            return Converter.toModel(CategoriesDB.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
