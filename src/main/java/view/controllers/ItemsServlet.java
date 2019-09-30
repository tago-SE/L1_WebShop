package view.controllers;

import model.handlers.CategoryHandler;
import model.handlers.UsersHandler;
import view.Commands;
import view.viewmodels.Category;
import view.viewmodels.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ItemsServlet")
public class ItemsServlet extends HttpServlet {

    private static final String ACCESS_DENIED_MSG = "Unauthorized access";
    private static final String INSERT_FAILURE_MSG = "Category already exists.";
    private static final String NO_CATEGORY_NAME_MSG = "No name provided.";
    private static final String UNKNOWN_EXCEPTION_MSG = "Unknown exception raised.";


    private void errorResponse(HttpServletRequest request,
                               HttpServletResponse response,
                               String msg,
                               String redirect) throws ServletException, IOException {

        request.setAttribute(Commands.ERROR_RESPONSE_COMMAND, msg);
        request.getRequestDispatcher(redirect).forward(request, response);
        System.out.println("ItemsServlet__errorResponse: " + msg);
    }

    private void doReloadPage(HttpSession session, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = CategoryHandler.getCategories();
        session.setAttribute(Commands.CMD_CATEGORY_GET_ALL, categories);
        response.sendRedirect("admin_categories.jsp");
    }

    private void doInsertCategory(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            User user,
            List<String> access
    ) throws ServletException, IOException {

        String paramName = request.getParameter(Commands.CATEGORY_NAME_ARG);
        if (paramName != null && paramName.length() > 0) {
            switch (CategoryHandler.newCategory(paramName, access)) {
                case CategoryHandler.INSERT_OK:
                    doReloadPage(session, response);
                    break;
                case CategoryHandler.INSERT_FAILURE:
                    errorResponse(request, response, INSERT_FAILURE_MSG, "admin_categories.jsp");
                    break;
                case CategoryHandler.ACCESS_DENIED:
                    errorResponse(request, response, ACCESS_DENIED_MSG, "admin_categories.jsp");
                    break;
                default:
                    errorResponse(request, response, UNKNOWN_EXCEPTION_MSG, "admin_categories.jsp");
            }
        } else {
            errorResponse(request, response, NO_CATEGORY_NAME_MSG, "admin_categories.jsp");
        }
    }

    private void doDeleteCategory(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            User user,
            List<String> access
    ) throws ServletException, IOException {
        String paramId = request.getParameter(Commands.CATEGORY_ID_ARG);
        String paramName = request.getParameter(Commands.CATEGORY_NAME_ARG);
        if (paramName != null && paramName.length() > 0) {
            switch (CategoryHandler.deleteCategory(paramName, access)) {
                case CategoryHandler.DELETE_OK:
                case CategoryHandler.DELETE_FAILURE:
                case CategoryHandler.ACCESS_DENIED:
                    doReloadPage(session, response);
                    break;
                default:
                    errorResponse(request, response, UNKNOWN_EXCEPTION_MSG, "admin_categories.jsp");
            }
        }
    }

    private void doUpdateCategory(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            User user,
            List<String> access
    ) throws ServletException, IOException {

        String paramId = request.getParameter(Commands.CATEGORY_ID_ARG);
        String paramName = request.getParameter(Commands.CATEGORY_NAME_ARG);
        String paramVersion =  request.getParameter(Commands.CATEGORY_VERSION_ARG);
        String paramNewName = request.getParameter(Commands.CATEGORY_NEW_NAME_ARG);
        if (paramId != null && paramId.length() > 0 && paramName != null && paramName.length() > 0 && paramVersion != null && paramVersion.length() > 0) {
            Category category = new Category(Integer.parseInt(paramId), paramNewName, Integer.parseInt(paramVersion));
            switch (CategoryHandler.updateCategory(category, access)) {
                case CategoryHandler.UPDATE_FAILURE:
                case CategoryHandler.UPDATE_OK:
                case CategoryHandler.ACCESS_DENIED:
                default:
                    doReloadPage(session, response);
            }
        }
    }

    private void doGetCategories(HttpSession session, HttpServletResponse response) throws IOException {
        List<Category> categories = CategoryHandler.getCategories();
        session.setAttribute(Commands.ARG_ALL_CATEGORIES, categories);
        response.sendRedirect("admin_categories.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = Commands.translateRequestToCommand(request);
        if (command != null && command.length() > 0) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Commands.ARG_CURR_USER);
            List<String> access = (List<String>) user.getAccessRoles();
            switch (command) {
                case Commands.CMD_INSERT_CATEGORY: doInsertCategory(request, response, session, user, access); break;
                case Commands.CMD_DELETE_CATEGORY: doDeleteCategory(request, response, session, user, access); break;
                case Commands.CMD_UPDATE_CATEGORY: doUpdateCategory(request, response, session, user, access); break;
                case Commands.CMD_CATEGORY_GET_ALL: doGetCategories(session, response); break;
                default:
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
