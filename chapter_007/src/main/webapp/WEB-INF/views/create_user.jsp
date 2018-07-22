<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Create User</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/backgrounds.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/js/create_user.js"></script>
</head>
<body>
<div class="container-fluid">
    <form action="${pageContext.servletContext.contextPath}/signin" method="post">
        <input type="hidden" name="action" value="signout">
        <div class="form-group">
            <label for="exit">
                You are authorized as a ${sessionScope.loginRole.login}. Your role: ${sessionScope.loginRole.role}
            </label>
            <input type="submit" class="btn btn-default" value="Выход" id="exit"/>
        </div>
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
            <div class="form-group">
                <label for="login">Login : </label>
                <input type="text" class="form-control" name="login" id="login">
            </div>
            <div class="form-group">
                <label for="name">Name : </label>
                <input type="text" class="form-control" name="name" id="name">
            </div>
            <div class="form-group">
                <label for="email">Email : </label>
                <input type="text" class="form-control" name="email" id="email">
            </div>
            <div class="form-group">
                <label for="select">Role : </label>
                <select class="form-control" name="role" id="select">
                    <option value="user">User</option>
                    <option value="administrator">Administrator</option>
                </select>
            </div>
            <div class="form-group">
                <label for="selectCountry">Country : </label>
                <select class="form-control" name="country" id="selectCountry" onchange="loadCities();">

                </select>
            </div>
            <div class="form-group" id="selectCityDiv">
                <label for="selectCity">City : </label>
                <select class="form-control" name="city" id="selectCity">
                </select>
            </div>
            <div class="form-group">
                <label for="password">Password : </label>
                <input type="password" class="form-control" name="password" id="password">
            </div>
            <button type="submit" class="btn btn-default" onclick="return validate()">Create</button>
        </form>
    </c:if>
    <c:if test="${sessionScope.loginRole.role != 'ADMINISTRATOR'}">
        <div style="background-color: red">
            <c:out value="Acces denied"/>
        </div>
    </c:if>
</div>
</body>
</html>
