<%@ page import="com.my.bussiness.beans.Book" %>
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
    <title>Book by id</title>
    <%@ include file="/jsp/includes/head.jsp" %>
</head>
<body>
    <%@ include file="/jsp/includes/header.jsp" %>
    <%--<jsp:include page="includes/header.jsp" flush="true" />--%>

    <div class="col-md-12" style="padding: 0 20px; margin: 0;">
        <%
            Book book = (Book) request.getAttribute(AttributeName.BookToDisplay);
        %>
        <div class="row book-item-block" style="background-color: #427278;">
            <bd:book book="<%=book%>" dontMakeLinkToBook="<%=true%>" />
        </div>
    </div>

</body>
</html>