package com.my.taglibs.bean;

import com.my.bussiness.beans.Quote;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 21.04.14
 * Time: 10:14
 * To change this template use File | Settings | File Templates.
 */
public class QuoteTag extends TagSupport {

    private Quote quote = null;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

        String quoteText = quote.getText();
        String quoteId = String.valueOf(quote.getId());
        String addDate = dateFormat.format(quote.getAddDate());

        String bookId = null;
        String bookAuthor = null;
        String bookTitle = null;
        if(quote.getBookId() != 0) {
            bookId = String.valueOf(quote.getBookId());
            bookAuthor = quote.getBookAuthor();
            bookTitle = quote.getBookTitle();
        }

        try {
out.write("<div style=\"width: 100%;\">\n");
out.write("    " + quoteText + "\n");
out.write("</div>\n");
out.write("<div class=\"quote-item-info\">\n");
out.write("    <hr style=\"margin: 0;\">\n");
if(bookId != null) {
    out.write("        <a href=\"/books/id/" + bookId + "\">\n");
    out.write("            " + bookAuthor + "&nbsp-&nbsp" + bookTitle + "\n");
    out.write("        </a>\n");
} else {
    out.write("        " + bookAuthor + "&nbsp-&nbsp" + bookTitle + "\n");
}
out.write("    <br>\n");
out.write("    Added: " + addDate + "\n");
out.write("</div>\n");
out.write("<div style=\"float: right; margin-top: 10px; margin-right: 10px;\">\n");
out.write("    <a href=\"/quotes/id/" + quoteId + "/edit\" >\n");
out.write("        <img src=\"/img/icons/edit-icon.png\" />\n");
out.write("    </a>\n");
out.write("    <a href=\"/books/id/" + bookId + "\" >\n");
out.write("        <img src=\"/img/icons/book-icon.png\" />\n");
out.write("    </a>\n");
out.write("    <a href=\"/quotes/id/" + quoteId +"/delete\" >\n");
out.write("        <img src=\"/img/icons/quote-delete-icon.png\" />\n");
out.write("    </a>\n");
out.write("</div>");
        } catch (IOException e1) {
            throw new JspException("(Exception)" +e1.getMessage());
        }

        return SKIP_BODY;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }
}
