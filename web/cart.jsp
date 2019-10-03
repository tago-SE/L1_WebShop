<%@ page import="static view.Commands.*" %>
<%@ page import="view.viewmodels.Cart" %>
<%@ page import="static view.Pages.SHOPPING_CART_JSP" %>
<%@ page import="static view.Pages.LOGIN_JSP" %>
<%@ page import="view.viewmodels.User" %>
<%@ page import="static view.Pages.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Cart</title>
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
</table>
<table>
    <th>Name</th><th>Price</th><th>Cost</th><th>stock</th><th>amount</th>
    <br>
    <br>
    <div style="color:#00FF00;">${successResponse}</div><br>
    <div style="color:#FF0000;">${errorResponse}</div><br>
    <tr>
    <form method="post" action=<%=SHOPPING_SERVLET%>>
        <input type="hidden" name=<%=COMMAND%> value=<%=GOTO_CMD%>>
        <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=SHOP_JSP%>>
        <input type="submit" value="Close Shopping Cart">
    </form>
    </tr><tr>
    <form method="post" action=<%=SHOPPING_SERVLET%>>
        <input type="hidden" name=<%=COMMAND%> value=<%=CUSTOMER_ORDER_CMD%>>
        <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=SHOPPING_CART_JSP%>>
        <input type="submit" value="Order">
    </form></tr>
<%
        Cart cart = (Cart) session.getAttribute(CART_ARG);
        if (cart != null)
        {
            for (Cart.CartItem item : cart.items)
            {
%>
        <tr>
            <td><%=item.name%></td>
            <td><%=item.price%></td>
            <td><%=item.cost%></td>
            <td><%=item.stock%></td>
            <td>
                <form method="post" action=<%=SHOPPING_SERVLET%>>
                    <input type="hidden" name=<%=COMMAND%> value=<%=EDIT_CART_ITEM_ARG%>>
                    <input type="hidden" name=<%=ITEM_ID_ARG%> value=<%=item.item.getId()%>>
                    <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=SHOPPING_CART_JSP%>>
                    <input type="number" name=<%=ITEM_AMOUNT_ARG%> value=<%=item.amount%>>
                    <input type="submit" value="Edit">
                </form>
            </td>
            <td>
                <form method="post" action=<%=SHOPPING_SERVLET%>>
                    <input type="hidden" name=<%=COMMAND%> value=<%=REMOVE_FROM_CART_CMD%>>
                    <input type="hidden" name=<%=ITEM_ID_ARG%> value=<%=item.item.getId()%>>
                    <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=SHOPPING_CART_JSP%>>
                    <input type="submit" value="Drop">
                </form>
            </td>
        </tr>
<%
            }
%>
        <tr>
            <td>
                Total Cost: <%=cart.cost%>
            </td>
        </tr>
<%
        }
%>
</table>
</body>
</html>
