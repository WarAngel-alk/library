<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.my.bussiness.beans.Book" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="com.my.enums.AttributeName" %>
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
        Book b = (Book) request.getAttribute(AttributeName.BookToDisplay);

        String title = b.getTitle();
        String author = b.getAuthor();

        String pictureUrl = (b.getPictureUrl() != null) ? b.getPictureUrl() : "";
        String comment = (b.getComment() != null) ? b.getComment() : "";

        String startDate = (b.getStartDate() != null) ? sdf.format(b.getStartDate()) : "";
        String endDate = (b.getEndDate() != null) ? sdf.format(b.getEndDate()) : "";
        String rating = (b.getRating() != null) ? b.getRating().toString() : "";
    %>
    <div id="resultMessageBox">
        <%
            List<String> errorsList =
                    (List<String>) request.getAttribute(AttributeName.ErrorsList);
            if(errorsList != null) {
                if(errorsList.size() == 0) { %>
                    <div class="label label-success">
                        Book has been updated successfully!
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
        <input type="text" class="input-group add-item" id="param_Title" name="param_Title"
               required="required" placeholder="Title" value="<%=title%>" />
        <input type="text" class="input-group add-item" id="param_Author" name="param_Author"
               required="required" placeholder="Author" value="<%=author%>" />
        <input type="number" class="input-group add-item" id="param_Rating" name="param_Rating"
               placeholder="Rating" min="0" max="10" value="<%=rating%>" />
        <input type="date" class="input-group add-item" id="param_StartDate"
               name="param_StartDate" placeholder="Start reading" value="<%=startDate%>" />
        <input type="date" class="input-group add-item" id="param_EndDate" name="param_EndDate"
               placeholder="End reading" value="<%=endDate%>" />
        <input type="url" class="input-group add-item" id="param_PictureUrl"
               name="param_PictureUrl" placeholder="Picture URL" value="<%=pictureUrl%>" />
        <textarea class="input-group add-item" id="param_Comment" name="param_Comment" placeholder="Comment"
                  style="resize:none; overflow:hidden;" rows="7"
                  onkeyup="$(this).height(this.scrollHeight);" ><%=comment%></textarea>
        <input type="submit" class="btn" id="submit_btn" value="Update book" style="float: right;" />
    </form>
</div>

</body>
</html>