package model.handlers;

import model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryHandler {

    private static List<Category> categories;

    public static List<Category> getCategories() {
        categories = new ArrayList<>();
        categories.add(new Category("Shirts"));
        categories.add(new Category("Pants"));
        categories.add(new Category("Gloves"));
        return categories;
    }
}
