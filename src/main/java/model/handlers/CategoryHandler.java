package model.handlers;

import model.Converter;
import view.viewmodels.Category;
import model.repository.DAO.CategoriesDB;
import model.repository.entities.CategoryEntity;

import java.util.Date;
import java.util.List;

public class CategoryHandler {

    // Authorized roles
    public static final String[] accessRoles = {"Admin", "Worker"};

    // Response codes
    public static final int EXCEPTION                   = 0;
    public static final int INSERT_OK                   = 1;
    public static final int INSERT_FAILURE              = 2;
    public static final int DELETE_OK                   = 4;
    public static final int DELETE_FAILURE              = 5;
    public static final int UPDATE_OK                   = 6;
    public static final int UPDATE_FAILURE              = 7;
    public static final int ACCESS_DENIED               = 8;

    // Singleton used for initialization
    private static final CategoryHandler instance = new CategoryHandler();

    private CategoryHandler() {
        AccessList.addAccessRights(CategoryHandler.class, accessRoles);
    }

    public static int newCategory(String name, String... access) {
        if (!AccessList.validateAccess(CategoryHandler.class, access))
            return ACCESS_DENIED;
        CategoryEntity newCategory = new CategoryEntity(name);
        try {
            if (CategoriesDB.insert(newCategory) != null ) {
                return INSERT_OK;
            }
            return INSERT_FAILURE;
        } catch (Exception e) {
            e.printStackTrace();
            return EXCEPTION;
        }
    }

    public static int updateCategory(Category category, String... access) {
        if (!AccessList.validateAccess(CategoryHandler.class, access))
            return ACCESS_DENIED;
        try {
            CategoryEntity entity = Converter.toCategoryEntity(category);;
            if (CategoriesDB.update(entity) != null) {
                return UPDATE_OK;
            }
            return UPDATE_FAILURE;
        } catch (Exception e) {
            e.printStackTrace();
            return EXCEPTION;
        }
    }

    public static int deleteCategory(String categoryName, String... access) {
        if (!AccessList.validateAccess(CategoryHandler.class, access))
            return ACCESS_DENIED;
        try {
            CategoryEntity toDelete = new CategoryEntity();
            toDelete.name = categoryName;
            if (CategoriesDB.delete(toDelete)) {
                return DELETE_OK;
            }
            return DELETE_FAILURE;
        } catch (Exception e) {
            e.printStackTrace();
            return EXCEPTION;
        }
    }

    public static List<Category> getCategories() {
        try {
            return Converter.toCategories(CategoriesDB.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
