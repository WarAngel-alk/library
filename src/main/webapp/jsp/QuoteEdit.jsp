<%@ page import="com.my.bussiness.beans.Book" %>
<%@ page import="com.my.bussiness.beans.Quote" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 29.03.14
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Add book</title>
    <%@ include file="includes/head.jsp" %>
</head>
<body>
<%@ include file="includes/header.jsp" %>
<%--<jsp:include page="includes/header.jsp" flush="true" />--%>

<div class="col-md-6 col-md-offset-3 add-block">
    <%
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Quote q = (Quote) request.getAttribute(AttributeName.QuoteToDisplay);
    %>
    <div id="resultMessageBox">
        <%
            List<String> errorsList =
                    (List) request.getAttribute(AttributeName.ErrorsList);
            if(errorsList != null) {
                if(errorsList.size() == 0) { %>
        <div class="label label-success">
            Quote has been updated successfully!
        </div>
        <% } else {
            for (String msg : errorsList) { %>
        <div class="label label-danger">
            <%=msg%>
        </div>
        <% }
        }
        }
        %>
    </div>
    <form method="post" accept-charset="utf-8">
        <% List<Book> booksList =
                (List<Book>) request.getAttribute(AttributeName.BookToDisplayList); %>
        <select class="add-item" size="1" id="param_book_id" name="param_book_id" size="<%=booksList.size()%>">
            <% for(Book book : booksList) {%>
            <option
                    value="<%=book.getId()%>"
                    <%=(book.getId() == q.getBookId()) ? "selected=\"selected\"" : ""%> >
                <%=book.getAuthor() + " - " + book.getTitle()%>
            </option>
            <% } %>
        </select>
        <textarea class="input-group add-item" id="param_comment" name="param_comment"
                  placeholder="Comment" rows="8" required="required" ><%=q.getText()%></textarea>
        <input type="submit" class="btn" id="submit_btn" style="float: right;" value="Update quote" />
    </form>
</div>

</body>
</html>