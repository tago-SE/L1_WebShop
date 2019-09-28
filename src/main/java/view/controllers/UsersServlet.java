package view.controllers;

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

@WebServlet(name = "Users")
public class UsersServlet extends HttpServlet {

    protected void errorMessage(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException {
        request.setAttribute("errorMessage", msg);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        switch (UsersHandler.login(username, password)) {
            case UsersHandler.NO_PASSWORD:
                errorMessage(request, response, "No specified password.");
                break;
            case UsersHandler.NO_USERNAME:
                errorMessage(request, response, "No specified username.");
                break;
            case UsersHandler.LOGIN_SUCCESS:
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                // Set Categories
                session.setAttribute("categories", CategoryHandler.getCategories());
                response.sendRedirect("home.jsp");
                break;
            case UsersHandler.LOGIN_FAILURE:
                errorMessage(request, response, "Invalid username or password");
                break;
            default:
                errorMessage(request, response, "Unknown exception raised.");
        }
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.invalidate();
        response.sendRedirect("login.jsp");
    }

    private void doRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");

        switch (UsersHandler.register(request.getParameter("username"), password, password1)) {
            case UsersHandler.NO_USERNAME:
                errorMessage(request, response, "Need to specify username.");
                break;
            case UsersHandler.NO_PASSWORD:
                errorMessage(request, response, "Need to specify both passwords.");
                break;
            case UsersHandler.NO_PASSWORD_MATCH:
                errorMessage(request, response, "Passwords do not match.");
                break;
            case UsersHandler.REGISTRATION_SUCCESS:
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect("home.jsp");
                break;
            case UsersHandler.REGISTRATION_FAILURE:
                errorMessage(request, response, "Username is already taken.");
                break;
            default:
                errorMessage(request, response, "Unknown exception raised.");
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
