package org.example.dataVisualizer;
//import Statement
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import static org.example.dataVisualizer.ViewController.switchView;

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
        switchView(event, "player-stat.fxml");
    }
}