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



</body>
</html>
