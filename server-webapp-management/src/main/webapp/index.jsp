<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final String queryString = request.getQueryString();
    final String url = request.getContextPath() + "/login" + (queryString != null ? "?" + queryString : "");
    response.sendRedirect(response.encodeURL(url));
%>