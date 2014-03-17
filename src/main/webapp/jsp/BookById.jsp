<%@ page import="com.my.bussiness.beans.Book" %>
<%@ page import="com.my.dao.BooksDao" %>
<%@ page import="org.apache.log4j.Logger" %>
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
    <title>Book by id</title>
    <%@ include file="/jsp/includes/head.jsp" %>
</head>
<body>
    <%@ include file="/jsp/includes/header.jsp" %>
    <%--<jsp:include page="includes/header.jsp" flush="true" />--%>

    <div class="col-md-10 col-md-offset-1">
        <%
            String[] pathArray = config.getServletContext().getContextPath().split("/");
            Logger.getLogger("test").error(config.getServletContext().getContextPath());
    //        long id = Long.parseLong(pathArray[pathArray.length - 1]);
            long id = 1;
            Book book = new BooksDao().selectById(id);
            config.getServletContext().setAttribute("bookToDisplay", book);
        %>
        <div class="row book-item-block">
            <jsp:include page="/jsp/includes/book.jsp" flush="true" />
        </div>
    </div>

</body>
</html>