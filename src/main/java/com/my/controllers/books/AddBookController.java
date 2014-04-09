package com.my.controllers.books;

import com.my.bussiness.beans.Book;
import com.my.dao.BooksDao;
import com.my.enums.AttributeName;
import com.my.enums.Pages;
import com.my.util.HttpUtil;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import static com.my.util.LogUtil.getCurrentClass;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 15.03.14
 * Time: 12:50
 * To change this template use File | Settings | File Templates.
 */
public class AddBookController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(getCurrentClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Got request in AddBookController");

        logger.debug("Parsing request parameters to form book");
        Book b = HttpUtil.fillBookWithParams(req);

        if(b.getTitle() == null) b.setTitle("");
        if(b.getAuthor() == null) b.setAuthor("");
        if(b.getPictureUrl() == null) b.setPictureUrl("");
        if(b.getComment() == null) b.setComment("");

        req.setAttribute(AttributeName.BookToDisplay, b);

        logger.info("Request redirected to AddBookView");
        req.setAttribute(AttributeName.CurrentPage, Pages.AddBook);
        getServletContext().getRequestDispatcher("/jsp/AddBook.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Filling book's properties with request parameters");
        Book book = HttpUtil.fillBookWithParams(req);

        String pictDir = "\\img\\books\\";

        if(book.getPictureUrl() != null) {
            String picturePath = null;
            try {
                picturePath = saveImage(book.getPictureUrl(), pictDir);
            } catch (MalformedURLException e1) {
                logger.warn("Trying to enter invalid URL for book picture. Url: " + book.getPictureUrl());
                req.setAttribute(AttributeName.ErrorsList, Arrays.asList("Invalid picture URL"));
            } catch (IOException e2) {
                logger.warn("Can not download picture by URL " + book.getPictureUrl());
                req.setAttribute(AttributeName.ErrorsList,
                        Arrays.asList("Error while downloading picture, try another address"));
            }

            // if picture not saved
            if (picturePath == null) {
                logger.info("Request redirected back to AddBook page " +
                        "because of error while saving picture");
                req.setAttribute(AttributeName.BookToDisplay, book);
                getServletContext().getRequestDispatcher("/jsp/AddBook.jsp").forward(req, resp);
                return;
            } else {
                book.setPictureUrl(picturePath.replace("\\", "/"));
            }
        }

        logger.debug("Passing new book bean to BooksDao to insert in DB");
        BooksDao bDao = new BooksDao();
        long addResult = bDao.add(book);

        if(addResult == 0) {
            logger.warn("Error while adding book to DB");
            req.setAttribute(AttributeName.ErrorsList,
                    Arrays.asList("Error while adding book to DB."));
            req.setAttribute(AttributeName.BookToDisplay, book);
            getServletContext().getRequestDispatcher("/jsp/AddBook.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/books/id/" + addResult);
        }

    }

    private String saveImage(String imgUrl, String dir)
            throws MalformedURLException, IOException, ServletException {
        String pathToFile = null;
        URL url = new URL(imgUrl);

        try {
            logger.debug("Checking URL correctness");
            String[] urlParts = imgUrl.split("\\.");
            String ext = urlParts[urlParts.length - 1];

            logger.debug("Loading picture from specified URL");
            BufferedImage pict = ImageIO.read(new URL(imgUrl));

            String fileName = System.currentTimeMillis() + "." + ext;
            pathToFile = dir + fileName;
            String fullPath = getServletContext().getRealPath(pathToFile);
            File pictFile = new File(fullPath);

            logger.debug("Writing picture to file");
            ImageIO.write(pict, ext, pictFile);
        } catch (IOException e1) {
            throw e1;
        } catch (Exception e2) {
            throw new MalformedURLException("URL is invalid");
        }

        return pathToFile;
    }

}
