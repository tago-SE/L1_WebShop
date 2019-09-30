package model.handlers;

import org.junit.Test;

import javax.persistence.Access;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AccessControlTest {

    @Test
    public void addMethodAccessRight() {
        AccessControl ac = new AccessControl();
        List<String> accessList = new ArrayList<>();
        accessList.add("Admin");
        accessList.add("User");
        ac.addMethodAccessRights("addMethodAccessRight", accessList);
        System.out.println(ac.toString());
        ac = new AccessControl();
        ac.addMethodAccessRights("method1", "Admin", "User", "Guard");
        System.out.println(ac.toString());
        ac.addMethodAccessRights("method2", "Admin", "User", "Guard", "Turtle");
        System.out.println(ac.toString());
    }

    @Test
    public void addAccessRights() {
        String[] access = {"Admin", "Customer"};
        AccessControl ac = new AccessControl();
        ac.addAccessRights(access);
        System.out.println(ac.toString());
    }

    @Test
    public void validateAccess() {
        String[] access = {"Admin", "Customer"};
        AccessControl ac = new AccessControl(access);
        List<String> accessRights = new ArrayList<>();
        accessRights.add("Admin");
        assertTrue(ac.validateAccess(null, accessRights));
        accessRights.clear();
        accessRights.add("admin");
        assertFalse(ac.validateAccess(null, accessRights));

        // Method access
        accessRights.clear();
        accessRights.add("Admin");
        ac.addMethodAccessRights("method1", "Admin");
        assertTrue(ac.validateAccess("method1", accessRights));
        assertTrue(ac.validateAccess("asdasd", accessRights));
        accessRights.clear();
        assertFalse(ac.validateAccess("method1", accessRights));
    }

    @Test
    public void Constructors() {
        String[] access = {"Admin", "Customer"};
        AccessControl ac = new AccessControl(access);
        System.out.println(ac.toString());

        ac = new AccessControl(Arrays.asList(access));
        System.out.println(ac.toString());
    }
}