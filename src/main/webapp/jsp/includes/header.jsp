<%@ page import="java.util.TreeMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<div class="header-line">
    <img src="/img/logo.jpg" width="50" height="50"/>
    <jsp:useBean id="currentPage" class="java.lang.String" scope="request" />
    <jsp:useBean id="messagesMap" class="java.util.TreeMap" scope="request" />
    <%
        String lblBooksClass = "header-link-passive";
        String lblQuotesClass = "header-link-passive";

        if (currentPage.equals("allBooks")) {
            lblBooksClass = "header-link-active";
        } else if(currentPage.equals("allQuotes")) {
            lblQuotesClass = "header-link-active";
        }
    %>
    <div class="header-link <%=lblBooksClass%>">
        <a href="/books">Books</a>
    </div>
    <div class="header-link <%=lblQuotesClass%>">
        <a href="/quotes">Quotes</a>
    </div>

    <div class="header-btn" style="float: right; margin-top: 7px; margin-right: 25px" >
        <a href="/books/add">
            <img src="/img/book-add-icon.png" width=40 height=40  />
        </a>
    </div>
    <div class="header-btn" style="float: right; margin-top: 7px; margin-right: 15px" >
        <a href="/quotes/add">
            <img src="/img/quote-add-icon.png" width=40 height=40 />
        </a>
    </div>
</div>

<%
    Set<String> keys = messagesMap.keySet();

    for(String key : keys) {
        String msgType;
        if (key.equals("danger")) {
            msgType = "label-danger";
        } else if (key.equals("warning")) {
            msgType = "label-warning";
        } else if (key.equals("success")) {
            msgType = "label-success";
        } else {
            msgType = "label-info";
        }
%>
<div class="<%=msgType%> page-message">
    <%=messagesMap.get(key)%>
</div>
<%
    }
%>