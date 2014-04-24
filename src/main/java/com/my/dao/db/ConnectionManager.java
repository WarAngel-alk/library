package com.my.dao.db;

import com.my.util.LogUtil;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 09.03.14
 * Time: 16:29
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionManager {

    private static final Logger log = Logger.getLogger(LogUtil.getCurrentClass());

    private DataSource dataSource;

    private static ConnectionManager instance;

    public synchronized static ConnectionManager getInstance() {
        if(instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    private ConnectionManager() {
        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:comp/env");
            dataSource = (DataSource) envCtx.lookup("jdbc/LibraryDS");
        } catch (NamingException e2) {
            log.error("Error while loading data source (JNDI)", e2);
            throw new RuntimeException("Error while loading data source (JNDI)");
        }
    }

    public Connection getConnection() {
        try {
            log.debug("Getting new connection to database... ");
            return dataSource.getConnection();
        } catch (SQLException e1) {
            log.error("Can not get connection to database", e1);
            throw new RuntimeException("Can not get connection to database");
        }
    }

}
