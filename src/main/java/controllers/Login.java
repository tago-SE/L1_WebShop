package controllers;

import db.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "/Login")
public class Login extends HttpServlet {

    // TODO: https://stackoverflow.com/tags/el/info change to handle user objects rather than what it does now

    protected void errorMessage(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException {
        request.setAttribute("errorMessage", msg);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.length() == 0) {
            errorMessage(request, response, "Need to specify username.");
        }
        else if (password == null || password.length() == 0) {
            errorMessage(request, response, "Need to specify a password.");
        }
        else {
            switch (UsersService.login(username, password)) {
                case UsersService.SUCCESS:
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
                    response.sendRedirect("home.jsp");
                    break;
                case UsersService.INVALID_CREDENTIALS:
                    errorMessage(request, response, "Invalid username or password");
                default:
                    errorMessage(request, response, "Unknown exception raised.");
            }
        }

    }
}
