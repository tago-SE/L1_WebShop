package controllers;

import db.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Register")
public class Register extends HttpServlet {

    protected void errorMessage(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException {
        request.setAttribute("errorMessage", msg);
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");

        if (username == null) {
            errorMessage(request, response, "Need to specify username.");
        }
        else if (password == null || password.length() == 0 || password1 == null || password1.length() == 0) {
            errorMessage(request, response, "Need to specify both passwords.");
        }
        else if (!password.equals(password1)) {
            errorMessage(request, response, "Passwords do not match.");
        } else {
            switch (UsersService.register(username, password)) {
                case UsersService.SUCCESS:
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
                    response.sendRedirect("home.jsp");
                    break;
                case UsersService.USER_NAME_TAKEN:
                    errorMessage(request, response, "Username is already taken.");
                    break;
                default:
                    errorMessage(request, response, "Unknown exception raised.");
            }
        }
    }
}
