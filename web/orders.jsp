<%@ page import="static view.Commands.*" %>
<%@ page import="view.viewmodels.User" %>
<%@ page import="static view.Pages.*" %>
<%@ page import="view.viewmodels.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="view.viewmodels.OrderItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
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
%>
        Only Admins and staff have access to this page! <%
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
        <form method="post" action=<%=SHOPPING_SERVLET%>>
        <input type="hidden" name=<%=COMMAND%> value=<%=GET_ALL_ORDERS_CMD%>>
        <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=ORDERS_JSP%>>
        <br>
            <!--
        <select name="Sort by">
            <option name=<%=DELIVERED_SORT_ARG%> value="1">Delivered</option>
            <option name=<%=UNDELIVERED_SORT_ARG%> value="1">Undelivered</option>
        </select>
        -->
        <input type="submit" value="Refresh">
    </form>
</tr>
</table>

<table>
    <th>id</th><th>username</th><th>status</th><th>cost</th><th>sent</th><th>delivered</th>

<%
    List<Order> orders = (List<Order>) session.getAttribute(ORDERS_ARG);
    if (orders != null)
    {
        for (Order order : orders)
        {
%>
        <tr>
            <td><%=order.id%></td>
            <td><%=order.user.name%></td>
            <td><%=order.status%></td>
            <td><%=order.cost%></td>
            <td><%=order.sent%></td>
            <td><%=order.delivered%></td>
            <%
                if (!order.isDelivered()) {
            %>
            <form method="post" action=<%=SHOPPING_SERVLET%>>
                <input type="hidden" name=<%=COMMAND%> value=<%=DELIVER_ORDER_CMD%>>
                <input type="hidden" name=<%=ORDER_ID_ARG%> value=<%=order.id%>>
                <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=ORDERS_JSP%>>
                <input type="submit" value="Deliver">
            <%
                }
            %>
        </tr>
        <tr>
        <table>
<%
            for (OrderItem item : order.orderItems)
            {
%>
                <tr>[(<%=item.item.id%>)<%=item.item.name%> x<%=item.amount%>]</tr>
<%
            }
%>
        </table>
        </tr>
<%
        }
    }
%>
</table>
</body>
</html>
