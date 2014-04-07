package com.my.controllers.books;

import com.my.bussiness.beans.Book;
import com.my.dao.BooksDao;
import com.my.enums.Pages;
import com.my.enums.AttributeName;
import com.my.util.HttpUtil;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderedImageFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

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

        if (!loadImageToDir(req, book)) {
            req.setAttribute(AttributeName.BookToDisplay, book);
            getServletContext().getRequestDispatcher("/jsp/AddBook.jsp").forward(req, resp);
            return;
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

    private boolean loadImageToDir(HttpServletRequest req, Book book) throws ServletException, IOException {
        String pictDir = getServletConfig().getInitParameter("books_picture_dir");

        try {
            logger.debug("Loading picture from specified URL");
            URL url = new URL(book.getPictureUrl());
            BufferedImage pict = ImageIO.read(new URL(book.getPictureUrl()));

            String[] urlParts = book.getPictureUrl().split("\\.");
            String ext = urlParts[urlParts.length - 1];
            String pathToSiteRoot = getServletContext().getRealPath("/");
            String fileName = System.currentTimeMillis() + "." + ext;
            String relativePath = "img\\books\\" + fileName;
            File pictFile = new File(pathToSiteRoot + relativePath);

            logger.debug("Writing picture to file");
            ImageIO.write(pict, ext, pictFile);

            book.setPictureUrl("/img/books/" + fileName);
        } catch (MalformedURLException | IllegalArgumentException e1) {
            logger.info("Trying to enter invalid URL for book picture. Url: " + book.getPictureUrl());
            req.setAttribute(AttributeName.ErrorsList,
                    Arrays.asList("Invalid picture URL"));
            return false;
        } catch (IOException e2) {
            logger.info("Can not download picture by URL " + book.getPictureUrl());
            req.setAttribute(AttributeName.ErrorsList,
                    Arrays.asList("Error while downloading picture, try another address"));
            return false;
        }
        return true;
    }

}
