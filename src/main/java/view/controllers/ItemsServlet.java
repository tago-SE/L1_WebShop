package view.controllers;

import model.handlers.CategoriesHandler;
import model.handlers.ItemsHandler;
import view.viewmodels.Category;
import view.viewmodels.Item;
import view.viewmodels.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static view.Commands.*;
import static view.Pages.*;

@WebServlet(name = "Items")
public class ItemsServlet extends HttpServlet {

    private static final String ACCESS_DENIED_MSG = "Unauthorized access";
    private static final String INSERT_FAILURE_MSG = "Category already exists.";
    private static final String NO_NAME_MSG = "No name provided.";
    private static final String NO_PRICE_MSG = "No price provided.";
    private static final String NO_CATEGORY_MSG = "At least one category is needed.";
    private static final String COULD_NOT_READ_PRICE_MSG = "Failed to read price from client.";
    private static final String COULD_NOT_READ_AMOUNT_MSG = "Failed to read amount from client.";
    private static final String COULD_NOT_READ_ID_MSG = "Failed to read id from client.";
    private static final String COULD_NOT_READ_VER_MSG = "Failed to read version from client.";
    private static final String UNKNOWN_EXCEPTION_MSG = "Unknown exception raised.";
    private static final String SELECT_MIN_CAT_MSG = "Select at least one category.";

    private void errorResponse(HttpServletRequest request,
                               HttpServletResponse response,
                               String msg,
                               String redirect) throws ServletException, IOException {

        request.setAttribute(ERR_RESPONSE_ARG, msg);
        request.getRequestDispatcher(redirect).forward(request, response);
    }

    private void insertOrUpdateItem(
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response,
            List<String> access) throws ServletException, IOException {

        String nameParam = request.getParameter(ITEM_NAME_ARG);
        String idParam = request.getParameter(ITEM_ID_ARG);
        String verParam = request.getParameter(ITEM_VER_ARG);
        String priceParam = request.getParameter(ITEM_PRICE_ARG);
        String quantityParam = request.getParameter(ITEM_AMOUNT_ARG);
        if (nameParam == null || nameParam.length() == 0) {
            errorResponse(request, response, NO_NAME_MSG, EDIT_ITEM_JSP);
            return;
        }
        if (priceParam == null || priceParam.length() == 0) {
            errorResponse(request, response, NO_PRICE_MSG, EDIT_ITEM_JSP);
            return;
        }
        List<Category> categories = CategoriesHandler.getCategories();
        List<Category> selectedCategories = new ArrayList<>();
        for (Category category : categories) {
            String catParam = request.getParameter(category.getName());
            if (catParam != null && catParam.equals("1"))
                selectedCategories.add(category);
        }
        if (selectedCategories.size() == 0) {
            errorResponse(request, response, NO_CATEGORY_MSG, EDIT_ITEM_JSP);
            return;
        }
        int id = 0, version = 0, price = 0, quantity = 0;
        try {
            id = Integer.parseInt(idParam);
        } catch (Exception e) {
            errorResponse(request, response, COULD_NOT_READ_ID_MSG, EDIT_ITEM_JSP);
            return;
        }
        try {
            version = Integer.parseInt(verParam);
        } catch (Exception e) {
            errorResponse(request, response, COULD_NOT_READ_VER_MSG, EDIT_ITEM_JSP);
            return;
        }
        try {
            price = Integer.parseInt(priceParam);
        } catch (Exception e) {
            errorResponse(request, response, COULD_NOT_READ_PRICE_MSG, EDIT_ITEM_JSP);
            return;
        }
        if (quantityParam != null && quantityParam.length() > 0) {
            try {
                quantity = Integer.parseInt(quantityParam);
            } catch (Exception e) {
                errorResponse(request, response, COULD_NOT_READ_AMOUNT_MSG, EDIT_ITEM_JSP);
                return;
            }
        }
        Item newItem = new Item(id, version, nameParam, price, quantity, selectedCategories);
        if (id == 0) {
            try {
                ItemsHandler.newItem(newItem, access);
                response.sendRedirect(EDIT_ITEM_JSP);
            } catch (Exception e) {
                if (e instanceof IllegalAccessException)
                    errorResponse(request, response, ACCESS_DENIED_MSG, EDIT_ITEM_JSP);
                else {
                    e.printStackTrace();
                    errorResponse(request, response, UNKNOWN_EXCEPTION_MSG, EDIT_ITEM_JSP);
                }
            }
        } else {
            try {
                ItemsHandler.updateItem(newItem, access);
                // Return to admin items page regardless of success or failure
                getAdminItems(session, response);
            } catch (Exception e) {
                if (e instanceof IllegalAccessException) {
                    errorResponse(request, response, ACCESS_DENIED_MSG, EDIT_ITEM_JSP);
                } else {
                    e.printStackTrace();
                    errorResponse(request, response, UNKNOWN_EXCEPTION_MSG, EDIT_ITEM_JSP);
                }
            }
        }
    }

