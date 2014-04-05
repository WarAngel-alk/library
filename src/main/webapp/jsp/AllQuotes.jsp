<%@ page import="com.my.bussiness.beans.Quote" %>
<%@ page import="com.my.dao.QuotesDao" %>
<%@ page import="com.my.dao.BooksDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.my.enums.RequestAttributes" %>
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
    <title>All quotes</title>
    <%@ include file="includes/head.jsp" %>
</head>
<body>
    <%@ include file="includes/header.jsp" %>
    <%--<jsp:include page="includes/header.jsp" flush="true" />--%>

    <div class="col-md-12">
        <%
            List<Quote> quoteList = new QuotesDao().selectAll();

            boolean lighter = true;
            for (Quote q : quoteList) {
                config.getServletContext()
                        .setAttribute(RequestAttributes.QuoteToDisplay.name(), q);
                lighter = ! lighter;
        %>
        <div class="row quote-item-block"
             style="background-color: <%=(lighter) ? "#48888f" : "#427278"%>">
            <jsp:include page="/jsp/includes/quote.jsp" flush="true" />
        </div>
        <% } %>
    </div>
</body>
</html>