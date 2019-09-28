<%--
  Created by IntelliJ IDEA.
  model.User: tiago
  Date: 2019-09-25
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="view.Commands" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form method="post" action="Users">
        <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.LOGIN_COMMAND%>>
        Enter username : <input type="text" name = "username"></br>
        Enter password : <input type="password" name = "password"></br>
        <div style="color: #FF0000;">${errorMessage}</div><br>
        <input type="submit" value="login">
    </form>
    <a href = "register.jsp">Register</a><br>
</body>
</html>
