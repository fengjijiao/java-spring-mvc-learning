<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>spring mvc series</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/hello.do">hello SpringMVC!</a>
<br/>
<%--<%= pageContext.getServletContext().getServerInfo() %><br>--%>
java.vm.name: <%= System.getProperty("java.vm.name") %><br>
java.vm.vendor: <%= System.getProperty("java.vm.vendor") %><br>
java.vm.version: <%= System.getProperty("java.vm.version") %><br>
</body>
</html>