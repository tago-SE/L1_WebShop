package model;

import java.util.Arrays;
import java.util.List;

public class UserRoles {

    public static final String ADMIN = "Admin";
    public static final String CUSTOMER = "Customer";
    public static final String STORAGE_WORKER = "Worker";

    public static final String[] roles = {ADMIN, CUSTOMER, STORAGE_WORKER};

    public static List<String> asList() {
        return Arrays.asList(roles);
    }

}
