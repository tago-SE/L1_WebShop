<%@ page import="static view.Commands.*" %>
<%@ page import="view.viewmodels.User" %>
<%@ page import="java.util.List" %>
<%@ page import="view.viewmodels.Item" %>
<%@ page import="view.viewmodels.Category" %>
<%@ page import="static view.Pages.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manage Items</title>
</head>
<body>
<%
    User user = (User) session.getAttribute(ARG_CURR_USER);
    if (user == null)
    {
        response.sendRedirect(LOGIN_JSP);
    }
    else
    {
        // Set current page
        session.setAttribute(ARG_CURR_PAGE, ADMIN_ITEMS_JSP);
%>
<table>
    <td>
        <!-- Logout -->
        <form method="post" action=<%=USERS_SERVLET%>>
            <input type="hidden" name=<%=  COMMAND%> value=<%=  LOGOUT_COMMAND%>>
            <input type="submit" value="logout">
        </form>
    </td><td>
        <!-- Go Home -->
        <form method="post" action=<%=USERS_SERVLET%>>
            <input type="hidden" name=<%=  COMMAND%> value=<%=  GOTO_CMD%>>
            <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=HOME_JSP%>>
            <input type="submit" value="home">
        </form>
    </td>
<%
    if (user.isAdmin())
    {
%>
    <td>
        <!-- Refresh Categories -->
        <form method="post" action=<%=ITEMS_SERVLET%>>
            <input type="hidden" name=<%= COMMAND%> value=<%= ITEMS_GET_ALL_CMD%>>
            <input type="submit" value="Refresh">
        </form>
    </td>
    <td>
        <!-- New Item -->
        <form method="post" action=<%= ITEMS_SERVLET %>>
            <input type="hidden" name=<%= COMMAND%> value=<%= GOTO_UPSERT_ITEM_CMD%>>
            <input type="hidden" name=<%= ITEM_ID_ARG%> value=0>
            <input type="submit" value="New Item">
        </form>
    </td>
        <%
    }
%>
</table>
<%
        if (!user.isAdmin())
        {
            %> Only Admins have access to this page! <%
        }
        else {
%>
        <h3>Items</h3>
        <div style="color: #FF0000;">${errorResponse}</div><br>
            <table style="table-layout:fixed">
                <th>id</th>
                <th>name</th>
                <th>price</th>
                <th>stock</th>
                <th>categories</th>
<%
                    List<Item> items = (List<Item>) session.getAttribute(ITEMS_ARG);
                    if (items != null) for (Item item : items)
                    {
%>
                    <tr>
                    <td><%= item.getId() %><td>
                    <td><%= item.getName() %><td>
                    <td><%= item.getPrice() %><td>
                    <td><%= item.getQuantity() %><td>
                    <td>
<%
                        for (Category c : item.getCategories())
                        {
%>
                            <%= c.getName() %>
<%
                        }
%>
                    </td>
                    <td>
                        <form method="post" action=<%= ITEMS_SERVLET %>>
                            <input type="hidden" name=<%= COMMAND%> value=<%= GOTO_UPSERT_ITEM_CMD%>>
                            <input type="hidden" name=<%= ITEM_ID_ARG%> value=<%= item.getId()%>>
                            <input type="submit" value="Edit">
                        </form>
                    </td><td>
                        <form method="post" action=<%= ITEMS_SERVLET %>>
                            <input type="hidden" name=<%= COMMAND%> value=<%= DELETE_ITEM_CMD%>>
                            <input type="hidden" name=<%= ITEM_ID_ARG%> value=<%= item.getId()%>>
                            <input type="submit" value="delete">
                        </form>
                    </td>
                    </tr>


<%
                    }
%>

            </table>

<%
        }
    }
%>
</body>
</html>
