<%@ page import="com.my.dao.QuotesDao" %>
<%@ page import="com.my.bussiness.beans.Quote" %>
<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 09.03.14
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Quote by id</title>
    <%@ include file="includes/head.jsp" %>
</head>
<body>
    <%@ include file="includes/header.jsp" %>
    <%--<jsp:include page="includes/header.jsp" flush="true" />--%>

    <div class="col-md-12" style="padding: 0 20px; margin: 0;">
        <%
            long id = (Long) request.getAttribute("quoteToDisplayId");
            Quote quote = new QuotesDao().selectById(id);
            config.getServletContext().setAttribute("quoteToDisplay", quote);
        %>
        <div class="row quote-item-block" style="background-color: #427278;" >
            <jsp:include page="/jsp/includes/quote.jsp" flush="true" />
        </div>
    </div>
</body>
</html>