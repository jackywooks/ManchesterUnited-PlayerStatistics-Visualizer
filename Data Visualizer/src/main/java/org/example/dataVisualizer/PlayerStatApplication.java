package org.example.dataVisualizer;
//Import Statement
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.List;

import static org.example.dataVisualizer.ApiClient.fetchPlayersID;
import static org.example.dataVisualizer.ApiClient.getPlayerList;

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

         // Load Data from API
        playerList = loadPlayerDataFromAPI();
        FXMLLoader fxmlLoader = new FXMLLoader(PlayerStatApplication.class.getResource("player-stat.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //add Bootstrap Stylesheet to the scene
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Manchester United 23/24 Player Statistics");
        //Apply the icon to the taskbar
        stage.getIcons().add(new Image("file:src/main/resources/org/example/dataVisualizer/images/icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to load player data from API
     * @return an observable list of players
     */
    private ObservableList<Player> loadPlayerDataFromAPI() {
        List<Integer> idList = fetchPlayersID();
        assert idList != null;
        List<Player> playerList = getPlayerList(idList);
        return FXCollections.observableArrayList( playerList);
    }
}