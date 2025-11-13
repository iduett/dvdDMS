package dms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Irene Duett, CEN 3024c, 11/12/2025
 * class: DBUtil
 * purpose: Provides a utility method to obtain a connection to a SQLite database.
 *          Simplifies connecting to the database from anywhere in the application.
 */
public class DBUtil {

    /**
     * method: getConnection
     * parameters: String dbPath - full path to SQLite database
     * return: Connection
     * purpose: Connects to the SQLite database at the specified path.
     *          Throws SQLException if the connection fails.
     *
     * @param dbPath Full path to the SQLite database file
     * @return Connection object to the SQLite database
     * @throws SQLException if the connection cannot be established
     */
    public static Connection getConnection(String dbPath) throws SQLException {
        String url = "jdbc:sqlite:" + dbPath;
        return DriverManager.getConnection(url);
    }
}
