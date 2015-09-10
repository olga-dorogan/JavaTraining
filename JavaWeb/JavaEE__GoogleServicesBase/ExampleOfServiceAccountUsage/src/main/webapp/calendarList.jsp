<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 22.05.15
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of calendars</title>
</head>
<body>
List of calendars from Service Account
<li>
    <c:forEach var="calendar" items="${calendars}">
        <ul>
                ${calendar.summary} <br/>
                ${calendar.frame}
        </ul>

    </c:forEach>
</li>
<hr>
<form action="addCalendar" method="get">
    <input type="text" name="summary" value="Title">
    <button type="submit">Add calendar</button>
</form>
<hr>
<form action="clear" method="get">
    <button type="submit">Remove all</button>
</form>
</body>
</html>
