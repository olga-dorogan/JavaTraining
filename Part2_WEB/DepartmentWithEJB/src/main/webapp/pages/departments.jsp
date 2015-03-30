<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 20.03.15
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Departments</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/styles.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-2" align="center">
            <ul class="nav nav-stacked">
                <li>Departments</li>
                <li><a href="universityInfo.do"> Create query </a></li>
            </ul>
        </div>
        <div class="col-md-6">
            <h4>Departments</h4>
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <th class="col-md-1">#</th>
                <th class="col-md-3">Description</th>
                <th class="col-md-2">Action</th>
                </thead>
                <tbody>
                <c:forEach var="department" items="${departments}" varStatus="cnt">
                    <tr>
                        <td class="vert-align center-align">${cnt.count}</td>
                        <td class="vert-align left-align">
                            <a href="departmentGroupAll.do?departmentId=${department.id}">${department.description}</a>
                        </td>
                        <td class="vert-align center-align">
                            <form class="form-inline" action="departmentRemove.do" method="post">
                                <input type="hidden" name="departmentId" value="${department.id}">
                                <button class="btn btn-default form-control" type="submit">
                                    <span class="glyphicon glyphicon-trash"></span>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <hr>
            <c:choose>
                <c:when test="${empty errorAddingDepartment}">
                    <h4>Add new department:</h4>

                    <form action="departmentAdd.do" method="post" class="form-horizontal">
                        <div class="form-group">
                            <label for="name" class="col-md-4 control-label">Description</label>

                            <div class="col-md-8">
                                <input type="text" class="form-control" id="name" placeholder="Department description"
                                       name="description">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-10">
                                <button type="submit" class="btn btn-default">Add department</button>
                            </div>
                        </div>
                    </form>
                </c:when>
                <c:otherwise>
                    <h4>Error</h4>
                    Department '${errorAddingDepartment}' already exists.
                    <form class="form-horizontal" action="departmentAll.do">
                        <button class="btn btn-default" type="submit">Ok</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
