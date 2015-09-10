<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CDI : BeanManager</title>
    </head>
    <body>
        <h1>CDI : BeanManager</h1>

        Show the list of beans using BeanManager by:
        <ol>
            <li><a href="${pageContext.request.contextPath}/TestServletInject">injection</a></li>
            <li><a href="${pageContext.request.contextPath}/TestServletCurrent">CDI.current</a></li>
            <li><a href="${pageContext.request.contextPath}/TestServletJNDI">JNDI</a></li>
        </ol>

</body>
</html>
