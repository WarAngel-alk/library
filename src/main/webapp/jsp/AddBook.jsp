<%@ page import="com.my.util.HttpUtil" %>
<%@ page import="com.my.bussiness.beans.Book" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="com.my.enums.AttributeName" %>
<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 15.03.14
  Time: 12:55
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
            Book book = (Book) request.getAttribute(AttributeName.BookToDisplay);

            String startDate = (book.getStartDate() != null) ? sdf.format(book.getStartDate()) : "";
            String endDate = (book.getEndDate() != null) ? sdf.format(book.getEndDate()) : "";
            String rating = (book.getRating() != null) ? book.getRating().toString() : "";
        %>
        <div id="resultMessageBox">
            <%
                List<String> errorsList =
                        (List<String>) request.getAttribute(AttributeName.ErrorsList);
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
        <form method="post" accept-charset="UTF-8">
            <input type="text" class="input-group add-item" id="param_Title" name="param_Title"
                   required="true" placeholder="Title" value="<%=book.getTitle()%>" />
            <input type="text" class="input-group add-item" id="param_Author" name="param_Author"
                   required="true" placeholder="Author" value="<%=book.getAuthor()%>" />
            <input type="number" class="input-group add-item" id="param_Rating" name="param_Rating"
                   placeholder="Rating" min="0" max="10" value="<%=rating%>" />
            <input type="date" class="input-group add-item" id="param_StartDate"
                   name="param_StartDate" placeholder="Start reading" value="<%=startDate%>" />
            <input type="date" class="input-group add-item" id="param_EndDate" name="param_EndDate"
                   placeholder="End reading" value="<%=endDate%>" />
            <input type="url" class="input-group add-item" id="param_PictureUrl"
                   name="param_PictureUrl" placeholder="Picture URL" value="<%=book.getPictureUrl()%>" />
            <textarea class="input-group add-item" id="param_Comment" name="param_Comment"
                      placeholder="Comment" rows="5"><%=book.getComment()%></textarea>
            <input type="submit" class="btn" id="submit_btn" value="Add book" style="float: right;" />
        </form>
    </div>

</body>
</html>