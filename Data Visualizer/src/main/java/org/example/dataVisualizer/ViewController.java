package org.example.dataVisualizer;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

// View Controller Class
public class ViewController {

    /**
     * Method to swtich view
     * @param event the action event
     * @param resourceName the target FXML
     */
    static void switchView(ActionEvent event, String resourceName) throws IOException {
        //Retrieve the current stage from the Action EVent
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        startView(stage, resourceName);
    }

    /**
     * Method to start a view
     * @param stage the current stage
     * @param resourceName the target FXML
     */

    static void startView(Stage stage, String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewController.class.getResource(resourceName));
        Scene scene = new Scene(fxmlLoader.load());
        //add Bootstrap Stylesheet to the scene
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        //add Customised Stylesheet to the scene
        scene.getStylesheets().add(ViewController.class.getResource("/Style/styles.css").toExternalForm());
        stage.setTitle("Manchester United 23/24 Player Statistics");
        //Apply the icon to the taskbar
        stage.getIcons().add(new Image("file:src/main/resources/org/example/dataVisualizer/images/icon.png"));
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Prompt Error Alert as per User Defined info
     * @param alert The alert created
     * @param alertTitle title of the alert
     * @param alertMessage message of the alert
     */

    static void promptErrorAlert(Alert alert, String alertTitle, String alertMessage) {
        // Apply customised CSS to the alert
        alert.getDialogPane().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        alert.getDialogPane().getStylesheets().add(ViewController.class.getResource("/Style/styles.css").toExternalForm());
        // Set the button attributes
        alert.setTitle(alertTitle);
        alert.setContentText(alertMessage);
        alert.getButtonTypes().setAll(ButtonType.CLOSE);
        // Style the dialog as Boostrap
        alert.getDialogPane().getStyleClass().add("alert-warning");
        // Get the button from the Dialog
        Button button = (Button) alert.getDialogPane().lookupButton(ButtonType.CLOSE);
        // Style the button as class in external CSS
        button.getStyleClass().add("alert-dismiss");
        alert.showAndWait();
    }
}
