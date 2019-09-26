<%--
  Created by IntelliJ IDEA.
  User: tiago
  Date: 2019-09-25
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <form action="Register">
        Enter username : <input type="text" name = "username"></br>
        Enter password : <input type="password" name = "password"></br>
        Enter password : <input type="password" name = "password1"></br>
        <div style="color: #FF0000;">${errorMessage}</div><br>
        <input type="submit" value="register">
    </form>
    <a href = "login.jsp">Login</a><br>
</body>
</html>
