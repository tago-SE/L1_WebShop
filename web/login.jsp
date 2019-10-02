<%@ page import="view.Commands" %>
<%@ page import="static view.Commands.USERS_SERVLET" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <%
        // Set current page
        session.setAttribute(Commands.ARG_CURR_PAGE, "login.jsp");
    %>
    <form method="post" action=<%=USERS_SERVLET%>>
        <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.LOGIN_COMMAND%>>
        Enter username : <input type="text" name=<%= Commands.USER_NAME_ARG%>></br>
        Enter password : <input type="password" name=<%= Commands.USER_PASS_ARG%>></br></br>
        <div style="color: #FF0000;">${errorResponse}</div><br>
        <input type="submit" value="login">
    </form>
    <a href = "register.jsp">Register</a><br>
</body>
</html>
