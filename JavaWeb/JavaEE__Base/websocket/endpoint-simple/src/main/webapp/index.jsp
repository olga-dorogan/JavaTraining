<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>WebSocket</title>

</head>
<body>

<h1>WebSocket</h1>

<div style="text-align: center;">
    <form action="">
        Payload size: <input id="size" value="1" type="text"><br>
        How many times ?: <input id="times" value="10" type="text"><br>
        <br/>
        <input onclick="echoText();" value="Echo" type="button">
    </form>
</div>

<script language="javascript" type="text/javascript" src="websocket.js"></script>

</body>
</html>
