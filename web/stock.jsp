<%@ page import="view.viewmodels.User" %>
<%@ page import="static view.Pages.*" %>
<%@ page import="static view.Commands.*" %>
<%@ page import="view.viewmodels.Item" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Stock</title>
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
<%
    if (!user.isAdmin() && !user.isStorageWorker())
    {
        %>Only Admins and staff have access to this page!<%
        return;
    }
%>

<div style="color:#00FF00;">${successResponse}</div><br>
<div style="color:#FF0000;">${errorResponse}</div><br>
<table>
    <tr>
        <!-- Logout -->
        <form method="post" action="Users">
            <input type="hidden" name=<%=  COMMAND%> value=<%=  LOGOUT_COMMAND%>>
            <input type="submit" value="logout">
        </form>
        <!-- Go Home -->
        <form method="post" action=<%=USERS_SERVLET%>>
            <input type="hidden" name=<%=  COMMAND%> value=<%=  GOTO_CMD%>>
            <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=HOME_JSP%>>
            <input type="submit" value="home">
        </form>
    </tr><tr>
    <form method="post" action=<%=ITEMS_SERVLET%>>
        <input type="hidden" name=<%=COMMAND%> value=<%=ITEMS_GET_ALL_CMD%>>
        <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=STOCK_JSP%>>
        <input type="submit" value="Refresh">
    </form>
</tr>
</table>

<table>
    <th>id</th><th>Item</th><th>amount</th>
<%
        List<Item> items = (List<Item>) session.getAttribute(ITEMS_ARG);
        if (items != null) for (Item item : items)
        {
%>
        <tr>
            <td><%= item.getId() %><td>
            <td><%= item.getName() %><td>
            <form method="post" action=<%=ITEMS_SERVLET%>>
                <input type="hidden" name=<%=COMMAND%> value=<%=EDIT_ITEM_STOCK_CMD%>>
                <input type="hidden" name=<%=ITEM_ID_ARG%> value=<%=item.getId()%>>
                <input type="number" name=<%=ITEM_AMOUNT_ARG%> value=<%=item.getQuantity()%>>
                <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=STOCK_JSP%>>
                <input type="submit" value="Edit">
            </form>
            <td>
        </tr>
<%
        }
%>
</table>
</body>
</html>
