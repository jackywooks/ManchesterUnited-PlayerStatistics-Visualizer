package org.example.dataVisualizer;
//import Statement
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;

import static org.example.dataVisualizer.PlayerStatController.switchingView;

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.0
 * @since       1.0
 */

public class PlayerDetailController {

    /**
     * Initialize the Tableview and link with the playerData list
     */
    @FXML
    private void initialize(){
        Player currentPLayer = PlayerStatController.getSelectedPlayer();
    }


    @FXML
    public void switchToTableView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PlayerStatApplication.class.getResource("player-stat.fxml"));
        //Retrieve the current stage from the Action EVent
        switchingView(event, fxmlLoader);
    }
}