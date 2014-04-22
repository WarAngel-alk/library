package com.my.controllers.books;

import com.my.bussiness.beans.Book;
import com.my.dao.BooksDaoImpl;
import com.my.dao.interfaces.BooksDao;
import com.my.enums.AttributeName;
import com.my.enums.Pages;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.my.util.LogUtil.getCurrentClass;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 26.02.14
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class AllBooksController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(getCurrentClass());
    private final BooksDao bDao = new BooksDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processShowAllBooks(req, resp);
    }

    private void processShowAllBooks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Requesting to BooksDao for all books list");
        List<Book> booksList = bDao.selectAll();
        req.setAttribute(AttributeName.BookToDisplayList, booksList);

        logger.info("Request redirected to AllBooksView");
        req.setAttribute(AttributeName.CurrentPage, Pages.AllBooks);
        getServletContext().getRequestDispatcher("/jsp/AllBooks.jsp").forward(req, resp);
    }
}
