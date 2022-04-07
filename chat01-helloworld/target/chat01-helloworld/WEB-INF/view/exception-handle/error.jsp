<%--
  Created by IntelliJ IDEA.
  User: jijiao
  Date: 4/7/2022
  Time: 3:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>登录失败！</h1><br/>
错误提示：${requestScope.msg}<br/>
Exception.message: ${e.message}
</body>
</html>
