<%@ page import="model.handlers.UsersHandler" %>
<%@ page import="view.viewmodels.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="view.Commands" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manage Users</title>
</head>
<body>
<%
    User user = (User) session.getAttribute(Commands.ARG_CURR_USER);
    if (user == null)
    {
        response.sendRedirect("login.jsp");
    }
    else
    {
        // Set current page
        session.setAttribute(Commands.ARG_CURR_PAGE, "admin_users.jsp");
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
        else
        {
%>
            <!-- Refresh Users -->
            <form method="post" action="Users">
                <input type="hidden" name=<%=Commands.COMMAND%> value=<%=Commands.CMD_USERS_GET_ALL%>>
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
                    List<User> users = (List<User>) session.getAttribute(Commands.ARG_ALL_USERS);
                    if (users != null) for (User u : users) {
                %>
                <tr>
                    <td><%= u.getName() %><td>
                    <td><%= u.getAccessRoles().toString() %><td>
                    <td>
                        <form method="post" action="Users">
                            <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.CMD_GOTO_EDIT_USER%>>
                            <input type="hidden" name=<%= Commands.ARG_USER_ID%> value=<%= u.getId()%>>
                            <input type="submit" value="Edit">
                        </form>
                    </td>
                    <td>
                    <form method="post" action="Users">
                        <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.CMD_DELETE_USER%>>
                        <input type="hidden" name=<%= Commands.ARG_USER_ID%> value=<%= u.getId()%>>
                        <input type="submit" value="delete">
                    </form>
                    </td>
                </tr>
                <%
                        }
                %>
            </table>
<%
        }
    }
%>
</body>
</html>
