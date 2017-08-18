<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="includes/tags.jsp"/>
<!DOCTYPE html>
<html>
<head>
    <title>login</title>
</head>
<body>
<c:if test="${serviceProvider != null}">
    <p>hello, welcome to ${serviceProvider.name}, please login.</p>
</c:if>
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
