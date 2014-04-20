<%@ page import="com.my.enums.AttributeName" %>
<%@ page import="com.my.enums.MessageType" %>
<%@ page import="com.my.enums.Pages" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<div class="header-line">
    <a href="/"><img src="/img/icons/logo.png" width="50" height="50"/></a>
    <%
        Pages currentPage = (Pages) request.getAttribute(AttributeName.CurrentPage);
        Map<MessageType, String> messagesMap =
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
        Set<MessageType> keys = messagesMap.keySet();

        for(MessageType key : keys) {
            String msgType = "label-info";
            if (key == MessageType.Success) {
                msgType = "label-success";
            } else if (key == MessageType.Warning) {
                msgType = "label-warning";
            } else if (key == MessageType.Danger) {
                msgType = "label-danger";
            } else if (key == MessageType.Info) {
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