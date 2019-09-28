<%@ page import="view.viewmodels.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
    <h1>Users:</h1>

    <table>
        <% ArrayList<User> userList = (ArrayList<User>) request.getAttribute("listData");
            for(User user : userList) {
                out.print(user.getName());
            }
        %>
    </table>
</body>
</html>
