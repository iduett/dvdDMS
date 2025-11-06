package dms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * class: DBUtil
 * purpose: Provides a connection to the SQLite database
 */
public class DBUtil {

    /**
     * method: getConnection
     * parameters: String dbPath - full path to SQLite database
     * return: Connection
     * purpose: Connects to the SQLite database, throws SQLException if fails
     */
    public static Connection getConnection(String dbPath) throws SQLException {
        String url = "jdbc:sqlite:" + dbPath;
        return DriverManager.getConnection(url);
    }
}
