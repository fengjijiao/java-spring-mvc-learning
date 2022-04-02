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
<form method="post" action="${pageContext.request.contextPath}/receiveparam/test6.do">
    姓名：<input name="userInfo.name" value="follow"/> <br/>
    年龄：<input name="userInfo.age" value="22"/> <br/>

    工作年限：<input name="workInfo.workYears" value="1"/> <br/>
    工作地址：<input name="workInfo.workAddress" value="上海市"/> <br/>

    第1份工作公司：<input name="experienceInfos[0].company" value="百度"/> <br/>
    第1分职位：<input name="experienceInfos[0].position" value="Java开发"> <br/>

    第2份工作公司：<input name="experienceInfos[1].company" value="阿里"/> <br/>
    第2分职位：<input name="experienceInfos[1].position" value="Java资深开发"> <br/>

    <input type="submit" value="提交">
</form>
</body>
</html>
