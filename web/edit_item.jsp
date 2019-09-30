<%@ page import="view.viewmodels.User" %>
<%@ page import="static view.Commands.*" %>
<%@ page import="static view.Pages.*" %>
<%@ page import="view.viewmodels.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Item</title>
</head>
<body>
<%
    User user = (User) session.getAttribute(ARG_CURR_USER);
    if (user == null)
    {
        response.sendRedirect(LOGIN_JSP);
        return;
    }
    List<Category> categories = (List<Category>) session.getAttribute(CATEGORIES_ARG);
    if (categories != null)
    {

    }
%>
    <form method="post" action="Items">
        <input type="hidden" name=<%= COMMAND%> value=<%= UPSERT_ITEM_CDM%>>
        <input type="text" name=<%= ITEM_NAME_ARG%>>
        <table>
            <tr><td>
                Customer
                <input type="checkbox" name="s1" value="1">
            </td><tr>
        </table>
        <input type="submit" value="commit">
    </form>


</body>
</html>
