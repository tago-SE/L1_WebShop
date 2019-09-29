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
    private static final String UNKNWON_EXCEPTION_MSG = "Unknown exception raised.";

    private void errorResponse(HttpServletRequest request,
                               HttpServletResponse response,
                               String msg,
                               String redirect) throws ServletException, IOException {

        request.setAttribute(Commands.ERROR_RESPONSE_COMMAND, msg);
        request.getRequestDispatcher(redirect).forward(request, response);
        System.out.println("ItemsServlet__errorResponse: " + msg);
    }

    private void doReloadPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute(Commands.CATEGORY_LIST_ARG, CategoryHandler.getCategories());
        session.setAttribute(Commands.CATEGORY_TS_ARG, new Date());
        response.sendRedirect("admin_categories.jsp");
    }

    private void doInsertCategory(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            User user,
            String... access
    ) throws ServletException, IOException {

        String paramName = request.getParameter(Commands.CATEGORY_NAME_ARG);
        if (paramName != null && paramName.length() > 0) {
            switch (CategoryHandler.newCategory(paramName, access)) {
                case CategoryHandler.INSERT_OK:
                    doReloadPage(request, response);
                    break;
                case CategoryHandler.INSERT_FAILURE:
                    errorResponse(request, response, INSERT_FAILURE_MSG, "admin_categories.jsp");
                    break;
                case CategoryHandler.ACCESS_DENIED:
                    errorResponse(request, response, ACCESS_DENIED_MSG, "admin_categories.jsp");
                    break;
                default:
                    errorResponse(request, response, UNKNWON_EXCEPTION_MSG, "admin_categories.jsp");
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
            String... access
    ) throws ServletException, IOException {

        String paramId = request.getParameter(Commands.CATEGORY_ID_ARG);
        String paramName = request.getParameter(Commands.CATEGORY_NAME_ARG);
        if (paramName != null && paramName.length() > 0) {
            System.out.println("Inserting: " + paramName + " " + paramName.length());
            switch (CategoryHandler.deleteCategory(paramName, access)) {
                case CategoryHandler.DELETE_OK:
                case CategoryHandler.DELETE_FAILURE:
                case CategoryHandler.ACCESS_DENIED:
                    doReloadPage(request, response);
                    break;
                default:
                    errorResponse(request, response, UNKNWON_EXCEPTION_MSG, "admin_categories.jsp");
            }
        }
    }

    private void doUpdateCategory(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            User user,
            String... access
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
                    doReloadPage(request, response);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = Commands.translateRequestToCommand(request);
        if (command != null && command.length() > 0) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Commands.CURR_USER_ARG);
            Collection<String> accessCollection = user.getAccessRoles();
            String[] access = accessCollection.toArray(new String[accessCollection.size()]);
            switch (command) {
                case Commands.CATEGORY_INSERT_COMMAND: doInsertCategory(request, response, session, user, access); break;
                case Commands.CATEGORY_DELETE_COMMAND: doDeleteCategory(request, response, session, user, access); break;
                case Commands.CATEGORY_UPDATE_COMMAND: doUpdateCategory(request, response, session, user, access); break;
                default:
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
