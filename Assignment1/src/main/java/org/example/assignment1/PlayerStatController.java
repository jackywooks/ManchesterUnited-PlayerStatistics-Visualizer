package org.example.assignment1;
//import Statement
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.0
 * @since       1.0
 */

public class PlayerStatController {
    /**
     * Initialize the Tableview and the column variables.
     */
    @FXML
    private TableView<Player> playerRecord;

    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TableColumn<Player, Integer> goalColumn;

    @FXML
    private TableColumn<Player, Integer> assistColumn;

    @FXML
    private TableColumn<Player, Player.Position> positionColumn;

    @FXML
    private TableColumn<Player, Double> ratingColumn;

    /**
     * Create an ObservableList to store the Player's data
     */
    private ObservableList<Player> playerData = FXCollections.observableArrayList();

    /**
     * Initialize the Tableview and link with the playerData list
     */
    @FXML
    private void initialize(){
        // Initialize the table
        nameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        goalColumn.setCellValueFactory(new PropertyValueFactory<Player,Integer>("goal"));
        assistColumn.setCellValueFactory(new PropertyValueFactory<Player,Integer>("assist"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Player,Double>("rating"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<Player, Player.Position>("position"));
//      positionColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPosition().toString()));
        //link the student table with the student data list
        playerRecord.setItems(playerData);
        showPlayerStat();
    }

    /**
     * To retrieve data from DB and display in the TableView
     */
    @FXML
    private void showPlayerStat(){
        DatabaseConnector dbConnector = new DatabaseConnector();
        try (Connection connection = dbConnector.connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM playerStat");
            while (resultSet.next()) {
                //Store the result from SQL query to variables
                String playerName = resultSet.getString("player_name");
                Player.Position position;
                try {
                    position = Player.Position.valueOf(resultSet.getString("player_position"));
                }catch (IllegalArgumentException e) {
                    // Handle invalid position string (e.g., log error or set default)
                    System.err.println("Invalid position value: " + resultSet.getString("player_position"));
                    position = Player.Position.MF; // Set a default position
                }
                int goal = resultSet.getInt("goal");
                int assist = resultSet.getInt("assist");
                double rating = resultSet.getDouble("rating");
                //Create a player instance to store the players' info.
                Player newPlayer = new Player(playerName, position, goal, assist, rating);
                //Add every player in the playerData List
                playerData.add(newPlayer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}