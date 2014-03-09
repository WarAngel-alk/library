package com.my.bussiness.beans;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 09.03.14
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class Quote {

    private String bookTitle;
    private String text;
    private String author;
    private int id;
    private int bookId;

    public Quote() {
        bookTitle = "<Not specified>";
        text = "<Not specified>";
        author = "<Not specified>";
        id = 0;
        bookId = 0;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
