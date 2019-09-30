package model;

import java.util.Arrays;
import java.util.List;

public class UserRoles {

    public static final String ADMIN = "Admin";
    public static final String CUSTOMER = "Customer";
    public static final String EMPLOYEE = "Employee";

    public static final String[] roles = {ADMIN, CUSTOMER, EMPLOYEE};

    public static List<String> asList() {
        return Arrays.asList(roles);
    }

}
