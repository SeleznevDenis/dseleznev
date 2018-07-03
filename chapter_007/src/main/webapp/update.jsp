
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update user</title>
</head>
<body>
${message}
<form action="<%=request.getContextPath()%>/edit" method="post">
    Login: <input type="text" name="login" value="${user.login}"><br>
    Name: <input type="text" name="name" value="${user.name}"><br>
    Email: <input type="text" name="email" value="${user.email}"><br>
    <input type="hidden" name="id" value="${user.id}">
    <input type="submit">
</form>
</body>
</html>
