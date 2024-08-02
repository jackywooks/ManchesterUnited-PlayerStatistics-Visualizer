package org.example.dataVisualizer;

//import Statement
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.dataVisualizer.PlayerStatApplication.playerList;

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.1
 * @since       1.0
 */

// Version 1.1 Update to fetch data from API
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
        // Set the series name as Goals
        playerGoalSeries.setName("Goals");

        // Set the data in the XY Goal Series by data in the API fetched data list
        playerList.forEach(player ->
                playerGoalSeries.getData().add(new XYChart.Data<String,Number>(player.getPlayerName(),player.getGoal())));
        //Sort the data by Goals descending order
        playerGoalSeries.getData().sort(Comparator.<XYChart.Data<String, Number>>comparingInt(data->data.getYValue().intValue()).reversed());
        //put XY series data in the goalLeaderboard bar chart
        goalLeaderboard.getData().add(playerGoalSeries);
        goalLeaderboard.setStyle("CHART_COLOR_1: #ff0000;");
    }
    /**
     * To retrieve data from DB and display in the Pie Chart
     */
    @FXML
    private void fetchRatingChart(){
        // Store the count of the rating grade in a Hash Map
        Map<String, Long> ratingGradeCounts = playerList.stream()
                .collect(Collectors.groupingBy(player -> ratingGrade(player.getRating()), Collectors.counting()));

        // Loop through the Hashmap, and add data in the rating data list
        ratingGradeCounts.forEach((grade, count) -> {
            ratingData.add(new PieChart.Data(grade, count));
        });

        ratingBoard.setData(ratingData);
        //Default Hiding the rating board
        ratingBoard.setVisible(false);
        ratingBoard.setStyle("-fx-text-base-color: white");
    }
    /**
     * Change display charts
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
        PlayerDetailController.switchingView(event, fxmlLoader);
    }

    /**
     * Get the rating and return the grade description, Helper function
     * @param rating player Rating
     * @return rating Grade in String
     */
    private static String ratingGrade(double rating){
        if (rating > 7.5){
            return "Elite - Greater Than 7.5";
        }
        if (rating > 6.75){
            return "Nice - Greater Than 6.75";
        }
        return "Mediocre - 6.75 or Lower";
    }
}