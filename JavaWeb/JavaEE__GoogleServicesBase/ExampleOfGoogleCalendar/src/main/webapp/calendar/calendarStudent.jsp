<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 21.05.15
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course calendar</title>
</head>
<body>
<h1>Your course calendar</h1>
<br/>
<c:choose>
    <c:when test="${not empty calendarFrame}">
        ${calendarFrame}
    </c:when>
    <c:otherwise>
        You are not enrolled in any course
    </c:otherwise>
</c:choose>
<hr/>
<form action="../logout" method="get">
    <button type="submit">Logout</button>
</form>
</body>
</html>
