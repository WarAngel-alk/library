package com.my.controllers.books;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        if(pathArray.length >= 5 && pathArray[5].equals("quotes")) {
            logger.info("Request redirected to QuotesByBookView");
            req.setAttribute("currentPage", "quotesByBook");
            getServletContext().getRequestDispatcher("/jsp/QuotesByBook.jsp").forward(req, resp);
        } else {
            logger.info("Request redirected to BookByIdView");
            req.setAttribute("currentPage", "bookById");
            getServletContext().getRequestDispatcher("/jsp/BookById.jsp").forward(req, resp);
        }
    }
}