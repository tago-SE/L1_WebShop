package model.handlers;

import org.junit.Test;
import view.viewmodels.Category;

import static org.junit.Assert.*;

public class AccessListTest {

    @Test
    public void validateAccess() {
        String[] rights = {"Guest", "Admin"};
        Class c = CategoryHandler.class;
        AccessList.addAccessRights(c, rights);
        assertEquals(2, AccessList.getAccessHashMap().get(c.getName()).size());
        assertTrue(AccessList.validateAccess(c, "Guest"));
        assertTrue(AccessList.validateAccess(c, "Admin"));
        assertFalse(AccessList.validateAccess(c, "asdasd"));
        assertFalse(AccessList.validateAccess(c, "admin"));

        String[] rights2 = {"Guest"};
        AccessList.addAccessRights(c, rights2);
        assertTrue(AccessList.validateAccess(c, "Guest"));
        assertTrue(AccessList.validateAccess(c, "Admin"));
        assertFalse(AccessList.validateAccess(c, "asdasd"));
        assertFalse(AccessList.validateAccess(c, "admin"));
        assertFalse(AccessList.validateAccess(c, ""));
        assertFalse(AccessList.validateAccess(c, null));
        assertEquals(2, AccessList.getAccessHashMap().get(c.getName()).size());
    }

}