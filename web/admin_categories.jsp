<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="view.Commands" %>
<%@ page import="view.viewmodels.Category" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="view.viewmodels.User" %>
<%@ page import="model.handlers.CategoryHandler" %>
<%@ page import="java.util.List" %>
<%@ page import="view.controllers.ItemsServlet" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Manage Categories</title>
</head>
<body>
<!-- Redirects the user to login if not logged in -->

<%
    User user = (User) session.getAttribute(Commands.ARG_CURR_USER);
    if (user == null)
    {
        response.sendRedirect("login.jsp");
    }
    else
    {
        // Set current page
        session.setAttribute(Commands.ARG_CURR_PAGE, "admin_categories.jsp");
%>
        <!-- Logout -->
        <form method="post" action="Users">
            <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.LOGOUT_COMMAND%>>
            <input type="submit" value="logout">
        </form>
<%
        if (!user.isAdmin())
        {
%>
            Only Admins have access to this page!
<%
        }
        else {
%>
            <!-- Refresh Categories -->
            <form method="post" action="Items">
                <input type="hidden" name=<%=Commands.COMMAND%> value=<%=Commands.CMD_CATEGORY_GET_ALL%>>
                <input type="submit" value="Refresh">
            </form>

            <h1>New Category</h1>
            <form method="post" action="Items">
                <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.CMD_INSERT_CATEGORY%>>
                <input type="text" name=<%= Commands.CATEGORY_NAME_ARG%>>
                <input type="submit" value="insert">
                <div style="color: #FF0000;">${errorResponse}</div><br>
                </td>
            </form>
            <h1>Edit Categories</h1>
            <table>
                <th>ID</th>
                <th>Category</th>
                <th>Modification</th>
                <th>Operators</th>

                <%
                    List<Category> categories = (List<Category>) session.getAttribute(Commands.ARG_ALL_CATEGORIES);
                    if (categories != null) for (Category category : categories) {
                        %>
                            <tr>
                                <td> <%= category.getId() %> </td>
                                <td> <%= category.getName() %> </td>
                                <form method="post" action="Items">
                                    <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.CMD_UPDATE_CATEGORY%>>
                                    <input type="hidden" name=<%= Commands.CATEGORY_ID_ARG%> value=<%= category.getId()%>>
                                    <input type="hidden" name=<%= Commands.CATEGORY_NAME_ARG%> value=<%= category.getName()%>>
                                    <input type="hidden" name=<%= Commands.CATEGORY_VERSION_ARG%> value=<%= category.getVersion()%>>
                                    <td>
                                        <input type="text" name=<%= Commands.CATEGORY_NEW_NAME_ARG%> value=<%= category.getName()%>>
                                    </td>
                                    <td>
                                        <input type="submit" value="update">
                                    </td>
                                </form>
                                <form method="post" action="Items">
                                    <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.CMD_DELETE_CATEGORY%>>
                                    <input type="hidden" name=<%= Commands.CATEGORY_ID_ARG%> value=<%= category.getId()%>>
                                    <input type="hidden" name=<%= Commands.CATEGORY_NAME_ARG%> value=<%= category.getName()%>>
                                    <td>
                                        <input type="submit" value="delete">
                                    </td>
                                </form>
                                </td>
                            </tr>
                        <%
                    }
                    else {
                        // No categories found
                    }
                %>

            </table>

<%
        }
    }
%>

</body>
</html>
