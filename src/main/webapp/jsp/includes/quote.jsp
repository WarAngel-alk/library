<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.my.bussiness.beans.Quote" %>
<%@ page import="com.my.enums.RequestAttributes" %>
<% Quote quoteToDisplay =
        (Quote) config.getServletContext().getAttribute(RequestAttributes.QuoteToDisplay.name()); %>
<% DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy"); %>

<div style="width: 100%;">
    <%= quoteToDisplay.getText() %>
</div>
<div class="quote-item-info">
    <hr style="margin: 0;">
    <% if(quoteToDisplay.getBookId() != 0) { %>
        <a href="/books/id/<%= quoteToDisplay.getBookId() %>">
            <%= quoteToDisplay.getBookAuthor() %>&nbsp-&nbsp<%= quoteToDisplay.getBookTitle() %>
        </a>
    <% } else { %>
        <%= quoteToDisplay.getBookAuthor() %> - <%= quoteToDisplay.getBookTitle() %>
    <% } %>
    <br>
    Added: <%= quoteToDisplay.getAddDate() %>
</div>
<div style="float: right; margin-top: 10px; margin-right: 10px;">
    <a href="/quotes/id/<%=quoteToDisplay.getId()%>/edit" >
        <img src="/img/edit-icon.png" />
    </a>
    <a href="/books/id/<%=quoteToDisplay.getBookId()%>" >
        <img src="/img/book-icon.png" />
    </a>
    <a href="/quotes/id/<%=quoteToDisplay.getId()%>/delete" >
        <img src="/img/quote-delete-icon.png" />
    </a>
</div>