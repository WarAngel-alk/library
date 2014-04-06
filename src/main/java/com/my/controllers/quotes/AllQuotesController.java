package com.my.controllers.quotes;

import com.my.enums.Pages;
import com.my.enums.RequestAttributes;
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
public class AllQuotesController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(getCurrentClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Got request in AllQuotesController");

        logger.info("Request redirected to AllQuotesView");
        req.setAttribute(RequestAttributes.CurrentPage.name(), Pages.AllQuotes);
        getServletContext().getRequestDispatcher("/jsp/AllQuotes.jsp").forward(req, resp);
    }
}
