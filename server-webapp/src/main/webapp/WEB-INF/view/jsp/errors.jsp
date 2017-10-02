<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="includes/tags.jsp" %>
<html>
<head>
    <title>error</title>
</head>
<body>
<%
    Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
    exception.printStackTrace();
%>
<c:set var="exception" value="${javax.servlet.error.exception}"/>
<p>exception: <span style="color: red;"><%=exception.getClass().getCanonicalName() + exception.getMessage()%></span> </p>

</body>
</html>
