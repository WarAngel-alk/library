<%@ page import="com.my.bussiness.beans.Book" %>
<%@ page import="com.my.dao.BooksDao" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.my.enums.AttributeName" %>
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
    <title>Book by id</title>
    <%@ include file="/jsp/includes/head.jsp" %>
</head>
<body>
    <%@ include file="/jsp/includes/header.jsp" %>
    <%--<jsp:include page="includes/header.jsp" flush="true" />--%>

    <div class="col-md-12" style="padding: 0 20px; margin: 0;">
        <%
            long id = (Long) request.getAttribute(AttributeName.BookToDisplayId);
            Book book = new BooksDao().selectById(id);
            config.getServletContext().setAttribute(AttributeName.BookToDisplay, book);
        %>
        <div class="row book-item-block" style="background-color: #427278;">
            <jsp:include page="/jsp/includes/book.jsp" flush="true" />
        </div>
    </div>

</body>
</html>