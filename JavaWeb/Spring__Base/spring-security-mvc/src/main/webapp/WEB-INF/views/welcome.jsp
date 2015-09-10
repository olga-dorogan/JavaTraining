<%--
  Created by IntelliJ IDEA.
  User: olga
  Date: 09.05.15
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<section>
    <h1>Message : ${message}</h1>
</section>
<div>Get <a href="protected">protected</a> resource for admin.</div>
<div>Get <a href="confidential">confidential</a> resource for superadmin.</div>
</body>
</html>
