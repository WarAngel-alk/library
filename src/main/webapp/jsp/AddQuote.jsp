<%@ page import="com.my.bussiness.beans.Book" %>
<%@ page import="com.my.dao.BooksDao" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 15.03.14
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add quote</title>
    <%@ include file="includes/head.jsp" %>
</head>
<body>
    <%@ include file="includes/header.jsp" %>
    <%--<jsp:include page="includes/header.jsp" flush="true" />--%>

    <div class="col-md-4 col-md-offset-4 add-block">
        <div id="resultMessageBox">
            <%
                List<String> errorsList = (List<String>) request.getAttribute("ErrorsList");
                if(errorsList != null) {
                    if(errorsList.size() == 0) { %>
            <div class="label label-success">
                Book has been added successfully!
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
    <form method="post">
        <% List<Book> booksList = new BooksDao().selectAllSimple(); %>
        <select class="add-item" size="1" id="param_book_id" name="param_book_id" size="<%=booksList.size()%>">
            <% for(Book book : booksList) {%>
            <option value="<%=book.getId()%>">
                <%=book.getAuthor() + " - " + book.getTitle()%>
            </option>
            <% } %>
        </select>
        <textarea class="input-group add-item" id="param_comment" name="param_comment"
                  placeholder="Comment" rows="8" required="required" ></textarea>
        <input type="submit" class="btn" id="submit_btn" style="float: right;" value="Add quote" />
    </form>

    </div>

</body>
</html>