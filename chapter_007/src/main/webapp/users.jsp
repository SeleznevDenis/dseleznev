<%@ page import="ru.job4j.servlets.User" %>
<%@ page import="ru.job4j.servlets.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>
${message}
<table>
    <caption>All Users</caption>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Login</th>
        <th>Email</th>
    </tr>
    <%for (User usr : ValidateService.getInstance().findAll()) {%>
    <tr>
        <td><%=usr.getId()%></td>
        <td><%=usr.getName()%></td>
        <td><%=usr.getLogin()%></td>
        <td><%=usr.getEmail()%></td>
        <td>
            <form action = "<%=request.getContextPath()%>/edit" method="get">
                <input type="hidden" name="id" value="<%=usr.getId()%>"/>
                <input type="submit" value="Edit"/>
            </form>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/list" method="post">
                <input type="hidden" name="id" value="<%=usr.getId()%>"/>
                <input type="submit" value="Delete"/>
            </form>
        </td>
    </tr>
    <%}%>
</table>
</body>
</html>
