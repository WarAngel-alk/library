<%@ page import="com.my.bussiness.beans.Quote" %>
<%@ page import="com.my.dao.QuotesDao" %>
<%@ page import="com.my.dao.BooksDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.my.enums.AttributeName" %>
<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 09.03.14
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="bd" uri="/jsp/taglibs/BeanDisplay.tld" %>
<html>
<head>
    <title>All quotes</title>
    <%@ include file="includes/head.jsp" %>
</head>
<body>
    <%@ include file="includes/header.jsp" %>

    <div class="col-md-12">
        <%
            List<Quote> quoteList =
                    (List<Quote>) request.getAttribute(AttributeName.QuoteToDisplayList);

            boolean lighter = true;
            for (Quote q : quoteList) {
                lighter = ! lighter;
        %>
        <div class="row quote-item-block"
             style="background-color: <%=(lighter) ? "#48888f" : "#427278"%>">
            <bd:quote quote="<%=q%>" />
        </div>
        <% } %>
    </div>
</body>
</html>