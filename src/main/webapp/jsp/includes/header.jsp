<div class="header-line">
    <img src="/img/logo.jpg" width="50" height="50"/>
    <jsp:useBean id="currentPage" class="java.lang.String" scope="request" />
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