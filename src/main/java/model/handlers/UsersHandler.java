package model.handlers;

import model.Converter;
import model.handlers.exceptions.LoginException;
import model.handlers.exceptions.RegisterException;
import model.repository.DAO.UsersDB;
import model.repository.entities.UserEntity;
import view.viewmodels.User;

import java.util.ArrayList;
import java.util.List;

public class UsersHandler {

    // Response codes
    public static final int NO_USERNAME             = 1;
    public static final int NO_PASSWORD             = 2;
    public static final int LOGIN_SUCCESS           = 3;
    public static final int LOGIN_FAILURE           = 4;
    public static final int NO_PASSWORD_MATCH       = 6;
    public static final int REGISTRATION_SUCCESS    = 7;
    public static final int REGISTRATION_FAILURE    = 8;
    public static final int EXCEPTION               = 9;

    public static User login(String username, String password) throws Exception {
        if (username == null || username.length() == 0)
            throw new LoginException(NO_USERNAME);
        else if (password == null || password.length() == 0)
            throw new LoginException(NO_PASSWORD);
        UserEntity user = UsersDB.findUserByCredentials(username, password);
        if (user != null) {
            return Converter.toUser(user);
        }
        throw new LoginException(LOGIN_FAILURE);
    }

    public static User register(String username, String password, String password1) throws Exception {
        if (username == null || username.length() == 0)
            throw new RegisterException(NO_USERNAME);
        if (password == null || password.length() == 0 || password1 == null || password1.length() == 0)
            throw new RegisterException(NO_PASSWORD);
        if (!password.equals(password1))
            throw new RegisterException(NO_PASSWORD_MATCH);
        // New User
        UserEntity newUser = new UserEntity(username, password);
        newUser.addAccess("Admin");
        newUser.addAccess("Customer");
        newUser.addAccess("Worker");
        UserEntity user = (UserEntity) UsersDB.insert(newUser);
        if (user == null)
            throw new RegisterException(REGISTRATION_FAILURE);
        return Converter.toUser(user);
    }

    public static void logout() {
        // Do Nothing
    }

    public static User getUserById(int id) {
        return null;
    }

    public static List<User> getUsers() {
        try {
            return Converter.toUsers(UsersDB.findAll());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
