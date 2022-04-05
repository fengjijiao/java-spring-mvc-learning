<%--
  Created by IntelliJ IDEA.
  User: jijiao
  Date: 4/1/2022
  Time: 5:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/upload/upload4.do" enctype="multipart/form-data">
    姓名：<input name="name" value="follow"><br/>
    年龄：<input name="age" value="22"><br/>
    头像图片：<input type="file" name="headImg"><br/>
    多张身份证图片<br/>
    <input type="file" name="idCardImg"><br/>
    <input type="file" name="idCardImg"><br/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
