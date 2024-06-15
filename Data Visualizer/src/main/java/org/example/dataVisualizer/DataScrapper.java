package org.example.dataVisualizer;
// import Statement
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//For DB Connection
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.0
 * @since       1.0
 */


public class DataScrapper {
    public static void main(String[] args) throws IOException, InterruptedException {
        getPlayerData();
    }

    /**
     * Scrap Data from Team Page in Fotmob to get the player ID and Name
     * Use the Player and Name to get the full data in player profile
     */
    public static void getPlayerData() throws IOException, InterruptedException {
            try {
                //Create New Client and Request to access player ID and NAme in the Manchester United Team
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest basicRequest = HttpRequest.newBuilder()
                        .uri(URI.create("https://www.fotmob.com/api/leagueseasondeepstats?id=47&season=20720&type=players&stat=goals&teamId=10260&slug=manchester-united"))
                        .GET()
                        .build();
                HttpResponse<String> idResponse = client.send(basicRequest, HttpResponse.BodyHandlers.ofString());
                //Scrap Data from Team Page in Fotmob to get the player ID and Name
                parsePlayerData(idResponse.body());
            }
            catch (Exception e) {
                e.printStackTrace();
            }

    }

    public static void parsePlayerData(String responseBody) throws IOException, InterruptedException {
        //parse the response as a JSON Object
        JSONObject JsonObject = new JSONObject(responseBody);
        JSONArray statsData = JsonObject.getJSONArray("statsData");
        // Loop through each player object in the array
        for (int i = 0; i < statsData.length(); i++) {
            JSONObject player = statsData.getJSONObject(i);
            // Extract player ID
            int id = player.getInt("id");
            // Extract player name
            String name = player.getString("name");
            //Use the Player and Name to get the full data in player profile
            String requestURI = String.format("https://www.fotmob.com/api/playerData?id=%d",id);

            //Create Local Variable to store the player's attributes
            int goal = 0;
            int assist = 0;
            int started = 0;
            int playing_minutes =0;
            int yellow_card =0;
            int red_card =0;
            double rating =0;

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest detailRequest = HttpRequest.newBuilder().uri(URI.create(requestURI)).GET().build();
                HttpResponse<String> detailResponse = client.send(detailRequest, HttpResponse.BodyHandlers.ofString());
                //Get the stats array from the detail response
                JSONObject mainLeagueMap = new JSONObject(detailResponse.body()).getJSONObject("mainLeague");
                JSONArray statsArray = mainLeagueMap.getJSONArray("stats");

                for (int j = 0; j < statsArray.length(); j++) {
                    JSONObject stat = statsArray.getJSONObject(j);
                    switch (stat.getString("title")){
                        case "Goals":
                            goal = stat.getInt("value");
                            break;
                        case "Assists":
                            assist = stat.getInt("value");
                            break;
                        case "Started":
                            started = stat.getInt("value");
                            break;
                        case "Minutes played":
                            playing_minutes = stat.getInt("value");
                            break;
                        case "Yellow cards":
                            yellow_card = stat.getInt("value");
                            break;
                        case "Red cards":
                            red_card = stat.getInt("value");
                            break;
                        case "Rating":
                            rating = stat.getDouble("value");
                            break;
                    }
                }


            }
            catch (Exception e) {
                e.printStackTrace();
            }
            insertPlayerData(id, name, goal, assist, started, playing_minutes, yellow_card, red_card, rating);
        }


    }



    /**
     * To Insert Player Data to DB, from the scrapped Data
     * @param id Fotmob ID of the Player
     * @param name Name of the player
     * @param goal the no. of goals the player scored in 2023/24 Season
     * @param started no. of games the player started in 2023/24 Season
     * @param playing_minutes no. of games the player started in 2023/24 Season
     * @param yellow_card no. of yellow card the player received in 2023/24 Season
     * @param red_card no. of red card the player received in 2023/24 Season
     * @param assist the no. of assist of the player assisted in 2023/24 Season
     * @param rating the average rating of the player in 2023/24 Season
     */
    public static void insertPlayerData(int id, String name, int goal, int assist, int started, int playing_minutes, int yellow_card, int red_card, double rating){
        //Connect to DB
        DatabaseConnector dbConnector = new DatabaseConnector();
        //Insert ID and Name to DB
        try (Connection connection = dbConnector.connect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT IGNORE INTO playerStat (player_id, player_name,goal,assist,started,playing_minutes,yellow_card, red_card, rating) VALUES (" + id
                    + ", '" + name + "', " + goal + ", " + assist + ", " + started + ", "+ playing_minutes + ", " + yellow_card + ", "+ red_card + ", "+ rating + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}