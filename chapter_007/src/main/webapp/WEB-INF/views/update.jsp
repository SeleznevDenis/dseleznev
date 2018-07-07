<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update user</title>
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
<c:if test="${sessionScope.loginRole.role == 'ADMINISTRATOR' || sessionScope.loginRole.login == user.login}">
    <form action="${pageContext.servletContext.contextPath}/edit" method="post">
        Login: <input type="text" name="login" value="${user.login}"><br>
        Name: <input type="text" name="name" value="${user.name}"><br>
        Email: <input type="text" name="email" value="${user.email}"><br>
        <c:if test="${sessionScope.loginRole.role == 'ADMINISTRATOR'}">
            Role: <select name="role">
            <option value="user">User</option>
            <option  value="administrator">Administrator</option>
        </select><br>
        </c:if>
        Password: <input type="password" name="password" value="${user.password}"><br>
        <input type="hidden" name="id" value="${user.id}">
        <input type="submit">
    </form>
</c:if>
</body>
</html>
