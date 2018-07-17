<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>User update</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        body {
            background-color: #EEEEFB;
        }
    </style>
    <script>
        function validate() {
            const validationMessages = {
                login: {
                    required: 'Enter login. '
                }, name: {
                    required: 'Enter name. '
                }, email: {
                    required: 'Enter email. '
                }, password: {
                    required: 'Enter password. '
                }
            };
            var done = true;
            var message =
                checkAndGetMessage($('#login'), validationMessages.login.required) +
                checkAndGetMessage($('#name'), validationMessages.name.required) +
                checkAndGetMessage($('#email'), validationMessages.email.required) +
                checkAndGetMessage($('#password'), validationMessages.password.required);
            if (message) {
                done = false;
                alert(message);
            }
            return done;
        }

        function checkAndGetMessage(el, message) {
            return !el.val() ? message : '';
        }
        function loadCities() {
            $.ajax('./address', {
                type: 'POST',
                dataType: 'json',
                data: {"country":$('#selectCountry').val()},
                complete: function(data) {
                    var cities = JSON.parse(data.responseText);
                    var result = "";
                    for (i = 0; i !== cities.length; ++i) {
                        result += "<option";
                        if(cities[i] === "${user.city}") {
                           result += " selected";
                        }
                        result += " value=\"" + cities[i] + "\">"+cities[i]+"</option>";
                    }
                    var citiesDiv = document.getElementById("selectCity");
                    citiesDiv.innerHTML = result;
                }
            })
        }

        $(
            $.ajax('./address', {
                type: 'GET',
                complete: function(data) {
                    var countries = JSON.parse(data.responseText);
                    var result = "<option>Choose country:</option>";
                    var selected = "";
                    for (var i = 0; i !== countries.length; ++i) {
                        result += "<option";
                        if (countries[i] === "${user.country}") {
                            result += " selected";
                        }
                        result += " value=\""+countries[i]+"\">"+countries[i]+"</option>";
                    }
                    var countriesDiv = document.getElementById("selectCountry");
                    countriesDiv.innerHTML = result;
                    loadCities();
                }
            })
        );

    </script>
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
    <h1>Update user</h1>
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
            <div class="form-group">
                <label for="login">Login : </label>
                <input type="text" class="form-control" name="login" value="${user.login}" id="login">
            </div>
            <div class="form-group">
                <label for="name">Name : </label>
                <input type="text" class="form-control" name="name" value="${user.name}" id="name">
            </div>
            <div class="form-group">
                <label for="email">Email : </label>
                <input type="text" class="form-control" name="email" value="${user.email}" id="email">
            </div>
            <c:if test="${sessionScope.loginRole.role == 'ADMINISTRATOR'}">
                <div class="form-group">
                    <label for="select">Role : </label>
                    <select class="form-control" name="role" id="select">
                        <option value="user">User</option>
                        <option value="administrator">Administrator</option>
                    </select>
                </div>
            </c:if>
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
                <input type="password" class="form-control" name="password" value="${user.password}" id="password">
            </div>
            <input type="hidden" name="id" value="${user.id}">
            <button type="submit" class="btn btn-default" onclick="return validate()">Submit</button>
        </form>
    </c:if>
</div>
</body>
</html>
