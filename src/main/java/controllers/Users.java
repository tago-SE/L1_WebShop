package controllers;

import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "Users")
public class Users extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User("Sally"));
        userList.add(new User("Peter"));
        userList.add(new User("Jenny"));

        request.setAttribute("listData", userList);
        request.getServletContext().getRequestDispatcher("users.jsp").forward(request, response);
    }

}
