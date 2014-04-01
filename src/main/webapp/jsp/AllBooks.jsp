<%@ page import="com.my.bussiness.beans.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="com.my.dao.BooksDao" %>
<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 09.03.14
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>All books</title>
    <%@ include file="/jsp/includes/head.jsp" %>
</head>
<body>
    <%@ include file="/jsp/includes/header.jsp" %>
    <%--<jsp:include page="includes/header.jsp" flush="true" />--%>

    <div class="col-md-1 col-md-offset-11">
        <a href="/library/books/add">
            <button class="btn custom-button">Add new book</button>
        </a>
    </div>

    <div class="col-md-12" style="padding: 0 20px; margin: 0;">
        <%
            List<Book> booksList = new BooksDao().selectAll();

            boolean lighter = true;
            for (Book b : booksList) {
                config.getServletContext().setAttribute("bookToDisplay", b);
                lighter = ! lighter;
        %>
        <div class="row book-item-block"
             style="background-color: <%=(lighter) ? "#48888f" : "#427278"%>" >
            <jsp:include page="/jsp/includes/book.jsp" flush="true" />
        </div>
        <% } %>
    </div>

</body>
</html>