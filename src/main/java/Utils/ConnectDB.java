package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for establishing a connection to the MySQL database.
 */
public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/javaavanceeresto ";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    /**
     * Returns a connection to the MySQL database using the configured URL, username, and password.
     *
     * @return a {@link Connection} object to interact with the database
     * @throws SQLException if the connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
