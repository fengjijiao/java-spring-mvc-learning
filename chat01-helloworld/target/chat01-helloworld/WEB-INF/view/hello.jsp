<%--
  Created by IntelliJ IDEA.
  User: jijiao
  Date: 4/1/2022
  Time: 9:53 AM
  To change this template use File | Settings | File Templates.
--%>
<jsp:useBean id="msg" scope="request" type="java.lang.String"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>spring mvc series</title>
</head>
<body>
<h2>Hello World!</h2>
<div style="text-align: center">
    <h1>hello SpringMVC</h1>
    <h1>msg: ${msg}</h1>
</div>
</body>
</html>
