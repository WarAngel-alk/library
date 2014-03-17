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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All books</title>
    <%@ include file="/jsp/includes/head.jsp" %>
</head>
<body>
    <%@ include file="/jsp/includes/header.jsp" %>
    <%--<jsp:include page="includes/header.jsp" flush="true" />--%>

    <div class="col-md-10 col-md-offset-1">
        <%
            List<Book> booksList = new BooksDao().selectAll();

            for (Book b : booksList) {
                config.getServletContext().setAttribute("bookToDisplay", b);
        %>
        <div class="row book-item-block">
            <jsp:include page="/jsp/includes/book.jsp" flush="true" />
        </div>
        <% } %>
    </div>

</body>
</html>