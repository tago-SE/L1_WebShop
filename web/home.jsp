<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="view.Commands" %>
<%@ page import="view.viewmodels.Category" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="view.viewmodels.User" %>
<%@ page import="static view.Commands.*" %>
<%@ page import="static view.Pages.HOME_JSP" %>
<%@ page import="java.util.List" %>
<%@ page import="static view.Pages.LOGIN_JSP" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <!-- Redirects the user to login if not logged in -->
    <%
        User user = (User) session.getAttribute(Commands.ARG_CURR_USER);
    if (user == null) {
        response.sendRedirect(LOGIN_JSP);
    }
    else
    {
        // Set current page
        session.setAttribute(Commands.ARG_CURR_PAGE, HOME_JSP);
    %>

        <!-- Logout -->
        <form method="post" action=<%=USERS_SERVLET%>>
            <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.LOGOUT_COMMAND%>>
            <input type="submit" value="logout">
        </form>

        Welcome <%= user.getName() %>

        <%
            if (user.isAdmin())
            {
                %>
                <h3>Admin Control</h3>
                    <a href = "admin_categories.jsp">Categories</a><br>
                    <a href = "admin_items.jsp">Items</a><br>
                    <a href = "admin_users.jsp">Users</a><br>
                <%
            }
        %>


        <!-- Refresh Categories -->
        </br>
        </br>
        <form method="post" action="Categories">
            <input type="hidden" name=<%=COMMAND%> value=<%=CMD_CATEGORY_GET_ALL%>>
            <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=HOME_JSP%>>
            <input type="submit" value="Refresh Categories">
        </form>
        </br>
        </br>
        <form method="post" action="Items">
            <input type="hidden" name=<%=COMMAND%> value=<%=QUERY_BY_CATEGORY_CMD%>>
            <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=HOME_JSP%>>
            <input type="submit" value="Query">
            </br>
            <div style="color: #FF0000;">${errorResponse}</div><br>
            </br>
            <table>
                <TH>Categories</TH>
<%
            List<Category> categories = (List<Category>) session.getAttribute(CATEGORIES_ARG);
            if (categories != null) for (Category category : categories)
            {   %>
                <tr><td>
                    <%= category.getName()%>
                    <input type="checkbox" name=<%= category.getName()%> value="1">
                </td></tr>
<%          }
%>
            </table>
        </form>
        </br>
        </br>
    <%
    }
    %>
</body>
</html>
