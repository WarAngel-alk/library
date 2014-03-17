<%@ page import="com.my.bussiness.beans.Quote" %>
<%@ page import="com.my.dao.QuotesDao" %>
<%@ page import="com.my.dao.BooksDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
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



    <div class="col-md-10 col-md-offset-1">
        <%
            List<Quote> quoteList = new QuotesDao().selectAll();

        for (Quote q : quoteList) {
            config.getServletContext().setAttribute("quoteToDisplay", q);
        %>
        <div class="row quote-item-block">
            <jsp:include page="/jsp/includes/quote.jsp" flush="true" />
        </div>
        <% } %>
    </div>
    <%--<br><br><br>--%>
    <%--<div class="col-md-10 col-md-offset-1">--%>
        <%--<div class="row book-item-block">--%>
            <%--<jsp:include page="includes/book.jsp" flush="true" />--%>
            <%--&lt;%&ndash;<%@ include file="includes/book.jsp" %>&ndash;%&gt;--%>
        <%--</div>--%>
    <%--</div>--%>
</body>
</html>