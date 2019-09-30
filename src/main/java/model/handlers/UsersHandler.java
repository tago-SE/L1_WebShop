package model.handlers;

import utils.Converter;
import model.handlers.exceptions.LoginException;
import model.handlers.exceptions.RegisterException;
import model.repository.DAO.UsersDao;
import model.repository.entities.UserEntity;
import view.viewmodels.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UsersHandler {

    // Authorized roles for specific operations
    public static final String[] accessRoles = {"Admin"};

    // Response codes
    public static final int NO_USERNAME             = 1;
    public static final int NO_PASSWORD             = 2;
    public static final int LOGIN_SUCCESS           = 3;
    public static final int LOGIN_FAILURE           = 4;
    public static final int NO_PASSWORD_MATCH       = 6;
    public static final int REGISTRATION_SUCCESS    = 7;
    public static final int REGISTRATION_FAILURE    = 8;
    public static final int EXCEPTION               = 9;
    public static final int ACCESS_DENIED           = 10;
    public static final int DELETE_OK               = 11;
    public static final int DELETE_FAILURE          = 12;


    // Singleton used for initialization
    private static final UsersHandler instance = new UsersHandler();
    private static AccessControl accessControl;

    private UsersHandler() {
        accessControl = new AccessControl(accessRoles);
    }

    public static User login(String username, String password) throws Exception {
        if (username == null || username.length() == 0)
            throw new LoginException(NO_USERNAME);
        else if (password == null || password.length() == 0)
            throw new LoginException(NO_PASSWORD);
        UserEntity user = UsersDao.findUserByCredentials(username, password);
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
        UserEntity user = (UserEntity) UsersDao.insert(newUser);
        if (user == null)
            throw new RegisterException(REGISTRATION_FAILURE);
        return Converter.toUser(user);
    }

    public static void logout() {
        // Do Nothing
    }

    public static User getUserById(int id) throws Exception {
        return Converter.toUser((UserEntity) Objects.requireNonNull(UsersDao.findById(new UserEntity(id))));
    }

    public static int deleteUser(int id, List<String> access) {
        if (!accessControl.validateAccess(null, access))
            return ACCESS_DENIED;
        UserEntity toDelete = new UserEntity();
        toDelete.id = id;
        try {
            if (UsersDao.delete(toDelete))
                return DELETE_OK;
            return DELETE_FAILURE;
        } catch (Exception e) {
            e.printStackTrace();
            return EXCEPTION;
        }
    }

    public static List<User> getAllUsers(List<String> access) {
        if (!accessControl.validateAccess(null, access))
            return null;
        try {
            List<User> users = Converter.toUsers(UsersDao.findAll());
            System.out.println(users.toString());
            return Converter.toUsers(UsersDao.findAll());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
