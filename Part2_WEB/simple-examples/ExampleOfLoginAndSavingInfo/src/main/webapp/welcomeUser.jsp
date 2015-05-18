<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 15.05.15
  Time: 12:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Hello, ${user.firstName}</h1>
<br>
You email is ${user.email}
<br>
You tasks:
<br>

<form method="post" action="updateTasks">
    <table>
        <thead>
        <tr>
            <td>Task</td>
            <td>State</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="state" items="${user.tasksStates}" varStatus="cnt">
            <tr>
                <td>Task ${cnt.index}, state ${state}</td>
                <td>
                    <c:choose>
                        <c:when test="${state}">
                            <input type="checkbox" name="tasks" value="${cnt.index}" checked>
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" name="tasks" value="${cnt.index}">
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <button type="submit">Update tasks states</button>
</form>
</body>
</html>
