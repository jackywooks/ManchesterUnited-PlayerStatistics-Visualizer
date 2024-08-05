package org.example.dataVisualizer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
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

    public static List<Player> getPlayerJSONData () throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/playerJSON/players.JSON"))) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Player>>(){}.getType();
            return gson.fromJson(bufferedReader,listType);
        }
    }

    // Save the player object list to JSON using GSON
    public static void savePlayerListToJSON(List<Player> playerList) throws IOException {
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
    public static List<Player> getPlayerList(List<Integer> idList) {
        List<CompletableFuture<Player>> futures = idList.stream().
                map(id -> fetchPlayerData(String.format("https://www.fotmob.com/api/playerData?id=%d",id))).toList();

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allFutures.join();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    // Fetch playerID from APIs
    public static List<Integer> fetchPlayersID(){
        try (HttpClient client = HttpClient.newHttpClient()){
            //Create New Client and Request to access player ID and NAme in the Manchester United Team
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
    public static List<Integer> getPlayerIDs(String jsonResponse){
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
    public static CompletableFuture<Player> fetchPlayerData(String API_URL){
        return CompletableFuture.supplyAsync(() -> {
            try (HttpClient client = HttpClient.newHttpClient()){
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
    public static Player parsePlayerJsonResponse(String jsonResponse) {
        Gson gson = createGson();
        return gson.fromJson(jsonResponse, Player.class);
    }

    // Using a customised Deserializer to parse JSON from API
    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Player.class, new PlayerDeserializer())
                .create();
    }

    //fetch Propic of player online
    public static BufferedImage fetchProPic(int PlayerID) throws IOException {
        URL url = null;
        try {
            url = new URL(String.format("https://www.fotmob.com/_next/image?url=http%%3A%%2F%%2Fimages.fotmob.com%%2Fimage_resources%%2Fplayerimages%%2F%d.png&w=96&q=75",PlayerID));
            return ImageIO.read(url);
        } catch (IOException e) {
            System.err.println("Error fetching image for country: " + PlayerID);
        }
        return null;
    }

    //fetch country pic of player online
    public static BufferedImage fetchCountryPic(String countryName) throws IOException {
        String countryCode = convertCountryCode(countryName);
        URL url = null;
        try {
            url = new URL(String.format("https://flagcdn.com/w80/%s.png", countryCode));
            return ImageIO.read(url);
        } catch (IOException e) {
            System.err.println("Error fetching image for country: " + countryName);
        }
        return null;
    }

    // convert country name to country code
    private static String convertCountryCode(String countryName) {
        try (HttpClient client = HttpClient.newHttpClient()){
            //Create New Client and Request to get the code and country name mapping json
            HttpRequest basicRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://flagcdn.com/en/codes.json"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(basicRequest, HttpResponse.BodyHandlers.ofString());
            //Scrap Data from Team Page in Fotmob to get the player ID
            String jsonResponse = response.body();
            return getCountryCode(jsonResponse, countryName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // lookup the country code from a hashmap
    private static String getCountryCode(String jsonResponse, String countryName) {
        Gson gson = new Gson();
        HashMap<String,String> map = gson.fromJson(jsonResponse, HashMap.class);
        for(Map.Entry<String,String> entry : map.entrySet()){
            if (Objects.equals(countryName, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}