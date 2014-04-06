<%@ page import="java.util.TreeMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.my.enums.AttributeName" %>
<%@ page import="com.my.enums.Pages" %>
<div class="header-line">
    <img src="/img/icons/logo.png" width="50" height="50"/>
    <%
        Pages currentPage = (Pages) request.getAttribute(AttributeName.CurrentPage);
        Map<String, String> messagesMap =
                (Map) request.getAttribute(AttributeName.MessagesMap);

        String lblBooksClass = "header-link-passive";
        String lblQuotesClass = "header-link-passive";

        if (currentPage == Pages.AllBooks) {
            lblBooksClass = "header-link-active";
        } else if(currentPage == Pages.AllQuotes) {
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
            <img src="/img/icons/book-add-icon.png" width=40 height=40  />
        </a>
    </div>
    <div class="header-btn" style="float: right; margin-top: 7px; margin-right: 15px" >
        <a href="/quotes/add">
            <img src="/img/icons/quote-add-icon.png" width=40 height=40 />
        </a>
    </div>
</div>

<%
    if (messagesMap != null) {
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
    }
%>