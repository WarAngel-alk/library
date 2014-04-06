package com.my.controllers.quotes;

import com.my.bussiness.beans.Quote;
import com.my.dao.QuotesDao;
import com.my.enums.Pages;
import com.my.enums.RequestAttributes;
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
        req.setAttribute(RequestAttributes.QuoteToDisplayId.name(), id);

        if (pathArray.length > 4) {
            String subPath = pathArray[4];
            if (subPath.equals("edit")) {
                logger.debug("Selecting quote for editing from DB");
                req.setAttribute(RequestAttributes.QuoteToEdit.name(),
                        new QuotesDao().selectById(id));

                logger.info("Request redirecting to QuoteEditView");
                req.setAttribute(RequestAttributes.CurrentPage.name(), Pages.QuoteEdit);
                getServletContext().getRequestDispatcher("/jsp/QuoteEdit.jsp").forward(req, resp);
            } else if (subPath.equals("delete")) {
                logger.debug("Deleting quote with id=" + id + " from DB");
                new QuotesDao().deleteById(id);

                logger.debug("Putting delete message to messageMap");
                Map<String, String> messageMap =
                        (Map<String, String>) req.getAttribute(RequestAttributes.MessagesMap.name());
                if(messageMap == null) {
                    messageMap = new TreeMap<String, String>();
                }
                messageMap.put("success", "Quote have been deleted successfully");
                req.setAttribute(RequestAttributes.MessagesMap.name(), messageMap);

                logger.info("Request redirected to AllQuotesView");
                req.setAttribute(RequestAttributes.CurrentPage.name(), Pages.AllQuotes);
                resp.sendRedirect("/quotes");
            }
        } else {
            logger.info("Request redirected to QuoteByIdView");
            req.setAttribute(RequestAttributes.CurrentPage.name(), Pages.QuoteById);
            getServletContext().getRequestDispatcher("/jsp/QuoteById.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Getting post request in QuoteByIdController");
        String[] pathArray = req.getRequestURI().split("/");
        if(pathArray.length > 4 && pathArray[4].equals("edit")) {
            logger.debug("Filling quotes's properties with request parameters");
            Quote formedQuote = HttpUtil.fillQuoteWithParams(req);
            formedQuote.setId(Long.parseLong(pathArray[3]));

            logger.debug("Passing formed book to update entry in DB");
            long result = new QuotesDao().update(formedQuote);

            if(result != 0) {
                logger.debug("Quote updated, redirecting to this quote page");
                req.setAttribute(RequestAttributes.QuoteToDisplayId.name(), result);
                resp.sendRedirect("/quotes/id/" + result);
            } else {
                logger.warn("Error while updating quote in DB");
                List<String> errors = Arrays.asList("Error while updating quote in DB. Try again later.");
                resp.sendRedirect(req.getRequestURI());
            }
        }
    }
}
