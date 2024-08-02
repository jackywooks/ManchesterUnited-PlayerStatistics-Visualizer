package org.example.dataVisualizer;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PlayerDeserializer implements JsonDeserializer<Player> {
    @Override
    public Player deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject playerObject = json.getAsJsonObject();

        Player player = new Player();
        player.setPlayerID(playerObject.get("id").getAsInt()) ;
        player.setPlayerName(playerObject.get("name").getAsString());

        JsonObject mainLeagueObject = playerObject.getAsJsonObject("mainLeague");
        JsonArray statsArray = mainLeagueObject.getAsJsonArray("stats");

        for (int i = 0; i < statsArray.size(); i++) {
            JsonObject stats = statsArray.get(i).getAsJsonObject();
            setPlayerStat(stats, player);
        }
        return player;
    }

    private static void setPlayerStat(JsonObject stats, Player player) {
        JsonElement statValue = stats.get("value");
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