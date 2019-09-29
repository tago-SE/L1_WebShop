package model.handlers;

import view.viewmodels.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to provide protection to certain classes by matching access roles to class names.
 */
public class AccessList {

    private static final HashMap<String, List<String>> accessHashMap = new HashMap<>();

    /**
     *
     * @param c, Class
     * @param access, One or more access roles
     * @return true if access was granted
     */
    public static boolean validateAccess(Class c, String... access) {
        if (c != null) {
            List<String> accessList = accessHashMap.get(c.getName());
            if (accessList != null && access != null) {
                for (String a : access) {
                    if (a != null && accessList.contains(a)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Adds accepted access roles for a certain class.
     * @param c, Class
     * @param roles, One or more access roles
     */
    public static void addAccessRights(Class c, String... roles) {
        if (c == null || roles.length < 1)
            throw new IllegalArgumentException();
        String key = c.getName();
        List<String> accessList = null;
        if (accessHashMap.containsKey(key)) {
            accessList = accessHashMap.get(key);
        } else {
            accessList = new ArrayList<>();
            accessHashMap.put(key, accessList);
        }
        for (String a: roles) {
            if (!accessList.contains(a))
                accessList.add(a);
        }
    }

    public static HashMap<String, List<String>> getAccessHashMap() {
        return accessHashMap;
    }

}
