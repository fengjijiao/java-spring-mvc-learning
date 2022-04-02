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
<form method="post" action="${pageContext.request.contextPath}/upload/upload2.do" enctype="multipart/form-data">
    文件1：<input type="file" name="file1"><br/>
    文件2：<input type="file" name="file2"><br/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
