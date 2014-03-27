package com.my.controllers.quotes;

import com.my.bussiness.beans.Quote;
import com.my.dao.QuotesDao;
import com.my.util.HttpUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static com.my.util.LogUtil.getCurrentClass;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 15.03.14
 * Time: 12:49
 * To change this template use File | Settings | File Templates.
 */
public class AddQuoteController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(getCurrentClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Got request in AddQuoteController");

        logger.info("Request redirected to AddQuoteView");
        req.setAttribute("currentPage", "addQuote");
        getServletContext().getRequestDispatcher("/jsp/AddQuote.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Getting post request in AddQuoteController");

        logger.debug("Filling quote's properties with request parameters");
        Quote quote = HttpUtil.fillQuoteWithParams(req);
        long addResult = new QuotesDao().add(quote);

        if(addResult == 0) {
            logger.warn("Error while adding quote to DB");
            req.setAttribute("ErrorsList", Arrays.asList("Error while adding quote to DB."));
            getServletContext().getRequestDispatcher("/jsp/AddQuote.jsp").forward(req, resp);
        } else {
            logger.info("Added quote with id=" + addResult + "; redirecting to page with new quote");
            resp.sendRedirect("/library/quotes/id/" + addResult);
        }
    }
}
