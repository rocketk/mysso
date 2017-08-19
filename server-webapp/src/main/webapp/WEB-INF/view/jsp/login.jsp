<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="includes/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>login</title>
</head>
<body>
<c:choose>
    <c:when test="${serviceProvider != null}">
        <p>您好，欢迎使用 ${serviceProvider.name}，请登录！</p>
    </c:when>
    <c:otherwise>
        <p>您好，请登录！</p>
    </c:otherwise>
</c:choose>
<div>
    <span style="color: red;">${message}</span>
</div>
<form action="${ctx}/login" method="post">
    username
    <input name="username"><br>
    password
    <input name="password" type="password"><br>
    <input type="submit">
</form>
</body>
</html>
