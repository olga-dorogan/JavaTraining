<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 29.03.15
  Time: 22:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html><head>
    <title></title>
</head>
<body>
Groups:
<ul>
    <c:forEach var="group" items="${departmentGroups}">
        <li>${group}</li>
    </c:forEach>
</ul>
</body>
</html>
