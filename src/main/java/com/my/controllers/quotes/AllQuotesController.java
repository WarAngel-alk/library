package com.my.controllers.quotes;

import com.my.bussiness.beans.Quote;
import com.my.dao.interfaces.QuotesDao;
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
public class AllQuotesController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(getCurrentClass());

    private static QuotesDao quotesDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processShowAllQuotes(req, resp);
    }

    private void processShowAllQuotes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Requesting to QuotesDao for all quotes list");
        List<Quote> quoteList = quotesDao.selectAll();
        req.setAttribute(AttributeName.QuoteToDisplayList, quoteList);

        logger.info("Request redirected to AllQuotesView");
        req.setAttribute(AttributeName.CurrentPage, Pages.AllQuotes);
        getServletContext().getRequestDispatcher("/jsp/AllQuotes.jsp").forward(req, resp);
    }

    public void setQuotesDao(QuotesDao quotesDao) {
        this.quotesDao = quotesDao;
    }
}
