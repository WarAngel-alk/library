package com.my.dao;

import com.my.bussiness.beans.Book;
import com.my.dao.db.ConnectionManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.my.util.LogUtil.getCurrentClass;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 26.02.14
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class BooksDao {

    private static final Logger log = Logger.getLogger(getCurrentClass());

    private static final SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sqlDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public List<Book> selectAll() {
        log.debug("Getting connection from pool");
        Connection con = ConnectionManager.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;

        List<Book> resultList = new ArrayList<Book>();

        try {

            st = con.createStatement();
            log.info("Executing query to DB to select all books");
            rs = st.executeQuery(
                "SELECT " +
                    "`books_id`, `books_title`, `books_author`, " +
                    "`books_rating`, `books_comment`, `books_start_date`, `books_end_date`, " +
                    "`books_picture_url`, `books_add_date` " +
                "FROM " +
                    "`books`");

            while(rs.next()) {
                Book book = new Book();

                book.setId(rs.getLong(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setRating(rs.getLong(4));
                book.setComment(rs.getString(5));
                book.setStartDate(rs.getDate(6));
                book.setEndDate(rs.getDate(7));
                book.setPictureUrl(rs.getString(8));
                book.setAddDate(rs.getDate(9));

                resultList.add(book);
            }

        } catch (SQLException e1) {
            log.warn("Error while executing SQL-query.", e1);
        } catch (Exception e2) {
            log.warn("Unknown error while getting data from DB", e2);
        } finally {
            log.debug("Closing resources of connection...");
            try {
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(con != null) con.close();
            } catch (SQLException e3) {
                log.warn("Some resources have not been closed!", e3);
            }
        }

        return resultList;
    }

    public Book selectById(long id) {
        log.debug("Getting connection from pool");
        Connection con = ConnectionManager.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;

        Book book = new Book();

        try {

            st = con.createStatement();
            log.info("Executing query for database to get book with id=" + id);
            rs = st.executeQuery(
                "SELECT" +
                    "`books_id`, `books_title`, `books_author`, `books_rating`, " +
                    "`books_comment`, `books_start_date`, `books_end_date`, " +
                    "`books_picture_url`, `books_add_date`" +
                "FROM " +
                    "`books`" +
                "WHERE" +
                    "`books_id`=" + id
            );

            if(rs.next()) {
                book.setId(rs.getLong(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setRating(rs.getLong(4));
                book.setComment(rs.getString(5));
                book.setStartDate(rs.getDate(6));
                book.setEndDate(rs.getDate(7));
                book.setPictureUrl(rs.getString(8));
                book.setAddDate(rs.getDate(9));
            } else {
                log.info("No book with such id, returning empty book");
            }

        } catch (SQLException e1) {
            log.warn("Error while getting data from db", e1);
        } catch (Exception e2) {
            log.warn("Unknown error while getting data from DB", e2);
        } finally {
            log.debug("Closing resources of connection...");
            try {
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(con != null) con.close();
            } catch (SQLException e3) {
                log.warn("Some resources have not been closed!", e3);
            }
        }

        return book;
    }

    public long add(Book book) {
        log.debug("Getting connection from pool");
        Connection con = ConnectionManager.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;

        long result = 0;

        // TODO: There is too many code for SQL generation. Extract it to SqlQueryFactory (Is it Factory?)
        Date startDate = book.getStartDate();
        Date endDate = book.getEndDate();
        String pictureUrl = book.getPictureUrl();
        String comment = book.getComment();
        String qryStartDate = (startDate != null) ? ("'" + sqlDate.format(startDate) + "'") : "null";
        String qryEndDate = (endDate != null) ? ("'" + sqlDate.format(endDate) + "'") : "null";
        String qryPictureUrl = (pictureUrl != null) ? ("'" + pictureUrl + "'") : "null";
        String qryComment = (comment != null) ? ("'" + pictureUrl + "'") : "null";
        String sql =
            "INSERT INTO `books` " +
                "(`books_title`, `books_author`, " +
                "`books_rating`, `books_comment`, `books_start_date`, `books_end_date`, " +
                "`books_picture_url`, `books_add_date`) " +
            "VALUES " +
                " ('" + book.getTitle() + "'," +
                " '" + book.getAuthor() + "'," +
                " " + book.getRating() + "," +
                " '" + qryComment + "'," +
                " " + qryStartDate + "," +
                " " + qryEndDate + "," +
                " " + qryPictureUrl + "," +
                " '" + sqlDateTime.format(book.getAddDate()) + "');";

        try {

            st = con.createStatement();
            log.info("Executing query for DB to add new book");
            st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            rs = st.getGeneratedKeys();
            if(rs.next()) {
                result = rs.getInt(1);
            } else {
                result = 0;
            }

        } catch (SQLException e1) {
            log.warn("Error while executing SQL-query, no data have been inserted", e1);
            log.debug("Sql with error: " + sql);
            result = 0;
        } catch (Exception e2) {
            log.warn("Unknown error while getting data from DB", e2);
            result = 0;
        } finally {
            log.debug("Closing resources of connection...");
            try {
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(con != null) con.close();
            } catch (SQLException e3) {
                log.warn("Some resources have not been closed!", e3);
            }
        }

        return result;
    }

    public void delete(Book book) {
        log.debug("Getting connection from pool");
        Connection con = ConnectionManager.getInstance().getConnection();
        Statement st = null;

        try {

            st = con.createStatement();
            log.info("Executing query for database to delete book with id=" + book.getId());
            st.execute("DELETE FROM `books` WHERE `id`=" + book.getId());

        } catch (SQLException e1) {
            log.warn("Error while executing SQL-query, no data have been deleted", e1);
        } catch (Exception e2) {
            log.warn("Unknown error while getting data from DB", e2);
        } finally {
            log.debug("Closing resources of connection...");
            try {
                if(st != null) st.close();
                if(con != null) con.close();
            } catch (SQLException e3) {
                log.warn("Some resources have not been closed!", e3);
            }
        }
    }

}
