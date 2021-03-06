<%@ page import="com.my.bussiness.beans.Book" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 09.03.14
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="bd" uri="/jsp/taglibs/BeanDisplay.tld" %>
<html>
<head>
    <title>All books</title>
    <%@ include file="/jsp/includes/head.jsp" %>
</head>
<body>
    <%@ include file="/jsp/includes/header.jsp" %>
    <%--<jsp:include page="includes/header.jsp" flush="true" />--%>

    <div class="col-md-12" style="padding: 0 20px; margin: 0;">
        <%
            List<Book> booksList =
                    (List<Book>) request.getAttribute(AttributeName.BookToDisplayList);

            boolean lighter = true;
            for (Book b : booksList) {
                lighter = ! lighter;
        %>
        <div class="row book-item-block"
             style="background-color: <%=(lighter) ? "#48888f" : "#427278"%>" >
            <bd:book book="<%=b%>" />
        </div>
        <% } %>
    </div>

</body>
</html>