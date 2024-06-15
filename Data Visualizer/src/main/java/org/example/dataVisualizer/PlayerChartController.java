package org.example.dataVisualizer;
//import Statement

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

public class PlayerChartController {
    /**
     * Initialize the Chart and the column variables.
     */
    @FXML
    private BarChart<String,Number> goalLeaderboard;
    /**
     * X-axis of the barchart, showing the goals
     */
    @FXML
    private NumberAxis goalAxis;
    /**
     * Y-axis of the barchart, showing the players
     */
    @FXML
    private CategoryAxis playerAxis;

    /**
     * Create a XYChart Series to store the player name and goals
     */
    private XYChart.Series<String,Number> playerSeries = new XYChart.Series<String,Number>();

    /**
     * Initialize the Chart and link with the playerSeries
     */
    @FXML
    private void initialize(){
        showPlayerStat();
        goalLeaderboard.getData().add(playerSeries);
    }

    /**
     * To retrieve data from DB and display in the TableView
     */
    @FXML
    private void showPlayerStat(){
        DatabaseConnector dbConnector = new DatabaseConnector();
        try (Connection connection = dbConnector.connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT player_name,goal FROM playerStat");
            while (resultSet.next()) {
                //Store the result from SQL query to variables
                String name = resultSet.getString("player_name");
                Integer goal = resultSet.getInt("goal");
                //Store the data in a XY Series
                playerSeries.getData().add(new XYChart.Data<String,Number>(name,goal));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Switch the scene to Table View, by loading the "player-stat.fxml", retrieve the current stage, and switch to the loaded fxml
     *
     * @param event Action Event, fxid:swtichTable button in player-chart
     */
    @FXML
    public void switchToTableView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PlayerStatApplication.class.getResource("player-stat.fxml"));
        //Retrieve the current stage from the Action EVent
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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