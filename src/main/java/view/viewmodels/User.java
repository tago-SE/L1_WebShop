package view.viewmodels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static model.UserRoles.*;

public class User {

    public final int id;
    public final int version;
    public final String name;
    public final List<String> accessRoles;
    public final String firstName;
    public final String lastName;


    public User(String name) {
        this.name = name;
        this.id = 0;
        this.version = 0;
        this.accessRoles = new ArrayList<>();
        firstName = lastName = "";
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.version = 0;
        this.accessRoles = new ArrayList<>();
        firstName = lastName = "";
    }

    public User(int id, int version, String name, List<String> accessRoles) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.accessRoles = new ArrayList<>(accessRoles);
        firstName = lastName = "";
    }

    public User(int id, int version, String name, List<String> accessRoles, String firstName, String lastName) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.accessRoles = accessRoles;
        this.firstName = firstName;
        this.lastName = lastName;
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
        for (String role : accessRoles) {
            if (role.equals(ADMIN))
                return true;
        }
        return false;
    }

    public boolean isCustomer() {
        for (String role : accessRoles) {
            if (role.equals(CUSTOMER))
                return true;
        }
        return false;
    }

    public boolean isStorageWorker() {
        for (String role : accessRoles) {
            if (role.equals(STORAGE_WORKER))
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
                ", accessRoles=" + accessRoles +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
