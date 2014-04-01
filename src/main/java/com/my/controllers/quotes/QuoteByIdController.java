package com.my.controllers.quotes;

import com.my.bussiness.beans.Book;
import com.my.bussiness.beans.Quote;
import com.my.dao.BooksDao;
import com.my.dao.QuotesDao;
import com.my.util.HttpUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class QuoteByIdController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(getCurrentClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Got request in QuoteByIdController");

        String[] pathArray = req.getRequestURI().split("/");
        long id = Long.parseLong(pathArray[4]);
        req.setAttribute("quoteToDisplayId", id);

        if (pathArray.length > 5) {
            String subPath = pathArray[5];
            if (subPath.equals("edit")) {
                logger.debug("Selecting quote for editing from DB");
                req.setAttribute("quoteToEdit", new QuotesDao().selectById(id));

                logger.info("Request redirecting to QuoteEditView");
                req.setAttribute("currentPage", "quoteEdit");
                getServletContext().getRequestDispatcher("/jsp/QuoteEdit.jsp").forward(req, resp);
            }
        } else {
            logger.info("Request redirected to QuoteByIdView");
            req.setAttribute("currentPage", "quotesById");
            getServletContext().getRequestDispatcher("/jsp/QuoteById.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Getting post request in QuoteByIdController");
        String[] pathArray = req.getRequestURI().split("/");
        if(pathArray.length > 5 && pathArray[5].equals("edit")) {
            logger.debug("Filling quotes's properties with request parameters");
            Quote formedQuote = HttpUtil.fillQuoteWithParams(req);
            formedQuote.setId(Long.parseLong(pathArray[4]));

            logger.debug("Passing formed book to update entry in DB");
            long result = new QuotesDao().update(formedQuote);

            if(result != 0) {
                logger.debug("Quote updated, redirecting to this quote page");
                req.setAttribute("quoteToDisplayId", result);
                resp.sendRedirect("/library/quotes/id/" + result);
            } else {
                logger.warn("Error while updating quote in DB");
                List<String> errors = Arrays.asList("Error while updating quote in DB. Try again later.");
                resp.sendRedirect(req.getRequestURI());
            }
        }
    }
}
