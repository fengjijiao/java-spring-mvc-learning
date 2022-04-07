<%--
  Created by IntelliJ IDEA.
  User: jijiao
  Date: 4/7/2022
  Time: 3:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/exception/handling/user/login">
    姓名：<input name="username"/><br/>
    密码：<input name="password"/><br/>
    <input type="submit" value="登录">
</form>
</body>
</html>
