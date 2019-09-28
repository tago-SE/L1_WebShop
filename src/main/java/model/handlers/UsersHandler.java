package model.handlers;

import model.repository.DAO.UsersDB;
import model.repository.entities.UserEntity;

public class UsersHandler {

    // Response codes
    public static final int NO_USERNAME             = 1;
    public static final int NO_PASSWORD             = 2;
    public static final int LOGIN_SUCCESS           = 3;
    public static final int LOGIN_FAILURE           = 4;
    public static final int LOGIN_EXCEPTION         = 5;
    public static final int NO_PASSWORD_MATCH       = 6;
    public static final int REGISTRATION_SUCCESS    = 7;
    public static final int REGISTRATION_FAILURE    = 8;
    public static final int REGISTRATION_EXCEPTION  = 9;

    public static int login(String username, String password) {
        if (username == null || username.length() == 0)
            return NO_USERNAME;
        else if (password == null || password.length() == 0)
            return NO_PASSWORD;
        try {
            UserEntity user = UsersDB.getUserByCredentials(username, password);
            if (user != null) {
                return LOGIN_SUCCESS;
            }
            return LOGIN_FAILURE;
        } catch (Exception e) {
            e.printStackTrace();
            return LOGIN_EXCEPTION;
        }
    }

    public static int register(String username, String password, String password1) {
        if (username == null || username.length() == 0)
            return NO_USERNAME;
        if (password == null || password.length() == 0 || password1 == null || password1.length() == 0)
            return NO_PASSWORD;
        if (!password.equals(password1))
            return NO_PASSWORD_MATCH;
        try {
            boolean result = UsersDB.insert(new UserEntity(username, password));
            if (result) {
                return REGISTRATION_SUCCESS;
            }
            return REGISTRATION_FAILURE;
        } catch (Exception e) {
            return REGISTRATION_EXCEPTION;
        }
    }

    public static void logout() {

    } // Do Nothing

}
