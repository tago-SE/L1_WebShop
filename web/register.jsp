<%--
  Created by IntelliJ IDEA.
  view.viewmodels.User: tiago
  Date: 2019-09-25
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="view.Commands" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <form method="post" action="Users">
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
