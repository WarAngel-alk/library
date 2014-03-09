package com.my.bussiness.beans;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 09.03.14
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class Book {

    private String title;
    private String author;
    private int rating;
    private int year;
    private String comment;
    private String startDate;
    private String endDate;
    private String pictureUrl;
    private int userId;
    private String userName;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book() {
        title = "<Not specified>";
        author = "<Not specified>";
        rating = 0;
        year = 0;
        comment = "<Not specified>";
        startDate = "<Not specified>";
        endDate = "<Not specified>";
        pictureUrl = "<Not specified>";
        userId = 0;
        userName = "<Not founded>";
        id = 0;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
