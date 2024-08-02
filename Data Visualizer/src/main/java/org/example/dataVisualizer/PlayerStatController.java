package org.example.dataVisualizer;
//import Statement
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import static org.example.dataVisualizer.PlayerStatApplication.playerList;

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.1
 * @since       1.0
 */

// Version 1.1 Update Stat Controller to fetch data directly from API
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
     * Create an ObservableList to store the Player's data, assign it as the value from the API call
     */
    private ObservableList<Player> playerData = FXCollections.observableArrayList(playerList);

    /**
     * Create a variable to store the current selected player
     */
    private static Player selectedPlayer = null;

    /**
     * Initialize the Tableview and link with the playerData list
     */
    @FXML
    private void initialize(){
        // Initialize the table
        nameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("playerName"));
        goalColumn.setCellValueFactory(new PropertyValueFactory<Player,Integer>("goal"));
        assistColumn.setCellValueFactory(new PropertyValueFactory<Player,Integer>("assist"));
        playMinColumn.setCellValueFactory(new PropertyValueFactory<Player,Integer>("playing_minutes"));
        ycColumn.setCellValueFactory(new PropertyValueFactory<Player,Integer>("yellow_card"));
        rcColumn.setCellValueFactory(new PropertyValueFactory<Player,Integer>("red_card"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Player,Double>("rating"));
        startedColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("started"));
        //link the student table with the student data list
        playerRecord.setItems(playerData);
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
        switchingView(event, fxmlLoader);
    }

    // Getter to allow other resource to get the current selected player
    public static Player getSelectedPlayer() {
        return selectedPlayer;
    }

    @FXML
    public void switchToDetailView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PlayerStatApplication.class.getResource("player-detail.fxml"));
        // Store the current selected Player ID, and pass to player detail controller
        selectedPlayer = playerRecord.getSelectionModel().getSelectedItem();
        // If no player selected, prompted error
        if(selectedPlayer == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.getDialogPane().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            a.getDialogPane().getStyleClass().add("alert-warning");
            a.setTitle("No Player Selected");
            a.setContentText("Please select a player first");
            a.getButtonTypes().setAll(ButtonType.OK);
            Button okButton = (Button) a.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setStyle("-fx-border-color:white;-fx-border-width: 5px; -fx-border-radius: 5px"); // Example style


            a.showAndWait();
            return;
        }
        //Retrieve the current stage from the Action EVent
        switchingView(event, fxmlLoader);
    }

    static void switchingView(ActionEvent event, FXMLLoader fxmlLoader) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
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