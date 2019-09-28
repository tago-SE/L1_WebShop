<%--
  Created by IntelliJ IDEA.
  model.User: tiago
  Date: 2019-09-25
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="view.Commands" %>
<%@ page import="view.viewmodels.Category" %>
<%@ page import="java.util.ArrayList" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    else
    {
    %>

        Welcome ${username}

        </br>
        </br>
        <table>
            <TR>
                <TH>Categories</TH>
            </TR>
            <%
                ArrayList<Category> categories = (ArrayList<Category>) session.getAttribute("categories");
                if (categories != null) {
                    for(Category category : categories) {
                %>
                    <TR>
                    <TD> <%= category.getName() %> </TD>
                    </TR>
                <%
                    }
                }
            %>
        </table>
        </br>
        </br>
        <form method="post" action="Users">
            <input type="hidden" name=<%= Commands.COMMAND%> value=<%= Commands.LOGOUT_COMMAND%>>
            <input type="submit" value="logout">
        </form>

    <%
    }
    %>
</body>
</html>
