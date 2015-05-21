<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 21.05.15
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Calendar list</title>
</head>
<body>
<h1>List of calendars:</h1>
<ul>
    <c:forEach var="calendar" items="${calendars}" varStatus="cnt">
        <li>
            id: ${calendar.id}<br>
            summary: ${calendar.summary}<br>
            description: ${calendar.description}
        </li>
    </c:forEach>
</ul>
<hr/>
<form action="calendarAdd" method="post">
    <input type="text" name="title" value="Calendar title">
    <button type="submit">Add calendar</button>
</form>
<hr/>
<form action="logout" method="get">
    <button type="submit">Logout</button>
</form>
<br/>
</body>
</html>
