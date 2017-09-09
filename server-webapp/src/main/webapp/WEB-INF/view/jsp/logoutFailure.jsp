<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="includes/tags.jsp" %>
<html>
<head>
    <title>登出失败</title>
</head>
<body>
<p>登出失败, 您尚未完成登出操作!</p>

<c:if test="${logoutResult != null && logoutResult.successLogoutServiceInstance != null}">
<p>本次成功登出的客户端应用:</p>
    <c:forEach items="${logoutResult.successLogoutServiceInstance}" var="one">
        应用名称: ${one.serviceProvider.name}, 登出地址: ${one.logoutUrl}, 信息: ${one.message} <br/>
    </c:forEach>
</c:if>

<c:if test="${logoutResult != null && logoutResult.successLogoutServiceInstance != null}">
    <p>本次未能成功登出的客户端应用:</p>
    <c:forEach items="${logoutResult.failLogoutServiceInstance}" var="one">
        应用名称: ${one.serviceProvider.name}, 登出地址: ${one.logoutUrl}, 信息: ${one.message} <br/>
    </c:forEach>
</c:if>
<a href="${ctx}/logout?force=true" title="仅退出服务端">强制退出</a>&nbsp;&nbsp;
<a href="${ctx}/home">home</a>
<p>强制退出服务端之后, 客户端应用尚未销毁您本次登录的会话, 因此有可能会被继续访问, 请及时联系您的管理员以解决这个问题</p>
</body>
</html>
