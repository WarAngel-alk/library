<%@ page import="com.my.util.HttpUtil" %>
<%@ page import="com.my.bussiness.beans.Book" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
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

    <div class="col-md-4 col-md-offset-4 add-block">
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Book book = HttpUtil.fillBookWithParams(request);

            String title = (book.getTitle() != null) ? book.getTitle() : "";
            String author = (book.getAuthor() != null) ? book.getAuthor() : "";
            String pictureUrl = (book.getPictureUrl() != null) ? book.getPictureUrl() : "";
            String comment = (book.getComment() != null) ? book.getComment() : "";

            Date _startDate = book.getStartDate();
            Date _endDate = book.getEndDate();
            Long _rating = book.getRating();
            String startDate = (_startDate != null) ? sdf.format(_startDate) : "";
            String endDate = (_endDate != null) ? sdf.format(_endDate) : "";
            String rating = (_rating != null) ? _rating.toString() : "";
        %>
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
        <form method="post" accept-charset="UTF-8">
            <input type="text" class="input-group add-item" id="param_Title" name="param_Title"
                   required="true" placeholder="Title" value="<%=title%>" />
            <input type="text" class="input-group add-item" id="param_Author" name="param_Author"
                   required="true" placeholder="Author" value="<%=author%>" />
            <input type="number" class="input-group add-item" id="param_Rating" name="param_Rating"
                   placeholder="Rating" min="0" max="10" value="<%=rating%>" />
            <input type="date" class="input-group add-item" id="param_StartDate"
                   name="param_StartDate" placeholder="Start reading" value="<%=startDate%>" />
            <input type="date" class="input-group add-item" id="param_EndDate" name="param_EndDate"
                   placeholder="End reading" value="<%=endDate%>" />
            <input type="url" class="input-group add-item" id="param_PictureUrl"
                   name="param_PictureUrl" placeholder="Picture URL" value="<%=pictureUrl%>" />
            <textarea class="input-group add-item" id="param_Comment" name="param_Comment"
                      placeholder="Comment" rows="5"><%=comment%></textarea>
            <input type="submit" class="btn" id="submit_btn" value="Add book" style="float: right;" />
        </form>
    </div>

</body>
</html>