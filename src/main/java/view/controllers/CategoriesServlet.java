package view.controllers;

import model.handlers.CategoriesHandler;
import model.handlers.CategoriesHandler;
import model.handlers.UsersHandler;
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

import static view.Commands.*;
import static view.Pages.*;

@WebServlet(name = "Categories")
public class CategoriesServlet extends HttpServlet {

    private static final String ACCESS_DENIED_MSG = "Unauthorized access";
    private static final String INSERT_FAILURE_MSG = "Category already exists.";
    private static final String NO_CATEGORY_NAME_MSG = "No name provided.";
    private static final String UNKNOWN_EXCEPTION_MSG = "Unknown exception raised.";


    private void errorResponse(HttpServletRequest request,
                               HttpServletResponse response,
                               String msg,
                               String redirect) throws ServletException, IOException {

        request.setAttribute(ERR_RESPONSE_ARG, msg);
        request.getRequestDispatcher(redirect).forward(request, response);
    }

    private void doReloadPage(HttpSession session, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = CategoriesHandler.getCategories();
        session.setAttribute(CATEGORIES_ARG, categories);
        response.sendRedirect(ADMIN_CATEGORIES_JSP);
    }

    private void doInsertCategory(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            User user,
            List<String> access
    ) throws ServletException, IOException {

        String paramName = request.getParameter( CATEGORY_NAME_ARG);
        if (paramName != null && paramName.length() > 0) {
            switch (CategoriesHandler.newCategory(paramName, access)) {
                case CategoriesHandler.INSERT_OK:
                    doReloadPage(session, response);
                    break;
                case CategoriesHandler.INSERT_FAILURE:
                    errorResponse(request, response, INSERT_FAILURE_MSG, "admin_categories.jsp");
                    break;
                case CategoriesHandler.ACCESS_DENIED:
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
        String paramId = request.getParameter( CATEGORY_ID_ARG);
        String paramName = request.getParameter( CATEGORY_NAME_ARG);
        if (paramName != null && paramName.length() > 0) {
            switch (CategoriesHandler.deleteCategory(Integer.parseInt(paramId), access)) {
                case CategoriesHandler.DELETE_OK:
                case CategoriesHandler.DELETE_FAILURE:
                case CategoriesHandler.ACCESS_DENIED:
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

        String paramId = request.getParameter( CATEGORY_ID_ARG);
        String paramName = request.getParameter( CATEGORY_NAME_ARG);
        String paramVersion =  request.getParameter( CATEGORY_VERSION_ARG);
        String paramNewName = request.getParameter( CATEGORY_NEW_NAME_ARG);
        if (paramId != null && paramId.length() > 0 && paramName != null && paramName.length() > 0 && paramVersion != null && paramVersion.length() > 0) {
            Category category = new Category(Integer.parseInt(paramId), paramNewName, Integer.parseInt(paramVersion));
            switch (CategoriesHandler.updateCategory(category, access)) {
                case CategoriesHandler.UPDATE_FAILURE:
                case CategoriesHandler.UPDATE_OK:
                case CategoriesHandler.ACCESS_DENIED:
                default:
                    doReloadPage(session, response);
            }
        }
    }

    private void doGetCategories(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirect = request.getParameter(REDIRECT_ARG);
        List<Category> categories = CategoriesHandler.getCategories();
        session.setAttribute(CATEGORIES_ARG, categories);
        response.sendRedirect(redirect);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command =  translateRequestToCommand(request);
        if (command != null && command.length() > 0) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute( ARG_CURR_USER);
            List<String> access = (List<String>) user.getAccessRoles();
            switch (command) {
                case CMD_INSERT_CATEGORY: doInsertCategory(request, response, session, user, access); break;
                case CMD_DELETE_CATEGORY: doDeleteCategory(request, response, session, user, access); break;
                case CMD_UPDATE_CATEGORY: doUpdateCategory(request, response, session, user, access); break;
                case CMD_CATEGORY_GET_ALL: doGetCategories(session, request, response); break;
                default:
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
