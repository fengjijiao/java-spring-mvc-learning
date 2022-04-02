<jsp:useBean id="msg" scope="request" type="java.lang.String"/>
<%--
  Created by IntelliJ IDEA.
  User: jijiao
  Date: 4/1/2022
  Time: 2:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Result</title>
</head>
<body>
<p>${msg}</p>
<form method="post" action="${pageContext.request.contextPath}/receiveparam/test1.do">
    姓名：<input name="name" value="follow"/> <br/>
    年龄：<input name="age" value="22"/> <br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
