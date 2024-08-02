package org.example.dataVisualizer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class ApiClient {
    public static void main(String[] args) throws IOException {
        List<Integer> idList = fetchPlayersID();
        List<Player> playerList = getPlayerList(idList);

        savePlayerListToJSON(playerList);

        List<Player> playerJSONList = getPlayerJSONData();

        for(Player player : playerJSONList) {
            System.out.println(player.toString());
        }
    }

    private static List<Player> getPlayerJSONData () throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/playerJSON/players.JSON"))) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Player>>(){}.getType();
            return gson.fromJson(bufferedReader,listType);
        }
    }

    // Save the player object list to JSON using GSON
    private static void savePlayerListToJSON(List<Player> playerList) throws IOException {
        // Saving the data in JSON file
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (FileWriter fileWriter = new FileWriter("src/main/resources/playerJSON/players.JSON"))
        {
            gson.toJson(playerList, fileWriter);
        }
    }

    // Create an async list to player from the id list
    private static List<Player> getPlayerList(List<Integer> idList) {
        List<CompletableFuture<Player>> futures = idList.stream().
                map(id -> fetchPlayerData(String.format("https://www.fotmob.com/api/playerData?id=%d",id))).toList();

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allFutures.join();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    // Fetch playerID from APIs
    private static List<Integer> fetchPlayersID(){
        try {
            //Create New Client and Request to access player ID and NAme in the Manchester United Team
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest basicRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.fotmob.com/api/leagueseasondeepstats?id=47&season=20720&type=players&stat=goals&teamId=10260&slug=manchester-united"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(basicRequest, HttpResponse.BodyHandlers.ofString());
            //Scrap Data from Team Page in Fotmob to get the player ID
            String jsonResponse = response.body();
            return getPlayerIDs(jsonResponse);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // Get the Player ID from the API response
    private static List<Integer> getPlayerIDs(String jsonResponse){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

        JsonArray teamStats = jsonObject.getAsJsonArray("statsData");
        List<Integer> playerIDs = new ArrayList<>();

        for(JsonElement player : teamStats){
            playerIDs.add(player.getAsJsonObject().get("id").getAsInt());
        }

        return playerIDs;
    }

    // Async function to get the playerData by id
    private static CompletableFuture<Player> fetchPlayerData(String API_URL){
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL))
                        .build();
                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    String jsonResponse = response.body();
                    return parsePlayerJsonResponse(jsonResponse);
                } else {
                    System.out.println("Error: " + response.statusCode());
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            return null;
        });
    }

    // Parse the Player Response as a Player Object
    private static Player parsePlayerJsonResponse(String jsonResponse) {
        Gson gson = createGson();
        return gson.fromJson(jsonResponse, Player.class);
    }

    // Using a customised Deserializer to parse JSON from API
    private static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Player.class, new PlayerDeserializer())
                .create();
    }
}