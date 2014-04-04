<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.my.bussiness.beans.Book" %>
<%--<jsp:useBean id="quoteToDisplay" class="com.my.bussiness.beans.Quote" scope="request"/>--%>
<%  Book bookToDisplay = (Book) config.getServletContext().getAttribute("bookToDisplay");
    DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
    String pictPath = (bookToDisplay.getPictureUrl() == null) ? "/img/logo.jpg" : bookToDisplay.getPictureUrl();
%>

<div class="row">
    <div class="col-md-2">
        <img src="<%=pictPath%>" width="96" height="128" />
    </div>

    <div class="col-md-10" style="padding-left: 0;">
        <div style="float: right;">
            <span class="book-item-info">Added: <%=dateFormat.format(bookToDisplay.getAddDate())%></span><br>
            <a href="/books/id/<%=bookToDisplay.getId()%>/edit" >
                <img src="/img/edit-icon.png" />
            </a>
            <a href="/books/id/<%=bookToDisplay.getId()%>/quotes" >
                <img src="/img/quote-icon.png" />
            </a>
            <a href="/books/id/<%=bookToDisplay.getId()%>/delete" >
                <img src="/img/book-delete-icon.png" />
            </a>
        </div>
        <% if(!request.getAttribute("currentPage").equals("bookById")) { %>
        <a href="/books/id/<%=bookToDisplay.getId()%>">
        <% } %>
            <div>
                <span class="book-item-param-name">Title:</span>
                <span class="book-item-param-value"><%=bookToDisplay.getTitle()%></span>
            </div>
            <div>
                <span class="book-item-param-name">Author:</span>
                <span class="book-item-param-value"><%=bookToDisplay.getAuthor()%></span>
            </div>
        <% if(!request.getAttribute("currentPage").equals("bookById")) { %>
        </a>
        <% } %>

        <% if(bookToDisplay.getRating() != 0) { %>
        <div>
            <span class="book-item-param-name">Rating:</span>
            <span class="book-item-param-value"><%=bookToDisplay.getRating()%></span>
        </div>
        <% } %>
    </div>
    <div class="col-md-1">

    </div>
</div>
<div class="row">
    <% if(bookToDisplay.getStartDate() != null) { %>
    <div>
        <span class="book-item-param-name">Start reading:</span>
        <span class="book-item-param-value"><%=dateFormat.format(bookToDisplay.getStartDate())%></span>
    </div>
    <% } %>
    <% if(bookToDisplay.getEndDate() != null) { %>
    <div>
        <span class="book-item-param-name">End reading:</span>
        <span class="book-item-param-value"><%=dateFormat.format(bookToDisplay.getEndDate())%></span>
    </div>
    <% } %>
    <% if(bookToDisplay.getComment() != null && bookToDisplay.getComment().length() != 0) { %>
    <div>
        <span class="book-item-param-name">Comment:</span>
        <span class="book-item-param-value"><%=bookToDisplay.getComment()%></span>
    </div>
    <% } %>
</div>
