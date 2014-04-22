package com.my.dao.interfaces;

import com.my.bussiness.beans.Book;
import com.my.bussiness.beans.Quote;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 22.04.14
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
public interface QuotesDao {
    List<Quote> selectAll();

    Quote selectById(long id);

    long add(Quote q);

    void delete(Quote q);

    void deleteById(long id);

    long update(Quote q);

    List<Quote> selectByBook(Book book);
}
