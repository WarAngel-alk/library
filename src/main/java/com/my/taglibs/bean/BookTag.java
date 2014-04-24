package com.my.taglibs.bean;

import com.my.bussiness.beans.Book;

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
 * Time: 8:49
 * To change this template use File | Settings | File Templates.
 */
public class BookTag extends TagSupport {

    private Book book = null;
    private boolean dontMakeLinkToBook = false;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

        String picturePath = (book.getPictureUrl() == null) ? "/img/icons/logo.png" : book.getPictureUrl();
        String addDate = dateFormat.format(book.getAddDate());
        String bookId = String.valueOf(book.getId());
        String bookAuthor = book.getAuthor();
        String bookTitle = book.getTitle();
        String bookRating = (book.getRating() != null && book.getRating() != 0) ? String.valueOf(book.getRating()) : null;
        String startDate = (book.getStartDate() != null) ? dateFormat.format(book.getStartDate()) : null;
        String endDate = (book.getEndDate() != null) ? dateFormat.format(book.getEndDate()) : null;
        String comment = (book.getComment() != null && book.getComment().length() != 0) ? book.getComment() : null;


        try {
            // Not by code convention, but I think it's looks better
out.write("<div class=\"row\">\n");
out.write("    <div class=\"col-md-2\">\n");
out.write("        <img src=\"" + picturePath + "\" width=\"96\" height=\"128\" />\n");
out.write("    </div>\n");

out.write("    <div class=\"col-md-10\" style=\"padding-left: 0;\">\n");
out.write("        <div style=\"float: right;\">\n");
out.write("            <span class=\"book-item-info\">Added: " + addDate + "</span><br>\n");
out.write("            <a href=\"/books/id/" + bookId + "/edit\" >\n");
out.write("                <img src=\"/img/icons/edit-icon.png\" />\n");
out.write("            </a>\n");
out.write("            <a href=\"/books/id/" + bookId + "/quotes\" >\n");
out.write("                <img src=\"/img/icons/quote-icon.png\" />\n");
out.write("            </a>\n");
out.write("            <a href=\"/books/id/" + bookId + "/delete\" >\n");
out.write("                <img src=\"/img/icons/book-delete-icon.png\" />\n");
out.write("            </a>\n");
out.write("        </div>\n");
if(!dontMakeLinkToBook) {
    out.write("        <a href=\"/books/id/" + bookId + "\">\n");
}
out.write("            <div>\n");
out.write("                <span class=\"book-item-param-name\">Title:</span>\n");
out.write("                <span class=\"book-item-param-value\">" + bookTitle + "</span>\n");
out.write("            </div>\n");
out.write("            <div>\n");
out.write("                <span class=\"book-item-param-name\">Author:</span>\n");
out.write("                <span class=\"book-item-param-value\">" + bookAuthor + "</span>\n");
out.write("            </div>\n");
if(!dontMakeLinkToBook) {
    out.write("        </a>\n");
}

if(bookRating != null) {
out.write("        <div>\n");
out.write("            <span class=\"book-item-param-name\">Rating:</span>\n");
out.write("            <span class=\"book-item-param-value\">" + bookRating + "</span>\n");
out.write("        </div>\n");
}
out.write("    </div>\n");
out.write("</div>\n");

out.write("<div class=\"row\">\n");
if(startDate != null) {
out.write("    <div>\n");
out.write("        <span class=\"book-item-param-name\">Start reading:</span>\n");
out.write("        <span class=\"book-item-param-value\">" + startDate + "</span>\n");
out.write("    </div>\n");
}
if(endDate != null) {
out.write("    <div>\n");
out.write("        <span class=\"book-item-param-name\">End reading:</span>\n");
out.write("        <span class=\"book-item-param-value\">" + endDate + "</span>\n");
out.write("    </div>\n");
}
if(comment != null) {
out.write("    <div>\n");
out.write("        <span class=\"book-item-param-name\">Comment:</span>\n");
out.write("        <span class=\"book-item-param-value\">" + comment + "</span>\n");
out.write("    </div>\n");
}
out.write("</div>");
        } catch (IOException e) {
            throw new JspException("(Exception)" + e.getMessage());
        }
        return SKIP_BODY;
    }

    public boolean isDontMakeLinkToBook() {
        return dontMakeLinkToBook;
    }

    public void setDontMakeLinkToBook(boolean dontMakeLinkToBook) {
        this.dontMakeLinkToBook = dontMakeLinkToBook;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
