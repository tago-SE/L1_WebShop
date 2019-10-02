<%@ page import="view.viewmodels.User" %>
<%@ page import="static view.Commands.*" %>
<%@ page import="static view.Pages.*" %>
<%@ page import="view.viewmodels.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="view.viewmodels.Item" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Item</title>
</head>
<body>
<%
    User user = (User) session.getAttribute(ARG_CURR_USER);
    if (user == null)
    {
        response.sendRedirect(LOGIN_JSP);
        return;
    }
%>
    <h1>Edit Item</h1>
    <div style="color: #FF0000;">${errorResponse}</div><br>
    <form method="post" action="Items">
        <input type="hidden" name=<%= COMMAND%> value=<%= UPSERT_ITEM_CMD%>>
<%
        Item item = (Item) session.getAttribute(ITEM_ARG);
        if (item == null)
        {
%>
            <input type="hidden" name=<%= ITEM_ID_ARG%> value=<%= 0 %>>
            <input type="hidden" name=<%= ITEM_VER_ARG%> value=<%= 0 %>>
            Name:
            <br>
            <input type="text" name=<%= ITEM_NAME_ARG%>>
            <br><br>
            Price:
            <br>
            <input type="number" name=<%= ITEM_PRICE_ARG%>>
            <br><br>
            Quantity:
            <br>
            <input type="number" name=<%= ITEM_AMOUNT_ARG%>>
            <br><br>
            Categories:
            <table>
<%
            List<Category> categories = (List<Category>) session.getAttribute(CATEGORIES_ARG);
            if (categories != null) for (Category category : categories) {
%>
                <tr><td>
                    <%= category.getName()%>
                    <input type="checkbox" name=<%= category.getName()%> value="1">
                </td><tr>
<%
            }
%>
            </table>
<%
        }
        else
        {
%>
            <input type="hidden" name=<%= ITEM_ID_ARG%> value=<%= item.getId() %>>
            <input type="hidden" name=<%= ITEM_VER_ARG%> value=<%= item.getVersion() %>>
            Name (<%= item.getName() %>):
            <br>
            <input type="text" name=<%= ITEM_NAME_ARG%>>
            <br><br>
            Price (<%= item.getPrice() %>)
            <br>
            <input type="number" name=<%= ITEM_PRICE_ARG%>>
            <br><br>
            Stock (<%= item.getQuantity() %>)
            <br>
            <input type="number" name=<%= ITEM_AMOUNT_ARG%>>
            <br><br>
            Categories:
            <table>
<%
            List<Category> categories = (List<Category>) session.getAttribute(CATEGORIES_ARG);
            if (categories != null) for (Category category : categories)
            {
                if (item.hasCategory(category))
                {
%>
                    <tr><td>
                    <%= category.getName()%>
                    <input type="checkbox" checked="checked" name=<%= category.getName()%> value="1">
                    </td></tr>
<%
                }
                else
                {
%>
                    <tr><td>
                    <%= category.getName()%>
                    <input type="checkbox" name=<%= category.getName()%> value="1">
                    </td></tr>
<%
                }
            }
%>
            </table>
<%
        }
%>
        <br>
        <input type="submit" value="commit">
    </form>
</body>
</html>
