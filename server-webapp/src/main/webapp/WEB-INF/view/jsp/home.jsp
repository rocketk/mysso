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
截至目前您登录了${fn:length(authentication.ticketGrantingTicket.tokenIds)}个应用 <a href="${ctx}/logout">全部登出</a>
<br/>
<p>接入单点登录系统的应用, 您可以直接访问:</p>
<c:forEach items="${serviceProviderMap}" var="one">
    <a href="${one.value.homeUrl}" target="_blank">${one.value.name}</a><br/>
</c:forEach>

</body>
</html>
