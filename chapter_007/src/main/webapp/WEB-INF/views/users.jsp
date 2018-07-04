<%@ page import="ru.job4j.servlets.User" %>
<%@ page import="ru.job4j.servlets.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>
<c:out value="${message}"/>
<table>
    <caption>All Users</caption>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Login</th>
        <th>Email</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.name}"/></td>
            <td><c:out value="${user.login}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td>
                <form action = "${pageContext.servletContext.contextPath}/edit" method="get">
                    <input type="hidden" name="id" value="${user.id}"/>
                    <input type="submit" value="Edit"/>
                </form>
            </td>
            <td>
                <form action="${pageContext.servletContext.contextPath}/list" method="post">
                    <input type="hidden" name="id" value="${user.id}"/>
                    <input type="submit" value="Delete"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
