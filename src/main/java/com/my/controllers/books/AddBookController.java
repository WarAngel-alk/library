package com.my.controllers.books;

import com.my.bussiness.beans.Book;
import com.my.dao.BooksDao;
import com.my.util.HttpUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static com.my.util.LogUtil.getCurrentClass;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 15.03.14
 * Time: 12:50
 * To change this template use File | Settings | File Templates.
 */
public class AddBookController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(getCurrentClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Got request in AddBookController");

        logger.info("Request redirected to AddBookView");
        req.setAttribute("currentPage", "addBook");
        getServletContext().getRequestDispatcher("/jsp/AddBook.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Filling book's properties with request parameters");
        Book book = HttpUtil.fillBookWithParams(req);

        logger.debug("Passing new book bean to BooksDao to insert in DB");
        BooksDao bDao = new BooksDao();
        long addResult = bDao.add(book);

        if(addResult == 0) {
            logger.warn("Error while adding book to DB");
            req.setAttribute("ErrorsList", Arrays.asList("Error while adding book to DB."));
            getServletContext().getRequestDispatcher("/jsp/AddBook.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/library/books/id/" + addResult);
        }

    }

}
