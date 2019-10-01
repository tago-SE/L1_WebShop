package model.handlers;

import utils.Converter;
import view.viewmodels.Category;
import model.repository.dao.CategoriesDao;
import model.repository.entities.CategoryEntity;

import java.util.List;

public class CategoriesHandler {

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


    public static final AccessControl accessControl = new AccessControl(accessRoles);

    public static int newCategory(String name, List<String> access) {
        if (!accessControl.validateAccess(null, access))
            return ACCESS_DENIED;
        CategoryEntity newCategory = new CategoryEntity(name);
        try {
            if (CategoriesDao.insert(newCategory) != null ) {
                return INSERT_OK;
            }
            return INSERT_FAILURE;
        } catch (Exception e) {
            e.printStackTrace();
            return EXCEPTION;
        }
    }

    public static int updateCategory(Category category, List<String> access) {
        if (!accessControl.validateAccess(null, access))
            return ACCESS_DENIED;
        try {
            CategoryEntity entity = Converter.toCategoryEntity(category);;
            if (CategoriesDao.update(entity) != null) {
                return UPDATE_OK;
            }
            return UPDATE_FAILURE;
        } catch (Exception e) {
            e.printStackTrace();
            return EXCEPTION;
        }
    }

    public static int deleteCategory(int id, List<String> access) {
        if (!accessControl.validateAccess(null, access))
            return ACCESS_DENIED;
        try {
            if (CategoriesDao.delete(id)) {
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
            return Converter.toCategories(CategoriesDao.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
