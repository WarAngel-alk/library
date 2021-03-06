package com.my.controllers.books;

import com.my.bussiness.beans.Book;
import com.my.bussiness.beans.Quote;
import com.my.dao.interfaces.BooksDao;
import com.my.dao.interfaces.QuotesDao;
import com.my.enums.AttributeName;
import com.my.enums.MessageType;
import com.my.enums.Pages;
import com.my.util.HttpUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
public class BookByIdController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(getCurrentClass());

    private static BooksDao booksDao;
    private static QuotesDao quotesDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Got request in BookByIdController");

        // domain.com/books/id/42
        String[] pathArray = req.getRequestURI().split("/");
        long id = Long.parseLong(pathArray[3]);
        req.setAttribute(AttributeName.BookToDisplayId, id);

        if (pathArray.length > 4) {
            // url like "domain.com/books/id/42/edit"
            String subPath = pathArray[4];
            switch (subPath) {
                case "quotes":
                    processShowBookQuotes(req, resp, id);
                    break;
                case "edit":
                    processEditBookGet(req, resp, id);
                    break;
                case "delete":
                    processDeleteBook(req, resp, id);
                    break;
            }
        } else {
            processShowBook(req, resp, id);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Getting post request in BookByIdController");
        String[] pathArray = req.getRequestURI().split("/");

        if(pathArray.length > 4 && pathArray[4].equals("edit")) {
            processEditBookPost(req, resp, pathArray[3]);
        }
    }

    private void processShowBook(HttpServletRequest req, HttpServletResponse resp, long id) throws ServletException, IOException {
        logger.info("Request redirected to BookByIdView");

        logger.info("Get book from DB");
        Book book = booksDao.selectById(id);
        req.setAttribute(AttributeName.BookToDisplay, book);

        req.setAttribute(AttributeName.CurrentPage, Pages.BookById);
        getServletContext().getRequestDispatcher("/jsp/BookById.jsp").forward(req, resp);
    }

    private void processDeleteBook(HttpServletRequest req, HttpServletResponse resp, long id) throws ServletException, IOException {
        logger.debug("Deleting book with id=" + id + " from DB");
        Book book = booksDao.selectById(id);

        booksDao.delete(book);

        deleteBookPicture(book);

        logger.debug("Putting delete message to messageMap");
        Map<MessageType, String> messageMap =
                (Map<MessageType, String>) req.getAttribute(AttributeName.MessagesMap);
        if(messageMap == null) {
            messageMap = new TreeMap<>();
        }
        messageMap.put(MessageType.Success, "Book have been deleted successfully");
        req.setAttribute(AttributeName.MessagesMap, messageMap);

        List<Book> booksList = booksDao.selectAll();
        req.setAttribute(AttributeName.BookToDisplayList, booksList);

        logger.info("Request redirected to AllBooksView");
        req.setAttribute(AttributeName.CurrentPage, Pages.AllBooks);
        getServletContext().getRequestDispatcher("/jsp/AllBooks.jsp").forward(req, resp);
    }

    private void deleteBookPicture(Book book) {
        if (book.getPictureUrl() != null) {
            String filePath = getServletContext().getRealPath(
                    book.getPictureUrl())
                    .replace("/", System.getProperty("file.separator")
                );
            File picture = new File(filePath);
            if(picture.delete()) {
                logger.debug("Picture deleted successfully");
            } else {
                logger.error("Book was deleted but picture was not deleted!");
            }
        }
    }

    private void processEditBookGet(HttpServletRequest req, HttpServletResponse resp, long id) throws ServletException, IOException {
        logger.debug("Selecting book for editing from DB");
        req.setAttribute(AttributeName.BookToDisplay,
                booksDao.selectById(id));

        logger.info("Request redirecting to BookEditView");
        req.setAttribute(AttributeName.CurrentPage, Pages.BookEdit);
        getServletContext().getRequestDispatcher("/jsp/BookEdit.jsp").forward(req, resp);
    }

    private void processShowBookQuotes(HttpServletRequest req, HttpServletResponse resp, long id) throws ServletException, IOException {
        Book book = new Book();
        book.setId(id);
        List<Quote> quoteList = quotesDao.selectByBook(book);
        req.setAttribute(AttributeName.QuoteToDisplayList, quoteList);

        logger.info("Request redirected to QuotesByBookView");
        req.setAttribute(AttributeName.CurrentPage, Pages.BookQuotes);
        getServletContext().getRequestDispatcher("/jsp/QuotesByBook.jsp").forward(req, resp);
    }

    private void processEditBookPost(HttpServletRequest req, HttpServletResponse resp, String s) throws IOException, ServletException {
        logger.debug("Filling book's properties with request parameters");
        Book formedBook = HttpUtil.fillBookWithParams(req);
        formedBook.setId(Long.parseLong(s));

        logger.debug("Passing formed book to update entry in DB");
        long result = booksDao.update(formedBook);

        if(result != 0) {
            logger.debug("Book updated, redirecting to this book page");
            resp.sendRedirect("/books/id/" + result);
        } else {
            logger.warn("Error while updating book in DB. Redirecting back to the edit book page.");

            List<String> errors = Arrays.asList("Error while updating book in DB. Try again later.");
            req.setAttribute(AttributeName.ErrorsList, errors);

//            resp.sendRedirect(req.getRequestURI());
            getServletContext().getRequestDispatcher("/jsp/BookEdit").forward(req, resp);
        }
    }

    public void setBooksDao(BooksDao booksDao) {
        this.booksDao = booksDao;
    }

    public void setQuotesDao(QuotesDao quotesDao) {
        this.quotesDao = quotesDao;
    }
}