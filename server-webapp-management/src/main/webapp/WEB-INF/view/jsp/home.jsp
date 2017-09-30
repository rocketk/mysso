<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="includes/tags.jsp" %>
<html>
<head>
    <title>login</title>
</head>
<body>
Hi, ${authentication.principal.id}!
<br>
您于<fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${authentication.authenticationDate}"/>登录，

<a href="${ctx}/logout">登出</a>

</body>
</html>
