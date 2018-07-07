<%@ page import="ru.job4j.servlets.User" %>
<%@ page import="ru.job4j.servlets.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>
<c:out value="You are authorized as a ${sessionScope.loginRole.login}. Your role: ${sessionScope.loginRole.role}"/>
<form action="${pageContext.servletContext.contextPath}/signin" method="post">
    <input type="hidden" name="action" value="signout">
    <input type="submit" value="Выход"/>
</form>
<c:if test="${message != ''}">
    <div style="background-color: dodgerblue">
        <c:out value="${message}"/>
    </div>
</c:if>
<c:if test="${error != ''}">
    <div style="background-color: red">
        <c:out value="${error}"/>
    </div>
</c:if>
<table>
    <caption>All Users</caption>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Login</th>
        <th>Email</th>
        <th>Role</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.name}"/></td>
            <td><c:out value="${user.login}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td><c:out value="${user.role}"/></td>
            <c:if test="${sessionScope.loginRole.role == 'ADMINISTRATOR' || sessionScope.loginRole.login == user.name}">
                <td>
                    <form action = "${pageContext.servletContext.contextPath}/edit" method="get">
                        <input type="hidden" name="id" value="${user.id}"/>
                        <input type="submit" value="Edit"/>
                    </form>
                </td>
            </c:if>
            <c:if test="${sessionScope.loginRole.role == 'ADMINISTRATOR'}">
                <td>
                    <form action="${pageContext.servletContext.contextPath}/list" method="post">
                        <input type="hidden" name="id" value="${user.id}"/>
                        <input type="submit" value="Delete"/>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
</body>
</html>
