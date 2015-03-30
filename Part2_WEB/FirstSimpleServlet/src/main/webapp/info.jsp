<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 12.03.15
  Time: 23:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>Headers</h1>
<c:forEach var="req" items="${header}">
    <strong><c:out value="${req.key}"/>
    </strong>: <c:out value="${req.value}"/>
    <br>
</c:forEach>
<h1>Parameters</h1>
<c:forEach var="par" items="${param}">
    <strong><c:out value="${par.key}"/>
    </strong>: <c:out value="${par.value}"/>
    <br>
</c:forEach>
<h1>Threads</h1>
<%
    java.util.List<String> listOfThreads = new java.util.ArrayList<String>();
    synchronized (this) {
        int active = Thread.activeCount();
        Thread[] allThreads = new Thread[active];
        if (Thread.enumerate(allThreads) == active) {
            for (Thread thread : allThreads) {
                listOfThreads.add(thread.toString());
            }
        }
    }
%>
<ul>
    <%
        for (int i = 0; i < listOfThreads.size(); i++) {
    %>
    <li>
        <%=listOfThreads.get(i)%>
    </li>
    <%
        }
    %>
</ul>
</body>
</html>
