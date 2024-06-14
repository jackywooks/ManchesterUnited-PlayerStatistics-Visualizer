package org.example.assignment1;

// import statements
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.0
 * @since       1.0
 */

public class DatabaseUsage {
    /**
     * Database Tester
     */
    public static void main(String[] args) {
        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection connection = dbConnector.connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM playerStat");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("player_id") + " " + resultSet.getString("player_name") + " " +
                        resultSet.getInt("goal")+ " " +
                        resultSet.getInt("assist")+ " " +
                        resultSet.getDouble("rating"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


