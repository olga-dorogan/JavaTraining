<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 20.03.15
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Groups</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/bootstrap.min.css">

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <!-- Optional theme -->
    <%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">--%>
    <!-- Latest compiled and minified JavaScript -->
    <%--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>--%>


</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-2" align="center">
            <ul class="nav nav-stacked">
                <c:forEach var="department" items="${departments}">
                    <li>
                        <c:choose>
                            <c:when test="${department.id!=departmentCurrent.id}">
                                <a href="departmentGroupAll.do?departmentId=${department.id}">${department.description}</a>
                            </c:when>
                            <c:otherwise>
                                ${department.description}
                            </c:otherwise>
                        </c:choose>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-6">
            <h4>Groups in department:</h4>
            <c:choose>
                <c:when test="${not empty departmentGroups}">
                    <table class="table table-striped table-bordered table-condensed">
                        <thead>
                        <th class="col-md-1 center-align">#</th>
                        <th class="col-md-3">Group</th>
                        <th class="col-md-1 center-align">Course</th>
                        <th class="col-md-1 center-align">Action</th>
                        </thead>
                        <tbody>
                        <c:forEach var="group" items="${departmentGroups}" varStatus="cnt">
                            <tr>
                                <td class="center-align">${cnt.count}</td>
                                <td>
                                    <a href="studentAll.do?groupId=${group.id}"> ${group.name}</a>
                                </td>
                                <td class="center-align">${group.course}</td>
                                <td class="center-align">
                                    <form class="form-inline" action="departmentGroupRemove.do" method="post">
                                        <input type="hidden" name="groupId" value="${group.id}">
                                        <button class="btn btn-default" type="submit" >
                                            <span class="glyphicon glyphicon-trash"></span>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
            </c:choose>
            <hr>
            <h4>Add new group to department:</h4>
            <c:choose>
                <c:when test="${not empty errorAddGroupMessage}">
                    <p>
                        Can't add group to department: '${errorAddGroupMessage}'.
                    </p>

                    <form action="departmentGroupAll.do" method="get">
                        <input type="hidden" name="departmentId" value="${departmentCurrent.id}">
                        <button class="btn btn-default" type="submit">Ok</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="departmentGroupAdd.do" method="post" class="form-horizontal">
                        <div class="form-group">
                            <label for="name" class="col-md-3 control-label">Name</label>

                            <div class="col-md-9">
                                <input type="text" class="form-control" id="name" placeholder="Group name"
                                       name="groupName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="course" class="col-md-3 control-label">Course</label>

                            <div class="col-md-9">
                                <input type="text" class="form-control" id="course" placeholder="Group course"
                                       name="groupCourse">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-3 col-md-9">
                                <button type="submit" class="btn btn-default">Add group</button>
                            </div>
                        </div>
                    </form>
                </c:otherwise>
            </c:choose>
            <hr>
            <h4>Edit department</h4>
            <c:choose>
                <c:when test="${not empty errorEditDepartmentMessage}">
                    <p>
                        Can't edit department: '${errorEditDepartmentMessage}'.
                    </p>

                    <form action="departmentGroupAll.do" method="get">
                        <input type="hidden" name="departmentId" value="${departmentCurrent.id}">
                        <button type="submit" class="btn btn-default">Ok</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="departmentEdit.do" method="post" class="form-horizontal">
                        <div class="form-group">
                            <label for="course" class="col-md-3 control-label">Description</label>

                            <div class="col-md-9">
                                <input type="text" class="form-control" id="description" placeholder="Group description"
                                       name="departmentDescription" value="${departmentCurrent.description}">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-3 col-md-9">
                                <button type="submit" class="btn btn-default">Edit department</button>
                            </div>
                        </div>
                    </form>
                </c:otherwise>
            </c:choose>
            <hr>
            <a href="departmentAll.do">Home</a>
        </div>
    </div>
</div>
</body>
</html>
