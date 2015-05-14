<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 14.05.15
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>Edit Shop page</h1>
<form:form method="POST" commandName="shop" action="${pageContext.request.contextPath}/shop/edit/${shop.id}.html">
    <table>
        <tbody>
        <tr>
            <td>Shop name:</td>
            <td><form:input path="name"/></td>
            <td><form:errors path="name" cssStyle="color: red;"/></td>
        </tr>
        <tr>
            <td>Employees number:</td>
            <td><form:input path="emplNumber"/></td>
            <td><form:errors path="emplNumber" cssStyle="color: red;"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Create"/></td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>
</form:form>
<a href="${pageContext.request.contextPath}/">Home page</a>

</body>
</html>
