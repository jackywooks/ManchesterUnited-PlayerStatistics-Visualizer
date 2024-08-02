package org.example.dataVisualizer;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.1
 * @since       1.1
 */

public class PlayerDeserializer implements JsonDeserializer<Player> {
    /**
     * Customise the player deserializer to fetch data
     * @param json JSON fetched from API
     * @return Customised Player Object
     */
    @Override
    public Player deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Create a JSON object to store the json element
        JsonObject playerObject = json.getAsJsonObject();

        // Create a new player object to store the data from JSON
        Player player = new Player();

        // Get id and name directly from the root of the JSON object
        player.setPlayerID(playerObject.get("id").getAsInt()) ;
        player.setPlayerName(playerObject.get("name").getAsString());

        // Traverse the JSON object to the stat Array
        JsonObject mainLeagueObject = playerObject.getAsJsonObject("mainLeague");
        JsonArray statsArray = mainLeagueObject.getAsJsonArray("stats");

        // As the stat is an array, loop through the array to get the statistics data
        for (int i = 0; i < statsArray.size(); i++) {
            JsonObject stats = statsArray.get(i).getAsJsonObject();
            setPlayerStat(stats, player);
        }
        return player;
    }

    private static void setPlayerStat(JsonObject stats, Player player) {
        JsonElement statValue = stats.get("value");
        // Fetch data by the title in the JSON, to get the value of the related stat
        try {
            switch (stats.get("title").getAsString()){
                case "Goals":
                    player.setGoal(statValue.getAsInt());
                    break;
                case "Assists":
                    player.setAssist(statValue.getAsInt());
                    break;
                case "Started":
                    player.setStarted(statValue.getAsInt());
                    break;
                case "Minutes played":
                    player.setPlayingMinutes(statValue.getAsInt());
                    break;
                case "Yellow cards":
                    player.setYellowCard(statValue.getAsInt());
                    break;
                case "Red cards":
                    player.setRedCard(statValue.getAsInt());
                    break;
                case "Rating":
                    player.setRating(statValue.getAsDouble());
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}