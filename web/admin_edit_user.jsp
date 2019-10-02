<%@ page import="view.viewmodels.User" %>
<%@ page import="static view.Commands.*" %>
<%@ page import="static view.Pages.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<%
    User user = (User) session.getAttribute(ARG_CURR_USER);
    if (user == null)
    {
        response.sendRedirect(LOGIN_JSP);
    }
    else {
        // Set current page
        session.setAttribute(ARG_CURR_PAGE, ADMIN_EDIT_USER_JSP);

        // User to edit
        User editUser = (User) session.getAttribute(USER_TO_EDIT_ARG);
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
