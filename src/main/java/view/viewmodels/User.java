package view.viewmodels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class User {

    private int id;
    private int version;
    private String name;
    private Collection<String> accessRoles;


    public User() {}

    public User(String name) {
        this.name = name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(int id, int version, String name, List<String> accessRoles) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.accessRoles = new ArrayList<>(accessRoles);
    }

    public Integer getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public Collection<String> getAccessRoles() {
        return accessRoles;
    }

    public boolean isAdmin() {
        if (accessRoles != null)
            for (String role : accessRoles) {
                if (role.equals("Admin"))
                    return true;
            }
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", roles=" + accessRoles +
                '}';
    }

}
