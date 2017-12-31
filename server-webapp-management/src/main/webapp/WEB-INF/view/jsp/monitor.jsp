<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="includes/tags.jsp" %>
<html>
<head>
    <title>Monitor</title>
</head>
<body>
<c:choose>
    <c:when  test="${allTGTs != null}">
        <c:forEach items="${allTGTs}" var="tgt">
            ${tgt}
        </c:forEach>
        <table>
            <tr>
                <th></th>
            </tr>
        </table>
    </c:when>
    <c:otherwise>
        <p>尚无登录票据！</p>
    </c:otherwise>
</c:choose>
</body>
</html>
