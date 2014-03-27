package com.my.util;

import com.my.bussiness.beans.Book;
import com.my.bussiness.beans.Quote;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.my.util.LogUtil.getCurrentClass;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 22.03.14
 * Time: 8:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class HttpUtil {

    private static final Logger logger = Logger.getLogger(getCurrentClass());

    public static Book fillBookWithParams(HttpServletRequest req) {
        logger.debug("Getting properties from request parameters");

        String title = req.getParameter("param_Title");
        String author = req.getParameter("param_Author");
        String comment = req.getParameter("param_Comment");
        String pictureUrl = req.getParameter("param_PictureUrl");
        String strRating = req.getParameter("param_Rating");
        String strStartDate = req.getParameter("param_StartDate");
        String strEndDate = req.getParameter("param_EndDate");

        if(pictureUrl == null || pictureUrl.length() == 0)  pictureUrl = null;
        if(comment == null || comment.length() == 0)        comment = null;

        logger.debug("Formatting string date to Date object");
        Date startDate = null;
        Date endDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(strStartDate != null && !strStartDate.equals("")) startDate = sdf.parse(strStartDate);
            if(strEndDate != null && !strEndDate.equals("")) endDate = sdf.parse(strEndDate);
        } catch (ParseException e1) {
            logger.warn("Incorrect date format", e1);
        }

        logger.debug("Formatting string number (rating) to Long object");
        Long rating = null;
        try {
            if(strRating != null) rating = Long.parseLong(strRating);
        } catch (NumberFormatException e2) {
            logger.warn("Incorrect number format (rating)", e2);
        }

        logger.debug("Setting properties of book");
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setRating(rating);
        book.setStartDate(startDate);
        book.setEndDate(endDate);
        book.setAddDate(new Date());
        book.setComment(comment);
        book.setPictureUrl(pictureUrl);
        // TODO: Why did I decided that I want to know when book was published?!

        return book;
    }

    public static Quote fillQuoteWithParams(HttpServletRequest req) {
        Quote quote = new Quote();

        Long id = Long.valueOf(req.getParameter("param_book_id"));
        String comment = req.getParameter("param_comment");

        quote.setBookId(id);
        quote.setText(comment);
        quote.setAddDate(new Date());

        return quote;
    }


}
