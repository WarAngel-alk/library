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