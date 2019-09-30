<%@ page import="view.viewmodels.User" %>
<%@ page import="view.Commands" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<%
    User user = (User) session.getAttribute(Commands.ARG_CURR_USER);
    if (user == null)
    {
        response.sendRedirect("login.jsp");
    }
    else {
        // Set current page
        session.setAttribute(Commands.ARG_CURR_PAGE, "admin_edit_user.jsp");

        // User to edit
        User editUser = (User) session.getAttribute(Commands.USER_TO_EDIT_ARG);
        if (editUser != null)
        {
        %>

            Editing User: <%= editUser.getName() %>

        <%
        }
        else
        {

        }
    }
%>



<!--
<table>
    <tr>
        <td>
            Customer
            <input type="checkbox" name="s1" value="1">
        </td>
        <td>
            Employee
            <input type="checkbox" name="s2">
        </td>
        <td>
            Admin
            <input type="checkbox" name="s3" value="2" checked='checked'>
        </td>
    </tr>
</table>
-->
</body>
</html>
