package org.example.assignment1;
//Import Statement
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.0
 * @since       1.0
 */

public class PlayerStatApplication extends Application {
    public static void main(String[] args) { launch(args); }
    /**
     * Method to start the application
     */
    @Override public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(PlayerStatApplication.class.getResource("player-stat.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //add Bootstrap Stylesheet to the scene
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Manchester United 23/24 Player Statistics");
        stage.setScene(scene);
        stage.show();
    }

}