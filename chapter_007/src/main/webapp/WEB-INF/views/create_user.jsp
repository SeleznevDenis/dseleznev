<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Create User</title>
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
<c:if test="${sessionScope.loginRole.role == 'ADMINISTRATOR'}">
    <form action="${pageContext.servletContext.contextPath}/create" method="post">
        Login: <input type="text" name="login"/><br>
        Name: <input type="text" name="name"><br>
        Email: <input type="text" name="email"><br>
        Role: <select name="role">
        <option value="administrator">Admin</option>
        <option value="user">User</option>
    </select><br>
        Password: <input type="password" name="password"><br>
        <input type="submit">
    </form>
</c:if>
<c:if test="${sessionScope.loginRole.role != 'ADMINISTRATOR'}">
    <div style="background-color: red">
        <c:out value="Acces denied"/>
    </div>
</c:if>
</body>
</html>
