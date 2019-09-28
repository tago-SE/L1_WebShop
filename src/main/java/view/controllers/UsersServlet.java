package view.controllers;

import view.viewmodels.User;
import model.handlers.CategoryHandler;
import model.handlers.UsersHandler;
import view.Commands;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "Users")
public class UsersServlet extends HttpServlet {

    private static final String NO_PASSWORD_MSG         = "No specified password.";
    private static final String NO_USERNAME_MSG         = "No specified username.";
    private static final String LOGIN_FAILURE_MSG       = "Invalid username or password";
    private static final String LOGIN_EXCEPTION_MSG     = "Unknown exception raised.";
    private static final String REG_PASSWORD_FAIL_MSG   = "Passwords do not match";
    private static final String USERNAME_TAKEN_MSG      = "Username is already taken.";
    private static final String REG_EXCEPTION_MSG       = "Unknown exception raised.";

    private void errorResponse(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String msg,
                                 String redirect) throws ServletException, IOException {

        request.setAttribute("errorResponse", msg);
        request.getRequestDispatcher(redirect).forward(request, response);
    }

    private void onLoginSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                String username) throws ServletException, IOException {


        User currUser = new User(-1, username, "Admin");

        HttpSession session = request.getSession();
        session.setAttribute(Commands.USER_NAME_ARG, username);
        session.setAttribute(Commands.CURR_USER_ARG, currUser);

        // Set Categories
        session.setAttribute(Commands.CATEGORY_LIST_ARG, CategoryHandler.getCategories());
        session.setAttribute(Commands.CATEGORY_TS_ARG, new Date());

        response.sendRedirect("home.jsp");


    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(Commands.USER_NAME_ARG);
        String password = request.getParameter(Commands.USER_PASS_ARG);
        switch (UsersHandler.login(username, password)) {
            case UsersHandler.NO_PASSWORD:
                errorResponse(request, response, NO_PASSWORD_MSG, "login.jsp");
                break;
            case UsersHandler.NO_USERNAME:
                errorResponse(request, response, NO_USERNAME_MSG, "login.jsp");
                break;
            case UsersHandler.LOGIN_SUCCESS:
                onLoginSuccess(request, response, username);
                break;
            case UsersHandler.LOGIN_FAILURE:
                errorResponse(request, response, LOGIN_FAILURE_MSG, "login.jsp");
                break;
            default:
                errorResponse(request, response, LOGIN_EXCEPTION_MSG, "login.jsp");
        }
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute(Commands.USER_NAME_ARG);
        session.removeAttribute(Commands.CURR_USER_ARG);
        session.invalidate();
        response.sendRedirect("login.jsp");
    }

    private void doRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(Commands.USER_NAME_ARG);
        String password = request.getParameter(Commands.USER_PASS_ARG);
        String password1 = request.getParameter(Commands.USER_PASS_1_ARG);

        switch (UsersHandler.register(username, password, password1)) {
            case UsersHandler.NO_USERNAME:
                errorResponse(request, response, NO_USERNAME_MSG, "register.jsp");
                break;
            case UsersHandler.NO_PASSWORD:
                errorResponse(request, response, NO_PASSWORD_MSG, "register.jsp");
                break;
            case UsersHandler.NO_PASSWORD_MATCH:
                errorResponse(request, response, REG_PASSWORD_FAIL_MSG, "register.jsp");
                break;
            case UsersHandler.REGISTRATION_SUCCESS:
                onLoginSuccess(request, response, username);
                break;
            case UsersHandler.REGISTRATION_FAILURE:
                errorResponse(request, response, USERNAME_TAKEN_MSG, "register.jsp");
                break;
            default:
                errorResponse(request, response, REG_EXCEPTION_MSG, "register.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = Commands.translateRequestToCommand(request);
        if (command != null && command.length() > 0) {
            switch (command) {
                case Commands.LOGIN_COMMAND: doLogin(request, response); break;
                case Commands.LOGOUT_COMMAND: doLogout(request, response); break;
                case Commands.REGISTER_COMMAND: doRegister(request, response); break;
                default:
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
