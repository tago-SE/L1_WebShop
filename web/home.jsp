<%--
  Created by IntelliJ IDEA.
  User: tiago
  Date: 2019-09-25
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <!-- Redirects the user to login if not logged in -->
    <%
        if (session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
        }
    %>
    Welcome ${username}
    <form action="Logout">
        <input type="submit" value="logout">
    </form>
</body>
</html>