    private void gotoEditItem(HttpSession session,  HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = CategoriesHandler.getCategories();
        String idParam = request.getParameter(ITEM_ID_ARG);
        try {
            int id = Integer.parseInt(idParam);
            session.setAttribute(ITEM_ARG, null);
            if (id != 0) {
                Item item = ItemsHandler.getItemById(Integer.parseInt(idParam));
                session.setAttribute(ITEM_ARG, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.setAttribute(CATEGORIES_ARG, categories);
        response.sendRedirect(EDIT_ITEM_JSP);
    }

    private void getAdminItems(HttpSession session, HttpServletResponse response) throws IOException {
        session.setAttribute(ITEMS_ARG, ItemsHandler.getItems());
        response.sendRedirect(ADMIN_ITEMS_JSP);
    }

    private void deleteItem(HttpSession session,  List<String> access, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter(ITEM_ID_ARG);
        try {
            int id = Integer.parseInt(idParam);
            ItemsHandler.deleteItem(id, access);
            getAdminItems(session, response);
        } catch (Exception e) {
            if (e instanceof IllegalAccessException) {
                errorResponse(request, response, ACCESS_DENIED_MSG, ADMIN_ITEMS_JSP);
            } else {
                errorResponse(request, response, ACCESS_DENIED_MSG, ADMIN_ITEMS_JSP);
            }
        }
    }

    private void queryItemsByCategory(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = (List<Category>) session.getAttribute(CATEGORIES_ARG);
        List<String> queryList = new ArrayList<>();
        if (categories != null) {
            categories.forEach(category -> {
                String catParam = request.getParameter(category.getName());
                if (catParam != null && catParam.equals("1"))
                   queryList.add(category.getName());
            });
        }
        if (queryList.size() > 0) {
            String[] args = new String[queryList.size()];
            queryList.toArray(args);
            try {
                session.setAttribute(ITEMS_ARG, ItemsHandler.getItemsByCategories(args));
                response.sendRedirect(SHOP_JSP);
            } catch (Exception e) {
                e.printStackTrace();
                errorResponse(request, response, UNKNOWN_EXCEPTION_MSG, request.getParameter(REDIRECT_ARG));
            }
        } else {
            errorResponse(request, response, SELECT_MIN_CAT_MSG, request.getParameter(REDIRECT_ARG));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = translateRequestToCommand(request);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ARG_CURR_USER);
        List<String> access = (List<String>) user.getAccessRoles();
        if (command != null && command.length() > 0) {
            switch (command) {
                case GOTO_UPSERT_ITEM_CMD: gotoEditItem(session, request, response); break;
                case UPSERT_ITEM_CMD: insertOrUpdateItem(session, request, response, access); break;
                case ITEMS_GET_ALL_CMD: getAdminItems(session, response); break;
                case DELETE_ITEM_CMD: deleteItem(session, access, request, response); break;
                case QUERY_BY_CATEGORY_CMD: queryItemsByCategory(session, request, response); break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
