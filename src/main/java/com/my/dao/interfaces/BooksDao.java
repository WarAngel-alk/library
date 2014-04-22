package com.my.dao.interfaces;

import com.my.bussiness.beans.Book;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 22.04.14
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 */
public interface BooksDao {
    List<Book> selectAll();

    List<Book> selectAllSimple();

    Book selectById(long id);

    long add(Book book);

    long update(Book b);

    void delete(Book book);
}
