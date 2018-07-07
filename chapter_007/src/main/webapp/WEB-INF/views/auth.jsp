<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Авторизация</title>
</head>
<body>
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
<form action="${pageContext.servletContext.contextPath}/signin" method="post">
    <input type="hidden" name="action" value="signin"/>
    <input type="text" name="login"/>
    <input type="password" name="password"/>
    <input type="submit"/>
</form>
</body>
</html>
