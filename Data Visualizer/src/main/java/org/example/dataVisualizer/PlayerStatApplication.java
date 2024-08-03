package org.example.dataVisualizer;
//Import Statement
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.List;

import static org.example.dataVisualizer.ApiClient.fetchPlayersID;
import static org.example.dataVisualizer.ApiClient.getPlayerList;
import static org.example.dataVisualizer.ViewController.startView;

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.1
 * @since       1.0
 */

// Version 1.1 Update to fetch data from API
public class PlayerStatApplication extends Application {

    // Create an observable list to store the API calls
    static ObservableList<Player> playerList;
    public static void main(String[] args) { launch(args); }

     /**
     * Method to start the application
     */

     @Override
    public void start(Stage stage) throws Exception {
         // Pre-Load Data from API upon application start
        playerList = loadPlayerDataFromAPI();
        // Start the view as stat
        startView(stage, "player-stat.fxml");
    }

    /**
     * Method to load player data from API
     * @return an observable list of players
     */
    private ObservableList<Player> loadPlayerDataFromAPI() {
        List<Integer> idList = fetchPlayersID();
        assert idList != null;
        List<Player> playerList = getPlayerList(idList);
        return FXCollections.observableArrayList(playerList);
    }
}