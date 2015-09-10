<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 21.05.15
  Time: 12:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course calendar</title>
</head>
<body>
  <h1>Your new calendar with name '${calendarTitle}'</h1>
  <br/>
  <form action="logout" method="get">
    <button type="submit">Logout</button>
  </form>
  <br/>
  ${calendarFrame}
</body>
</html>
