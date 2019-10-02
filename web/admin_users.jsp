<%@ page import="model.handlers.UsersHandler" %>
<%@ page import="view.viewmodels.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="static view.Pages.LOGIN_JSP" %>
<%@ page import="static view.Pages.ADMIN_USERS_JSP" %>
<%@ page import="static view.Commands.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manage Users</title>
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
        session.setAttribute(ARG_CURR_PAGE, ADMIN_USERS_JSP);
%>
        <!-- Logout -->
        <form method="post" action=<%=USERS_SERVLET%>>
            <input type="hidden" name=<%= COMMAND%> value=<%= LOGOUT_COMMAND%>>
            <input type="submit" value="logout">
        </form>

<%
        if (!user.isAdmin())
        {
%>
            Only Admins have access to this page!
<%
        }
        else
        {
%>
            <!-- Refresh Users -->
            <form method="post" action="Users">
                <input type="hidden" name=<%=COMMAND%> value=<%=CMD_USERS_GET_ALL%>>
                <input type="submit" value="Refresh">
            </form>
            <h1>Users</h1>
            <table>
                <th>username</th>
                <th></th>
                <th>Access</th>
                <th></th>
                <th>Actions</th>
                <%
                    List<User> users = (List<User>) session.getAttribute(ARG_ALL_USERS);
                    if (users != null) for (User u : users) {
                %>
                <tr>
                    <td><%= u.getName() %><td>
                    <td><%= u.getAccessRoles().toString() %><td>
                    <td>
                        <form method="post" action="Users">
                            <input type="hidden" name=<%=COMMAND%> value=<%=CMD_GOTO_EDIT_USER%>>
                            <input type="hidden" name=<%=ARG_USER_ID%> value=<%= u.getId()%>>
                            <input type="submit" value="Edit">
                        </form>
                    </td>
                    <td>
                    <form method="post" action="Users">
                        <input type="hidden" name=<%=COMMAND%> value=<%=CMD_DELETE_USER%>>
                        <input type="hidden" name=<%=ARG_USER_ID%> value=<%= u.getId()%>>
                        <input type="submit" value="delete">
                    </form>
                    </td>
                </tr>
                <%
                        }
                %>
                <div style="color: #FF0000;">${errorResponse}</div><br>
            </table>
<%
        }
    }
%>
</body>
</html>
