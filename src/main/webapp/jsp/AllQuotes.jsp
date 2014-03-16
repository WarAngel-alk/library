<%@ page import="com.my.bussiness.beans.Quote" %>
<%@ page import="com.my.dao.QuotesDao" %>
<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 09.03.14
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All quotes</title>
    <%@ include file="includes/head.jsp" %>
</head>
<body>
    <%@ include file="includes/header.jsp" %>
    <%--<jsp:include page="includes/header.jsp" flush="true" />--%>

    <%
        config.getServletContext().setAttribute("quoteToDisplay", new QuotesDao().selectById(1));
    %>
    <jsp:include page="/jsp/includes/quote.jsp" flush="true" />
    <jsp:include page="/jsp/includes/quote.jsp" flush="true" />
    <%--<%@ include file="includes/quote.jsp" %>--%>
    <%--<%@ include file="includes/quote.jsp" %>--%>
</body>
</html>