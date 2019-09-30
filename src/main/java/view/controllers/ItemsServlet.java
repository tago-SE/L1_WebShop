package view.controllers;

import view.Commands;
import view.viewmodels.User;

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

@WebServlet(name = "Items")
public class ItemsServlet extends HttpServlet {

    private void insertOrUpdateItem(HttpSession session,  HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nameParam = request.getParameter(ITEM_NAME_ARG);
        String idParam = request.getParameter(ITEM_ID_ARG);
        String c1 = request.getParameter("s1");
        System.out.println("UPSERT: " + nameParam + ", " + idParam + ", " + c1);
    }

    private void gotoEditItem(HttpSession session,  HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(EDIT_ITEM_JSP);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = Commands.translateRequestToCommand(request);
        HttpSession session = request.getSession();
        if (command != null && command.length() > 0) {
            switch (command) {
                case GOTO_INSERT_ITEM_CMD: gotoEditItem(session, request, response); break;
                case UPSERT_ITEM_CDM: insertOrUpdateItem(session, request, response); break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
