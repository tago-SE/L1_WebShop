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
import java.net.http.HttpRequest;
import java.util.Date;

@WebServlet(name = "ItemsServlet")
public class ItemsServlet extends HttpServlet {

    private void errorResponse(HttpServletRequest request,
                               HttpServletResponse response,
                               String msg,
                               String redirect) throws ServletException, IOException {

        request.setAttribute(Commands.ERROR_RESPONSE_COMMAND, msg);
        request.getRequestDispatcher(redirect).forward(request, response);
    }

    private void doReloadPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute(Commands.CATEGORY_LIST_ARG, CategoryHandler.getCategories());
        session.setAttribute(Commands.CATEGORY_TS_ARG, new Date());
        response.sendRedirect("admin_categories.jsp");
    }

    private void doInsertCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paramName = request.getParameter(Commands.CATEGORY_NAME_ARG);
        if (paramName != null && paramName.length() > 0) {
            switch (CategoryHandler.newCategory(paramName)) {
                case CategoryHandler.INSERT_OK:
                    doReloadPage(request, response);
                    break;
                case CategoryHandler.INSERT_FAILURE:
                    errorResponse(request, response, "Category already exists.", "admin_categories.jsp");
                    break;
                default:
                    errorResponse(request, response, "Unknown exception raised.", "admin_categories.jsp");
            }
        }
    }

    private void doDeleteCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paramId = request.getParameter(Commands.CATEGORY_ID_ARG);
        if (paramId != null && paramId.length() > 0) {
            int id = Integer.parseInt(paramId);
            switch (CategoryHandler.deleteCategory(id)) {
                case CategoryHandler.DELETE_OK:
                case CategoryHandler.DELETE_FAILURE:
                    doReloadPage(request, response);
                    break;
                default:
                    errorResponse(request, response, "Unknown exception raised.", "admin_categories.jsp");
            }
        }
    }

    private void doUpdateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paramId = request.getParameter(Commands.CATEGORY_ID_ARG);
        String paramName = request.getParameter(Commands.CATEGORY_NAME_ARG);
        if (paramId != null && paramId.length() > 0 && paramName != null && paramName.length() > 0) {
            int id = Integer.parseInt(paramId);
            HttpSession session = request.getSession();
            Date date = (Date) session.getAttribute(Commands.CATEGORY_TS_ARG);
            switch (CategoryHandler.updateCategoryName(id, paramName, date)) {
                case CategoryHandler.UPDATE_FAILURE:
                case CategoryHandler.UPDATE_OK:
                default:
                    // TODO: Currently updates, failed updates and exceptions all lead to the page being reloaded.
                    doReloadPage(request, response);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = Commands.translateRequestToCommand(request);
        if (command != null && command.length() > 0) {
            switch (command) {
                case Commands.CATEGORY_INSERT_COMMAND: doInsertCategory(request, response); break;
                case Commands.CATEGORY_DELETE_COMMAND: doDeleteCategory(request, response); break;
                case Commands.CATEGORY_UPDATE_COMMAND: doUpdateCategory(request, response); break;
                default:
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
