<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create User</title>
</head>
<body>
${message}
<form action="<%=request.getContextPath()%>/create" method="post">
    Login: <input type="text" name="login"/><br>
    Name: <input type="text" name="name"><br>
    Email: <input type="text" name="email"><br>
    <input type="submit">
</form>
</body>
</html>
