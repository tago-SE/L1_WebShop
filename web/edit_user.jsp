<%@ page import="view.viewmodels.User" %>
<%@ page import="static view.Commands.*" %>
<%@ page import="static view.Pages.*" %>
<%@ page import="model.UserRoles" %>
<%@ page import="org.apache.log4j.lf5.LogLevel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>

<div style="color:#00FF00;">${successResponse}</div><br>
<div style="color:#FF0000;">${errorResponse}</div><br>

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
        // Edit user if admin and not null
        if (editUser != null && user.isAdmin())
        {
        %>
            Editing User: <%= editUser.getName() %>
            <br>
            <form method="post" action=<%= USERS_SERVLET %>>
                <input type="hidden" name=<%= COMMAND%> value=<%=UPDATE_USER_CMD%>>
                <input type="hidden" name=<%= ARG_USER_ID%> value=<%= editUser.id%>>
                <input type="hidden" name=<%=REDIRECT_ARG%> value=<%=ADMIN_EDIT_USER_JSP%>>
                First name: <input type="text" name=<%=FIRST_NAME_ARG%> value=<%=editUser.firstName%>></br>
                Last name : <input type="text" name=<%=LAST_NAME_ARG%> value=<%=editUser.lastName%>></br>

                <%=UserRoles.ADMIN%>
                <input type="checkbox" name=<%=UserRoles.ADMIN%> value=<%=UserRoles.ADMIN%>>
                <%=UserRoles.CUSTOMER%>
                <input type="checkbox" name=<%=UserRoles.CUSTOMER%> value=<%=UserRoles.CUSTOMER%>>
                <%=UserRoles.STORAGE_WORKER%>
                <input type="checkbox" name=<%=UserRoles.STORAGE_WORKER%> value=<%=UserRoles.STORAGE_WORKER%>>
                <br>
                <input type="submit" value="Update">
            </form>
        <%
        }
        else
        {
            editUser = user;

            if (editUser.isAdmin()) {
                // show role checkbox for yourself...
            }

        }
    }
%>



</body>
</html>
