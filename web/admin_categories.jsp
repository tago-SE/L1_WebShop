<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="view.Commands" %>
<%@ page import="view.viewmodels.Category" %>
<%@ page import="view.viewmodels.User" %>
<%@ page import="java.util.List" %>
<%@ page import="static view.Commands.*" %>
<%@ page import="static view.Pages.*" %>
<%@ page import="static view.Pages.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Manage Categories</title>
</head>
<body>
<!-- Redirects the user to login if not logged in -->

<%
    User user = (User) session.getAttribute(ARG_CURR_USER);
    if (user == null)
    {
        response.sendRedirect("login.jsp");
    }
    else
    {
        // Set current page
        session.setAttribute( ARG_CURR_PAGE, "admin_categories.jsp");
%>
<table>
    <td>
        <!-- Logout -->
        <form method="post" action=<%=USERS_SERVLET%>>
            <input type="hidden" name=<%=  COMMAND%> value=<%=  LOGOUT_COMMAND%>>
            <input type="submit" value="logout">
        </form>
    </td>

    <td>
        <!-- Go Home -->
        <form method="post" action=<%=USERS_SERVLET%>>
            <input type="hidden" name=<%=  COMMAND%> value=<%=  GOTO_CMD%>>
            <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=HOME_JSP%>>
            <input type="submit" value="home">
    </form>
<%
    if (user.isAdmin())
    {
%>
        <td>
            <!-- Refresh Categories -->
            <form method="post" action=<%=CATEGORIES_SERVLET%>>
                <input type="hidden" name=<%= COMMAND%> value=<%= CMD_CATEGORY_GET_ALL%>>
                <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=ADMIN_CATEGORIES_JSP%>>
                <input type="submit" value="Refresh">
            </form>
        </td>
<%
    }
%>
    </td>
</table>

<%
        if (!user.isAdmin())
        {
%>
            Only Admins have access to this page!
<%
        }
        else {
%>
            <h3>New Category</h3>
            <form method="post" action="Categories">
                <input type="hidden" name=<%=COMMAND%> value=<%=CMD_INSERT_CATEGORY%>>
                <input type="text" name=<%=CATEGORY_NAME_ARG%>>
                <input type="submit" value="insert">
                <div style="color: #FF0000;">${errorResponse}</div><br>
                </td>
            </form>
            <h3>Edit Categories</h3>
            <table>
                <th>ID</th>
                <th>Category</th>
                <th>Modification</th>
                <th>Operators</th>

                <%
                    List<Category> categories = (List<Category>) session.getAttribute(CATEGORIES_ARG);
                    if (categories != null) for (Category category : categories) {
                        %>
                            <tr>
                                <td> <%= category.getId() %> </td>
                                <td> <%= category.getName() %> </td>
                                <form method="post" action="Categories">
                                    <input type="hidden" name=<%=  COMMAND%> value=<%=  CMD_UPDATE_CATEGORY%>>
                                    <input type="hidden" name=<%=  CATEGORY_ID_ARG%> value=<%= category.getId()%>>
                                    <input type="hidden" name=<%=  CATEGORY_NAME_ARG%> value=<%= category.getName()%>>
                                    <input type="hidden" name=<%=  CATEGORY_VERSION_ARG%> value=<%= category.getVersion()%>>
                                    <td>
                                        <input type="text" name=<%=  CATEGORY_NEW_NAME_ARG%> >
                                    </td>
                                    <td>
                                        <input type="submit" value="update">
                                    </td>
                                </form>
                                <form method="post" action="Categories">
                                    <input type="hidden" name=<%=  COMMAND%> value=<%=  CMD_DELETE_CATEGORY%>>
                                    <input type="hidden" name=<%=  CATEGORY_ID_ARG%> value=<%= category.getId()%>>
                                    <input type="hidden" name=<%=  CATEGORY_NAME_ARG%> value=<%= category.getName()%>>
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
