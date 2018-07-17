<%@ page import="ru.job4j.servlets.User" %>
<%@ page import="ru.job4j.servlets.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>All Users</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        body {
            background-color: #EEEEFB;
        }
        table {
            background-color: #ffffff;
        }
    </style>
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
    <h2>Users</h2>
    <p>All users registered on the server:</p>
    <table class="table table-bordered">
        <caption>All Users</caption>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Login</th>
            <th>Email</th>
            <th>Role</th>
            <th>Country</th>
            <th>City</th>
            <th colspan="2">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.role}"/></td>
                <td><c:out value="${user.country}"/></td>
                <td><c:out value="${user.city}"/></td>
                <c:if test="${sessionScope.loginRole.role == 'ADMINISTRATOR' || sessionScope.loginRole.login == user.login}">
                    <td>
                        <form action = "${pageContext.servletContext.contextPath}/edit" method="get">
                            <div class="form-group">
                                <input type="hidden" name="id" value="${user.id}"/>
                                <button type="submit" class="btn btn-default">Edit</button>
                            </div>
                        </form>
                    </td>
                </c:if>
                <c:if test="${sessionScope.loginRole.role == 'ADMINISTRATOR'}">
                    <td>
                        <form action="${pageContext.servletContext.contextPath}/list" method="post">
                            <input type="hidden" name="id" value="${user.id}"/>
                            <div class="form-group">
                                <button type="submit" class="btn btn-default">Delete</button>
                            </div>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
