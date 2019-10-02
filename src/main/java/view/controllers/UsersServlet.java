package view.controllers;

import model.UserRoles;
import model.handlers.ShoppingHandler;
import model.handlers.exceptions.LoginException;
import model.handlers.exceptions.RegisterException;
import view.viewmodels.User;
import model.handlers.UsersHandler;
import view.Commands;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static view.Commands.*;
import static view.Pages.*;

@WebServlet(name = "Users")
public class UsersServlet extends HttpServlet {

    private static final String NO_PASSWORD_MSG         = "No specified password.";
    private static final String NO_USERNAME_MSG         = "No specified username.";
    private static final String LOGIN_FAILURE_MSG       = "Invalid username or password";
    private static final String LOGIN_EXCEPTION_MSG     = "Unknown exception raised.";
    private static final String REG_PASSWORD_FAIL_MSG   = "Passwords do not match";
    private static final String USERNAME_TAKEN_MSG      = "Username is already taken.";
    private static final String REG_EXCEPTION_MSG       = "Unknown exception raised.";
    private static final String ILLEGAL_ACCESS_MSG      = "Unauthorized Access.";
    private static final String UNKNOWN_EXCEPTION_MSG   = "Unknown exception raised";
    private static final String CANNOT_DELETE_SELF      = "Cannot delete yourself";

    private ShoppingHandler shoppingHandler = ShoppingHandler.getInstance();

    public void init(ServletConfig config) {

    }


    private void errorResponse(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String msg,
                                 String redirect) throws ServletException, IOException {
        request.setAttribute(ERR_RESPONSE_ARG, msg);
        request.getRequestDispatcher(redirect).forward(request, response);
    }

    private void onLoginSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                User user) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setAttribute(USER_NAME_ARG, user.getName());
        session.setAttribute(ARG_CURR_USER, user);

        response.sendRedirect(HOME_JSP);

    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(USER_NAME_ARG);
        String password = request.getParameter(USER_PASS_ARG);
        int result = -1;
        User user = null;
        try {
            user = UsersHandler.login(username, password);
            result = UsersHandler.LOGIN_SUCCESS;
        } catch (Exception e) {
            if (e instanceof LoginException) {
                LoginException le = (LoginException) e;
                result = le.code;
            } else {
                // Unexpected exception
                e.printStackTrace();
                result = UsersHandler.EXCEPTION;
            }
        }
        switch (result) {
            case UsersHandler.NO_PASSWORD:
                errorResponse(request, response, NO_PASSWORD_MSG, LOGIN_JSP);
                break;
            case UsersHandler.NO_USERNAME:
                errorResponse(request, response, NO_USERNAME_MSG, LOGIN_JSP);
                break;
            case UsersHandler.LOGIN_SUCCESS:
                onLoginSuccess(request, response, user);
                break;
            case UsersHandler.LOGIN_FAILURE:
                errorResponse(request, response, LOGIN_FAILURE_MSG, LOGIN_JSP);
                break;
            default:
                errorResponse(request, response, LOGIN_EXCEPTION_MSG, LOGIN_JSP);
        }
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ARG_CURR_USER);
        UsersHandler.logout(user.getId());
        // User
        session.removeAttribute(USER_NAME_ARG);
        session.removeAttribute(ARG_CURR_USER);
        // User Cart
        session.removeAttribute(CART_ARG);
        session.invalidate();
        response.sendRedirect(LOGIN_JSP);

    }

    private void doRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(USER_NAME_ARG);
        String password = request.getParameter(USER_PASS_ARG);
        String password1 = request.getParameter(USER_PASS_1_ARG);

        int result = -1;
        User user = null;
        try {
            user = UsersHandler.register(username, password, password1);
            result = UsersHandler.REGISTRATION_SUCCESS;
        } catch (Exception e) {
            if (e instanceof RegisterException) {
                RegisterException re = (RegisterException) e;
                result = re.code;
            } else {
                // Unexpected exception
                e.printStackTrace();
                result = UsersHandler.EXCEPTION;
            }
        }
        switch (result) {
            case UsersHandler.NO_USERNAME:
                errorResponse(request, response, NO_USERNAME_MSG, REGISTER_JSP);
                break;
            case UsersHandler.NO_PASSWORD:
                errorResponse(request, response, NO_PASSWORD_MSG, REGISTER_JSP);
                break;
            case UsersHandler.NO_PASSWORD_MATCH:
                errorResponse(request, response, REG_PASSWORD_FAIL_MSG, REGISTER_JSP);
                break;
            case UsersHandler.REGISTRATION_SUCCESS:
                onLoginSuccess(request, response, user);
                break;
            case UsersHandler.REGISTRATION_FAILURE:
                errorResponse(request, response, USERNAME_TAKEN_MSG, REGISTER_JSP);
                break;
            default:
                errorResponse(request, response, REG_EXCEPTION_MSG, REGISTER_JSP);
        }
    }

    private void adminGetAllUsers(HttpSession session, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) session.getAttribute(ARG_CURR_USER);
        List<User> users = UsersHandler.getAllUsers((List<String>) user.getAccessRoles());
        session.setAttribute(ARG_ALL_USERS, users);
        session.setAttribute(ARG_ACCESS_ROLES, UserRoles.asList());
        response.sendRedirect(ADMIN_USERS_JSP);
    }

    private void deleteUser(HttpSession session,  HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paramId = request.getParameter(ARG_USER_ID);
        if (paramId != null && paramId.length() > 0) {
            int id = Integer.parseInt(paramId);
            User currUser = (User) session.getAttribute(ARG_CURR_USER);
            String currPage = (String) session.getAttribute(ARG_CURR_PAGE);
            if (currUser.getId() != id) {
                switch (UsersHandler.deleteUser(id, (List<String>) currUser.getAccessRoles())) {
                    case UsersHandler.ACCESS_DENIED:
                        errorResponse(request, response, ILLEGAL_ACCESS_MSG, currPage);
                        break;
                    case UsersHandler.DELETE_OK:
                    case UsersHandler.DELETE_FAILURE:
                    default:
                        adminGetAllUsers(session, response);
                }
            }
            else {
                errorResponse(request, response, CANNOT_DELETE_SELF, ADMIN_USERS_JSP);
            }
        }
    }

    private void gotoEditUser(HttpSession session,  HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paramId = request.getParameter(ARG_USER_ID);
        if (paramId != null && paramId.length() > 0) try {
            User currUser = (User) session.getAttribute(ARG_CURR_USER);
            if (currUser != null && currUser.isAdmin()) {
                User editUser = UsersHandler.getUserById(Integer.parseInt(paramId));
                session.setAttribute(USER_TO_EDIT_ARG, editUser);
                response.sendRedirect(ADMIN_EDIT_USER_JSP);
            } else {
                errorResponse(request, response, ILLEGAL_ACCESS_MSG, HOME_JSP);
            }
        } catch (Exception e) {
            errorResponse(request, response, UNKNOWN_EXCEPTION_MSG, ADMIN_USERS_JSP);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = translateRequestToCommand(request);
        HttpSession session = request.getSession();
        if (command != null && command.length() > 0) {
            switch (command) {
                case LOGIN_COMMAND: doLogin(request, response); break;
                case LOGOUT_COMMAND: doLogout(request, response); break;
                case REGISTER_COMMAND: doRegister(request, response); break;
                case CMD_USERS_GET_ALL: adminGetAllUsers(session, response); break;
                case CMD_DELETE_USER: deleteUser(session, request, response); break;
                case CMD_GOTO_EDIT_USER: gotoEditUser(session, request, response); break;
                default:
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
