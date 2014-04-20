package com.my.controllers.quotes;

import com.my.bussiness.beans.Quote;
import com.my.dao.QuotesDao;
import com.my.enums.AttributeName;
import com.my.enums.MessageType;
import com.my.enums.Pages;
import com.my.util.HttpUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        long id = Long.parseLong(pathArray[3]);
        req.setAttribute(AttributeName.QuoteToDisplayId, id);

        if (pathArray.length > 4) {
            String subPath = pathArray[4];
            if (subPath.equals("edit")) {
                processEditQuoteGet(req, resp, id);
            } else if (subPath.equals("delete")) {
                processDeleteQuote(req, resp, id);
            }
        } else {
            processShowQuote(req, resp, id);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Getting post request in QuoteByIdController");
        String[] pathArray = req.getRequestURI().split("/");
        if(pathArray.length > 4 && pathArray[4].equals("edit")) {
            processEditQuotePost(req, resp, pathArray[3]);
        }
    }

    private void processShowQuote(HttpServletRequest req, HttpServletResponse resp, long id) throws ServletException, IOException {
        Quote q = new QuotesDao().selectById(id);
        req.setAttribute(AttributeName.QuoteToDisplay, q);

        logger.info("Request redirected to QuoteByIdView");
        req.setAttribute(AttributeName.CurrentPage, Pages.QuoteById);
        getServletContext().getRequestDispatcher("/jsp/QuoteById.jsp").forward(req, resp);
    }

    private void processDeleteQuote(HttpServletRequest req, HttpServletResponse resp, long id) throws IOException {
        logger.debug("Deleting quote with id=" + id + " from DB");
        new QuotesDao().deleteById(id);

        logger.debug("Putting delete message to messageMap");
        Map<MessageType, String> messageMap =
                (Map<MessageType, String>) req.getAttribute(AttributeName.MessagesMap);
        if(messageMap == null) {
            messageMap = new TreeMap<MessageType, String>();
        }
        messageMap.put(MessageType.Success, "Quote have been deleted successfully");
        req.setAttribute(AttributeName.MessagesMap, messageMap);

        logger.info("Request redirected to AllQuotesView");
        req.setAttribute(AttributeName.CurrentPage, Pages.AllQuotes);
        resp.sendRedirect("/quotes");
    }

    private void processEditQuoteGet(HttpServletRequest req, HttpServletResponse resp, long id) throws ServletException, IOException {
        logger.debug("Selecting quote for editing from DB");
        req.setAttribute(AttributeName.QuoteToDisplay,
                new QuotesDao().selectById(id));

        logger.info("Request redirecting to QuoteEditView");
        req.setAttribute(AttributeName.CurrentPage, Pages.QuoteEdit);
        getServletContext().getRequestDispatcher("/jsp/QuoteEdit.jsp").forward(req, resp);
    }

    private void processEditQuotePost(HttpServletRequest req, HttpServletResponse resp, String s) throws IOException {
        logger.debug("Filling quotes's properties with request parameters");
        Quote formedQuote = HttpUtil.fillQuoteWithParams(req);
        formedQuote.setId(Long.parseLong(s));

        logger.debug("Passing formed book to update entry in DB");
        long result = new QuotesDao().update(formedQuote);

        if(result != 0) {
            logger.debug("Quote updated, redirecting to this quote page");
            req.setAttribute(AttributeName.QuoteToDisplayId, result);
            resp.sendRedirect("/quotes/id/" + result);
        } else {
            logger.warn("Error while updating quote in DB");
            List<String> errors = Arrays.asList("Error while updating quote in DB. Try again later.");
            resp.sendRedirect(req.getRequestURI());
        }
    }
}
