package model.handlers;

import model.Converter;
import view.viewmodels.Category;
import model.repository.entities.CategoryEntity;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ConvertTest {

    @Test
    public void toModel() {
        CategoryEntity ce = new CategoryEntity();
        ce.id = 1;
        ce.name = "Hello";
        Category c = Converter.toModel(ce);
        assertEquals("Hello", c.getName());
        assertEquals(1, c.getId());
    }
}