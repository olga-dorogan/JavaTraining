<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 29.03.15
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Students</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/styles.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-2" align="center">
            <ul class="nav nav-stacked">
                <c:forEach var="group" items="${groups}">
                    <li>
                        <c:choose>
                            <c:when test="${group.id!=groupCurrent.id}">
                                <a href="studentAll.do?groupId=${group.id}">${group.name}</a>
                            </c:when>
                            <c:otherwise>
                                ${group.name}
                            </c:otherwise>
                        </c:choose>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-6">
            <hr>
            <h4>Students in group:</h4>
            <c:choose>
                <c:when test="${not empty groupStudents}">
                    <table class="table table-striped table-bordered table-condensed">
                        <thead>
                        <th class="col-md-1 center-align">#</th>
                        <th class="col-md-2">First name</th>
                        <th class="col-md-2">Last name</th>
                        <th class="col-md-1 center-align">Age</th>
                        <th class="col-md-1 center-align">Action</th>
                        </thead>
                        <tbody>
                        <c:forEach var="student" items="${groupStudents}" varStatus="cnt">
                            <tr>
                                <td class="center-align">${cnt.count}</td>
                                <td>${student.firstName}</td>
                                <td>${student.lastName}</td>
                                <td class="center-align">${student.age}</td>
                                <td>
                                    <form class="form-inline" action="studentRemove.do" method="post">
                                        <input type="hidden" name="studentId" value="${student.id}">
                                        <button class="btn btn-default" type="submit">
                                            <span class="glyphicon glyphicon-trash"></span>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    Group hasn't any student.
                </c:otherwise>
            </c:choose>
            <hr>
            <h4>Add student to current group</h4>
            <c:choose>
                <c:when test="${not empty errorAddStudentMessage}">
                    <p>
                        Can't add student to group: '${errorAddStudentMessage}'.
                    </p>
                    <form action="studentAll.do" method="get" class="form-horizontal">
                        <input type="hidden" name="groupId" value="${groupCurrent.id}">
                        <button class="btn btn-default" type="submit">Ok</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="studentAdd.do" method="post" class="form-horizontal">
                        <div class="form-group">
                            <label for="fName" class="col-md-3 control-label">First name</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="fName" placeholder="First name"
                                       name="studentFirstName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lName" class="col-md-3 control-label">Last name</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="lName" placeholder="Last name"
                                       name="studentLastName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="age" class="col-md-3 control-label">Age</label>

                            <div class="col-md-9">
                                <input type="text" class="form-control" id="age" placeholder="Age"
                                       name="studentAge">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-3 col-md-9">
                                <button type="submit" class="btn btn-default">Add student</button>
                            </div>
                        </div>
                    </form>
                </c:otherwise>
            </c:choose>
            <hr>
            <a href="departmentAll.do">Home</a>
        </div>
    </div>
</body>
</html>
