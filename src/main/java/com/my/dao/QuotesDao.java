package com.my.dao;

import com.my.bussiness.beans.Book;
import com.my.bussiness.beans.Quote;
import com.my.dao.db.ConnectionManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.my.util.LogUtil.getCurrentClass;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 26.02.14
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
public class QuotesDao {

    // TODO: Make possible adding quotes without linking with book

    private static final Logger log = Logger.getLogger(getCurrentClass());

    public List<Quote> selectAll() {
        log.debug("Getting connection from pool");
        Connection con = ConnectionManager.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;

        String sql =
            "SELECT " +
                "`quotes_id`, `quotes_book_id`, `quotes_text`, `quotes_add_datetime`, " +
                "`books_title`, `books_author` " +
            "FROM " +
                "`quotes` " +
                "JOIN `books` ON `quotes`.`quotes_book_id`=`books`.`books_id`";

        List<Quote> resultList = new ArrayList<Quote>();

        try {

            st = con.createStatement();
            log.debug("Executing query to DB to select all quotes");
            rs = st.executeQuery(sql);

            log.debug("Creation list with all quotes");
            while (rs.next()) {
                Quote quote = new Quote();

                quote.setId(rs.getLong(1));
                quote.setBookId(rs.getLong(2));
                quote.setText(rs.getString(3));
                quote.setAddDate(rs.getDate(4));
                quote.setBookTitle(rs.getString(5));
                quote.setBookAuthor(rs.getString(6));

                resultList.add(quote);
            }

        } catch (SQLException e1) {
            log.warn("Error while getting data from DB. SQL: " + sql, e1);
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

    public Quote selectById(int id) {
        log.debug("Getting connection from pool");
        Connection con = ConnectionManager.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;

        Quote quote = new Quote();

        try {

            st = con.createStatement();
            log.info("Executing query for database to get quote with id=" + id);
            rs = st.executeQuery(
                "SELECT " +
                    "`quotes_id`, `quotes_book_id`, `quotes_text`, `quotes_add_datetime`," +
                    "`books_title`, `books_author` " +
                "FROM `quotes` " +
                    "JOIN `books` ON `quotes_book_id`=`books_id` " +
                "WHERE " +
                    "`quotes_id`=" + id
            );

            if(rs.next()) {

                quote.setId(rs.getLong(1));
                quote.setBookId(rs.getLong(2));
                quote.setText(rs.getString(3));
                quote.setAddDate(rs.getDate(4));
                quote.setBookTitle(rs.getString(5));
                quote.setBookAuthor(rs.getString(6));

            } else {
                log.info("No user with such id, returning empty user");
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

        return quote;
    }

    public long add(Quote q) {
        log.debug("Getting connection from pool");
        Connection con = ConnectionManager.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;

        long result = 0;

        try {

            st = con.createStatement();
            log.info("Executing query for database to get add query");
            st.executeUpdate(
                    "INSERT INTO" +
                        "`quotes`" +
                        "(`quotes_book_id`, `quotes_text`, `quotes_add_datetime`)" +
                    "VALUES" +
                        "('" + q.getBookId() + "', '" + q.getText() + "', '" + q.getAddDate() + "')",
                    Statement.RETURN_GENERATED_KEYS);

            rs = st.getGeneratedKeys();
            if(rs.next()) {
                result = rs.getInt(1);
            } else {
                result = 0;
            }

        } catch (SQLException e1) {
            log.warn("Error while executing SQL-query, no data have been inserted", e1);
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

    public void delete(Quote q) {
        log.debug("Getting connection from pool");
        Connection con = ConnectionManager.getInstance().getConnection();
        Statement st = null;

        try {

            st = con.createStatement();
            log.info("Executing query for database to get delete user");
            st.execute("DELETE FROM `quotes` WHERE `quotes_id`=" + q.getId());

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

    public List<Quote> selectByBook(Book book) {
        log.debug("Getting connection from pool");
        Connection con = ConnectionManager.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;

        List<Quote> resultList = new ArrayList<Quote>();

        try {

            st = con.createStatement();
            log.info("Executing query to DB");
            rs = st.executeQuery(
                "SELECT" +
                    "`quotes_id`, `quotes_book_id`, `quotes_text`, `quotes_add_datetime`, " +
                    "`books_title`, `books_author`" +
                "FROM" +
                    "`quotes`" +
                    "JOIN `books` ON `books_id`=`quotes_book_id`" +
                "WHERE `books_id`=1"
            );

            log.debug("Creation list with quotes from book");
            while (rs.next()) {
                Quote quote = new Quote();

                quote.setId(rs.getLong(1));
                quote.setBookId(rs.getLong(2));
                quote.setText(rs.getString(3));
                quote.setAddDate(rs.getDate(4));
                quote.setBookTitle(rs.getString(5));
                quote.setBookAuthor(rs.getString(6));

                resultList.add(quote);
            }

        } catch (SQLException e1) {
            log.warn("Error while getting data from DB", e1);
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

}
