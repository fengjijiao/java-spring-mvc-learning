<jsp:useBean id="ex" scope="request" type="java.lang.Exception"/>
<%--
  Created by IntelliJ IDEA.
  User: jijiao
  Date: 4/13/2022
  Time: 11:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <h2>出错啦，错误信息如下：</h2>
  <h3>${ex}</h3>
</body>
</html>
