<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update user</title>
</head>
<body>
<c:out value="${message}"/>
<form action="${pageContext.servletContext.contextPath}/edit" method="post">
    Login: <input type="text" name="login" value="${user.login}"><br>
    Name: <input type="text" name="name" value="${user.name}"><br>
    Email: <input type="text" name="email" value="${user.email}"><br>
    <input type="hidden" name="id" value="${user.id}">
    <input type="submit">
</form>
</body>
</html>
