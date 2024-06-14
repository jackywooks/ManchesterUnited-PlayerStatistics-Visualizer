package org.example.assignment1;

// import statements
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.0
 * @since       1.0
 */

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3307/stat";
    private static final String USER = "root";
    private static final String PASS = "";
    /**
     * Constructor to create an instance to connect to the MySQL database
     */
    public Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }




}
