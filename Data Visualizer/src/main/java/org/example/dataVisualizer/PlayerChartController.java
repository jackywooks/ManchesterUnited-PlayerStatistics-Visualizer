package org.example.dataVisualizer;
//import Statement

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.0
 * @since       1.0
 */

public class PlayerChartController {
    @FXML
    private Label title;
    /**
     * Initialize the Bar Chart and the column variables.
     * Create a XYChart Series to store the player name and goals
     */
    @FXML
    private BarChart<String,Number> goalLeaderboard;
    private XYChart.Series<String,Number> playerGoalSeries = new XYChart.Series<String,Number>();

    /**
     * Initialize the Pie Chart.
     * Create an Observable List to store the player name and ratings
     */
    @FXML
    private PieChart ratingBoard;
    private ObservableList<PieChart.Data> ratingData = FXCollections.observableArrayList();

    /**
     * Initialize the scene with goal leader board
     */
    @FXML
    private void initialize(){
        fetchGoalLeaderboard();
        fetchRatingChart();
    }

    /**
     * To retrieve data from DB and display in the Bar Chart
     */
    @FXML
    private void fetchGoalLeaderboard(){
        DatabaseConnector dbConnector = new DatabaseConnector();
        try (Connection connection = dbConnector.connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT player_name,goal FROM playerStat");
            while (resultSet.next()) {
                //Store the result from SQL query to variables
                String name = resultSet.getString("player_name");
                Integer goal = resultSet.getInt("goal");
                //Store the data in a XY Series
                playerGoalSeries.setName("Goals");
                playerGoalSeries.getData().add(new XYChart.Data<String,Number>(name,goal));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Sort the data by Goals
        playerGoalSeries.getData().sort(Comparator.comparingInt(d->d.getYValue().intValue()));
        //put XY series data in the goalLeaderboard bar chart
        goalLeaderboard.getData().add(playerGoalSeries);
        goalLeaderboard.setStyle("CHART_COLOR_1: #ff0000;");
    }
    /**
     * To retrieve data from DB and display in the Pie Chart
     */
    @FXML
    private void fetchRatingChart(){
        DatabaseConnector dbConnector = new DatabaseConnector();
        try (Connection connection = dbConnector.connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("" +
                    "SELECT Category, COUNT(*) AS PlayerCount\n" +
                    "FROM (\n" +
                    "  SELECT \n" +
                    "    CASE WHEN Rating > 7.5 THEN 'Elite - Greater Than 7.5'\n" +
                    "         WHEN Rating > 6.75 THEN 'Nice - Greater Than 6.75'\n" +
                    "         ELSE 'Mediocre - 6.75 or Lower'\n" +
                    "    END AS Category\n" +
                    "  FROM playerStat\n" +
                    ") AS categorized_players\n" +
                    "GROUP BY Category;");
            while (resultSet.next()) {
                //Store the result from SQL query to variables
                String ratingGrade = resultSet.getString("Category");
                Double playerCount = resultSet.getDouble("PlayerCount");
                //Store the data in a XY Series
                ratingData.add(new PieChart.Data(ratingGrade,playerCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ratingBoard.setData(ratingData);
        //Default Hiding the rating board
        ratingBoard.setVisible(false);
    }
    /**
     * To retrieve data from DB and display in the Pie Chart
     */
    @FXML
    private void changeToRatingBoard(){
        title.setText("Player's Rating Board");
        goalLeaderboard.setVisible(false);
        ratingBoard.setVisible(true);
    }

    @FXML
    private void changeToGoalBoard(){
        title.setText("Goal Scorer Board");
        goalLeaderboard.setVisible(true);
        ratingBoard.setVisible(false);
    }

    /**
     * Switch the scene to Table View, by loading the "player-stat.fxml", retrieve the current stage, and switch to the loaded fxml
     *
     * @param event Action Event, fxid:switchTable button in player-chart
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