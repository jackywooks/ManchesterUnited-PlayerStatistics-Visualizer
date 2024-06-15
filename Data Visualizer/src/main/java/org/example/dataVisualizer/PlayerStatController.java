package org.example.dataVisualizer;
//import Statement
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
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
    private TableColumn<Player, Integer> startedColumn;

    @FXML
    private TableColumn<Player, Integer> playMinColumn;

    @FXML
    private TableColumn<Player, Integer> ycColumn;

    @FXML
    private TableColumn<Player, Integer> rcColumn;

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
        playMinColumn.setCellValueFactory(new PropertyValueFactory<Player,Integer>("playing_minutes"));
        ycColumn.setCellValueFactory(new PropertyValueFactory<Player,Integer>("yellow_card"));
        rcColumn.setCellValueFactory(new PropertyValueFactory<Player,Integer>("red_card"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Player,Double>("rating"));
        startedColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("started"));
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
                int goal = resultSet.getInt("goal");
                int assist = resultSet.getInt("assist");
                int started = resultSet.getInt("started");
                int playing_minutes = resultSet.getInt("playing_minutes");
                int yellow_card = resultSet.getInt("yellow_card");
                int red_card = resultSet.getInt("red_card");
                double rating = resultSet.getDouble("rating");
                //Create a player instance to store the players' info.
                Player newPlayer = new Player(playerName, goal, assist,started, playing_minutes,yellow_card,red_card, rating);
                //Add every player in the playerData List
                playerData.add(newPlayer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch the scene to Chart View, by loading the "player-chart.fxml", retrieve the current stage, and switch to the loaded fxml
     *
     * @param event Action Event, fxid:switchChart button in player-stat
     */
    @FXML
    public void switchToChartView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PlayerStatApplication.class.getResource("player-chart.fxml"));
       //Retrieve the current stage from the Action EVent
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        //add Bootstrap Stylesheet to the scene
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Manchester United 23/24 Player Statistics");
        //Apply the icon to the taskbar
        stage.getIcons().add(new Image("file:src/main/resources/org/example/dataVisualizer/images/icon.png"));
        stage.setScene(scene);
        stage.show();
    }
}