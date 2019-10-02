<%@ page import="view.viewmodels.Item" %>
<%@ page import="java.util.List" %>
<%@ page import="static view.Commands.*" %>
<%@ page import="static view.Pages.*" %>
<%@ page import="view.viewmodels.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Items</title>
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
            <form method="post" action=<%=SHOPPING_SERVLET%>>
                <input type="hidden" name=<%=COMMAND%> value=<%=GOTO_CMD%>>
                <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=SHOPPING_CART_JSP%>>
                <input type="submit" value="Open Shopping Cart">
            </form>
        </tr>
    </table>

    <table>
        <th>Name</th><th>Price</th><th>Stock</th>
<%
        List<Item> items = (List<Item>) session.getAttribute(ITEMS_ARG);
        if (items != null) for (Item item : items)
        {
%>
            <tr>
                <td> <%= item.getName() %> </td>
                <td> <%= item.getPrice() %> </td>
                <td> <%= item.getQuantity() %> </td>
                <td>
                    <form method="post" action=<%=SHOPPING_SERVLET%>>
                        <input type="hidden" name=<%=COMMAND%> value=<%=ADD_TO_CART_CMD%>>
                        <input type="hidden" name=<%=ITEM_ID_ARG%> value=<%=item.getId()%>>
                        <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=SHOP_JSP%>>
                        <input type="submit" value="Add to cart">
                    </form>
                </td>
            </tr>
<%
        }
%>
    </table>
</body>
</html>
