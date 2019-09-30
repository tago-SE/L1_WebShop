<%@ page import="static view.Commands.*" %>
<%@ page import="view.viewmodels.User" %>
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
        response.sendRedirect("login.jsp");
    }
    else
    {
        // Set current page
        session.setAttribute(ARG_CURR_PAGE, "admin_categories.jsp");
%>
        <!-- Logout -->
        <form method="post" action="Users">
            <input type="hidden" name=<%= COMMAND%> value=<%= LOGOUT_COMMAND%>>
            <input type="submit" value="logout">
        </form>
<%
        if (!user.isAdmin())
        {
            %> Only Admins have access to this page! <%
        }
        else {
%>
            <!-- Refresh Items -->
            <form method="post" action="Items">
                <input type="hidden" name=<%=COMMAND%> value=<%=ITEMS_GET_ALL_CMD%>>
                <input type="submit" value="Refresh">
            </form>

            <h1>New Item</h1>
            <form method="post" action="Items">
                <input type="hidden" name=<%= COMMAND%> value=<%= GOTO_INSERT_ITEM_CMD%>>
                <input type="hidden" name=<%= ITEM_ID_ARG%> value=0>
                <input type="submit" value="New Item">
            </form>

<%
        }
    }
%>
</body>
</html>
