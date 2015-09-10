<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 14.05.15
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>Home page</h1>

<p>
    Welcome to "Shop application".<br/>
    <i>${message}</i><br/>
    <a href="${pageContext.request.contextPath}/shop/create.html">Create a new shop</a><br/>
    <a href="${pageContext.request.contextPath}/shop/list.html">View all shops</a><br/>
</p>
</body>
</html>
