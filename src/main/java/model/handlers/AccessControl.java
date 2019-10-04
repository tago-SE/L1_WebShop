package model.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to provide protection by filtering access either by using a general filter or
 * a method specific filter if such exists.
 */
public class AccessControl {

    private final List<String> classACL = new ArrayList<>();
    private final HashMap<Integer, List<String>> methodACL = new HashMap<>();

    public AccessControl() { }

    public AccessControl(List<String> access) {
        if (access != null)
            addAccessRights(access);
    }

    public AccessControl(String... access) {
        if (access != null)
            addAccessRights(access);
    }

    public void addAccessRights(String... accessRights) {
        if (accessRights != null) for (String a : accessRights) {
            if (!classACL.contains(a))
                classACL.add(a);
        }
    }

    public void addAccessRights(List<String> accessRights) {
        if (accessRights != null) for (String a : accessRights) {
            if (!classACL.contains(a))
                classACL.add(a);
        }
    }

    private int getMethodKey(String methodName) {
        return  Objects.hash(methodName);
    }

    public void addMethodAccessRights(String methodName, String... accessRights) {
        if (accessRights == null && methodName == null)
            throw new NullPointerException();
        int key = getMethodKey(methodName);
        List<String> accessList = methodACL.get(key);
        if (accessList == null) {
            accessList = new ArrayList<>();
            methodACL.put(key, accessList);
        }
        for (String a : accessRights) {
           if (!accessList.contains(a))
               accessList.add(a);
        }
    }

    public void addMethodAccessRights(String methodName,List<String> accessRights) {
        if (accessRights == null && methodName == null)
            throw new NullPointerException();
        addMethodAccessRights(methodName, accessRights.toArray(new String[accessRights.size()]));
    }

    public boolean validateAccess(String methodName, List<String> access) {
        List<String> accessList = classACL;
        if (methodName != null) {
            int key = getMethodKey(methodName);
            if (methodACL.containsKey(key))
                accessList = methodACL.get(key);
        }
        for (String a : access) {
            if (accessList.contains(a))
                return true;
        }
        return false;
    }

    public boolean validateAccess(List<String> access) {
        return validateAccess(null, access);
    }

    @Override
    public String toString() {
        return "AccessControl{" +
                "classACL=" + classACL +
                ", methodACL=" + methodACL +
                '}';
    }
}
