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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.my.util.LogUtil.getCurrentClass;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 26.02.14
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class BookByIdController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(getCurrentClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Got request in BookByIdController");

        String[] pathArray = req.getRequestURI().split("/");
        long id = Long.parseLong(pathArray[4]);
        req.setAttribute("bookToDisplayId", id);

        if (pathArray.length > 5) {
            String subPath = pathArray[5];
            if (subPath.equals("quotes")) {
                logger.info("Request redirected to QuotesByBookView");
                req.setAttribute("currentPage", "quotesByBook");
                getServletContext().getRequestDispatcher("/jsp/QuotesByBook.jsp").forward(req, resp);
            } else if (subPath.equals("edit")) {
                logger.debug("Selecting book for editing from DB");
                req.setAttribute("bookToEdit", new BooksDao().selectById(id));

                logger.info("Request redirecting to BookEditView");
                req.setAttribute("currentPage", "bookEdit");
                getServletContext().getRequestDispatcher("/jsp/BookEdit.jsp").forward(req, resp);
            }
        } else {
            logger.info("Request redirected to BookByIdView");
            req.setAttribute("currentPage", "bookById");
            getServletContext().getRequestDispatcher("/jsp/BookById.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Getting post request in BookByIdController");
        String[] pathArray = req.getRequestURI().split("/");
        if(pathArray.length > 5 && pathArray[5].equals("edit")) {
            logger.debug("Filling book's properties with request parameters");
            Book formedBook = HttpUtil.fillBookWithParams(req);
            formedBook.setId(Long.parseLong(pathArray[4]));

            logger.debug("Passing formed book to update entry in DB");
            long result = new BooksDao().update(formedBook);

            if(result != 0) {
                logger.debug("Book updated, redirecting to this book page");
                req.setAttribute("bookToDisplayId", result);
                getServletContext().getRequestDispatcher("/jsp/BookById.jsp").forward(req, resp);
            } else {
                logger.warn("Error while updating book in DB");
                List<String> errors = Arrays.asList("Error while updating book in DB. Try again later.");
            }
        }
    }
}