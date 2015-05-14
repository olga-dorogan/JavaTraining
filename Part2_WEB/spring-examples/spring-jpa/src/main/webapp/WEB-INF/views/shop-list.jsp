<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.custom.entity.Shop" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 14.05.15
  Time: 12:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>Shop List page</h1>
<p>There are ${listSize} items in list now</p>
<%--<p>And shopList consists--%>q
    <%--<% ArrayList<Shop> list = (ArrayList<Shop>) request.getAttribute("shopList"); %>--%>
    <%--<%= list.size()%>--%>
<%--</p>--%>
<table style="text-align: center;" border="1px" cellpadding="0" cellspacing="0">
    <thead>
    <tr>
        <th width="25px">id</th>
        <th width="150px">company</th>
        <th width="25px">employees</th>
        <th width="50px">actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="shop" items="${shopList}">
        <tr>
            <td>${shop.id}</td>
            <td>${shop.name}</td>
            <td>${shop.emplNumber}</td>
            <td>
                <a href="${pageContext.request.contextPath}/shop/edit/${shop.id}.html">Edit</a><br/>
                <a href="${pageContext.request.contextPath}/shop/delete/${shop.id}.html">Delete</a><br/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="${pageContext.request.contextPath}/">Home page</a>
</body>
</html>
