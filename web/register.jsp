<%@ page import="view.Commands" %>
<%@ page import="static view.Pages.REGISTER_JSP" %>
<%@ page import="static view.Commands.USERS_SERVLET" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<%
    // Set current page
    session.setAttribute(Commands.ARG_CURR_PAGE, REGISTER_JSP);
%>
    <form method="post" action=<%=USERS_SERVLET%>>
        <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.REGISTER_COMMAND%>>
        Enter username : <input type="text" name = "username"></br>
        Enter password : <input type="password" name = "password"></br>
        Enter password : <input type="password" name = "password1"></br>
        <div style="color: #FF0000;">${errorResponse}</div><br>
        <input type="submit" value="register">
    </form>
    <a href = "login.jsp">Login</a><br>
</body>
</html>
