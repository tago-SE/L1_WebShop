<%--
  Created by IntelliJ IDEA.
  User: tiago
  Date: 2019-09-28
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="view.Commands" %>
<%@ page import="view.viewmodels.Category" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="view.viewmodels.User" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Manage Categories</title>
</head>
<body>
<!-- Redirects the user to login if not logged in -->

<%
    User user = (User) session.getAttribute(Commands.CURR_USER_ARG);
    if (user == null)
    {
        response.sendRedirect("login.jsp");
    }
    else
    {
        %>
        <!-- Logout -->
        <form method="post" action="Users">
            <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.LOGOUT_COMMAND%>>
            <input type="submit" value="logout">
        </form>
        <%
        if (user.getRole() == null || !user.getRole().equals("Admin"))
        {
        %>
            Only Admins have access to this page!
            <%
        }
        else {
            %>
            <h1>New Category</h1>
            <form method="post" action="Items">
                <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.CATEGORY_INSERT_COMMAND%>>
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
                    ArrayList<Category> categories = (ArrayList<Category>) session.getAttribute(Commands.CATEGORY_LIST_ARG);
                    if (categories != null)
                    {
                        for(Category category : categories)
                        {
                %>
                            <tr>
                                <td> <%= category.getId() %> </td>
                                <td> <%= category.getName() %> </td>
                                <form method="post" action="Items">
                                    <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.CATEGORY_UPDATE_COMMAND%>>
                                    <input type="hidden" name=<%= Commands.CATEGORY_ID_ARG%> value=<%= category.getId()%>>
                                    <input type="hidden" name=<%= Commands.CATEGORY_NAME_ARG%> value=<%= category.getName()%>>
                                    <td>
                                        <input type="text" name=<%= Commands.CATEGORY_NAME_ARG%> value=<%= category.getName()%>>
                                    </td>
                                    <td>
                                        <input type="submit" value="update">
                                    </td>
                                </form>

                                <form method="post" action="Items">
                                    <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.CATEGORY_DELETE_COMMAND%>>
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
                    }
                %>
            </table>
    <%
        }
    }
    %>
</body>
</html>
