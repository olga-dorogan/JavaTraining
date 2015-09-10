<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 29.03.15
  Time: 22:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/styles.css">
</head>
<body>
<%--departments -- List --%>
<%--lastSelectedDepartments -- Map with keys eq sel Departments--%>
<%--groupsFromSelectedDepartments -- List--%>
<%--cntsGroupPerDepartment - List--%>
<%--cntsStudentsPerDepartment--%>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <form class="form-horizontal" action="universityInfo.do">
                <div class="form-group">
                    <label for="department">Choose department:</label>
                    <select multiple class="form-control" id="department" name="selectedDepartments">
                        <c:forEach var="department" items="${departments}">
                            <option value="${department.id}"
                                    selected="${not empty lastSelectedDepartments[department.id]}">
                                    ${department.description}
                            </option>
                        </c:forEach>
                    </select>

                    <p></p>

                    <div class="btn-group" role="group">
                        <button type="submit" class="btn btn-default" name="getGroupsFromDeps">Get all groups form
                            departments
                        </button>
                        <button type="submit" class="btn btn-default" name="getGroupsCnt">Get groups cnt per
                            department
                        </button>
                        <button type="submit" class="btn btn-default" name="getStudentsCnt">Get students cnt per
                            department
                        </button>
                    </div>
                </div>
            </form>
            <c:if test="${not empty groupsFromSelectedDepartments}">
                <table class="table table-bordered">
                    <thead>
                    <th class="center-align">#</th>
                    <th>Department</th>
                    <th>Name</th>
                    <th class="center-align">Course</th>
                    </thead>
                    <tbody>
                    <c:forEach var="group" items="${groupsFromSelectedDepartments}" varStatus="cnt">
                        <tr>
                            <td class="center-align">${cnt.count}</td>
                            <td></td>
                            <td>${group.name}</td>
                            <td>${group.course}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${not empty cntsGroupPerDepartment}">
                <table class="table table-bordered">
                    <thead>
                    <th>#</th>
                    <th>Department</th>
                    <th>Groups count</th>
                    </thead>
                    <tbody>
                    <c:forEach var="pair" items="${cntsGroupPerDepartment}" varStatus="cnt">
                        <tr>
                            <td>${cnt.count}</td>
                            <td>${pair.firstValue}</td>
                            <td>${pair.secondValue}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${not empty cntsStudentsPerDepartment}">
                <table class="table table-bordered">
                    <thead>
                    <th>#</th>
                    <th>Department</th>
                    <th>Students count</th>
                    </thead>
                    <tbody>
                    <c:forEach var="pair" items="${cntsStudentsPerDepartment}" varStatus="cnt">
                        <tr>
                            <td>${cnt.count}</td>
                            <td>${pair.firstValue}</td>
                            <td>${pair.secondValue}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <%--<div class="form-group">--%>
            <%--<label for="group">Choose group:</label>--%>
            <%--<select multiple class="form-control" id="group">--%>
            <%--<option>1.1</option>--%>
            <%--<option>1.2</option>--%>
            <%--<option>3.1</option>--%>
            <%--<option>3.2</option>--%>
            <%--</select>--%>
            <%--<button class="btn btn-default">Get students</button>--%>
            <%--<button class="btn btn-default">Get avg students cnt</button>--%>
            <%--</div>--%>
        </div>
    </div>
</div>
</body>
</html>
