package edu.metu.lngamesml.stats.sqllite;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 1/25/11
 * Time: 2:47 AM
 * To change this template use File | Settings | File Templates.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caglar
 */
public class DBManager {

    private Connection Conn = null;
    Statement Stat = null;

    public void startDb() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            // create a database connection
            Conn = DriverManager.getConnection("jdbc:sqlite:stats.db");
            Stat = Conn.createStatement();
            Stat.setQueryTimeout(30);  // set timeout to 30 sec.
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }

    public void executeInsertUpdate(String query) throws SQLException {
        if (Stat != null) {
            Stat.executeUpdate(query);
        } else {
            throw new SQLException("Connection is null!");
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        ResultSet rs = null;
        if (Stat != null) {
            rs = Stat.executeQuery(query);
        } else {
            throw new SQLException("Connection is null!");
        }
        return rs;
    }

    public void closeDBConn() {
        try {
            if (Conn != null) {
                Conn.close();
            }
        } catch (SQLException e) {
            // connection close failed.
            Logger.getAnonymousLogger(DBManager.class.getName()).log(Level.WARNING, e.getMessage());
        }
    }

    public boolean isConnClosed() {
        boolean returnVal = true;
        try {
            if (Conn != null) {
                returnVal = Conn.isClosed();
            }
        } catch (SQLException e) {
            // connection close failed.
            Logger.getAnonymousLogger(DBManager.class.getName()).log(Level.WARNING, e.getMessage());
        }
        return returnVal;
    }
}

