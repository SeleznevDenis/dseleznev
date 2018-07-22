<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Авторизация</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/backgrounds.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/js/auth.js"></script>
</head>
<body>
<div class="container-fluid">
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
    <h1>Sign in.</h1>
    <form action="${pageContext.servletContext.contextPath}/signin" method="post">
        <input type="hidden" name="action" value="signin"/>
        <div class="form-group">
            <label for="login">Login : </label>
            <input type="text" class="form-control" name="login" id="login"/>
        </div>
        <div class="form-group">
            <label for="password">Password : </label>
            <input type="password" class="form-control" name="password" id="password"/>
        </div>
        <button type="submit" class="btn btn-default" onclick="return validate()">Sign in</button>
    </form>
</div>
</body>
</html>
