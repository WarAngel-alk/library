<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.my.bussiness.beans.Quote" %>
<%--<jsp:useBean id="quoteToDisplay" class="com.my.bussiness.beans.Quote" scope="request"/>--%>
<% Quote quoteToDisplay = (Quote) config.getServletContext().getAttribute("quoteToDisplay"); %>
<% DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy"); %>

<div style="width: 100%;">
    <%= quoteToDisplay.getText() %>
</div>
<div class="quote-item-info">
    <hr style="margin: 0;">
    <% if(quoteToDisplay.getBookId() != 0) { %>
        <a href="/books/id/<%= quoteToDisplay.getBookId() %>">
            <%= quoteToDisplay.getBookAuthor() %> - <%= quoteToDisplay.getBookTitle() %>
        </a>
    <% } else { %>
        <%= quoteToDisplay.getBookAuthor() %> - <%= quoteToDisplay.getBookTitle() %>
    <% } %>
    <br>
    Added: <%= quoteToDisplay.getAddDate() %>
</div>