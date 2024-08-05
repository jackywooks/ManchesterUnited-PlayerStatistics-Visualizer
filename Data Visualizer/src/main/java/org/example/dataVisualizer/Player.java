package org.example.dataVisualizer;
import com.google.gson.annotations.SerializedName;

//Import Statement

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.1
 * @since       1.0
 */

// Version 1.1 - Update of Player Class with GSON method to serialise JSON directly from Web
public class Player {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("position")
    private String position;
    @SerializedName("height")
    private String height;
    @SerializedName("shirtNo")
    private String shirtNo;
    @SerializedName("birthDate")
    private String birthDate;
    @SerializedName("nationality")
    private String nationality;
    @SerializedName("goals")
    private int goal = 0;
    @SerializedName("assist")
    private int assist = 0;
    @SerializedName("started")
    private int started = 0;
    @SerializedName("playing_minutes")
    private int playing_minutes =0;
    @SerializedName("yellow_card")
    private int yellow_card =0;
    @SerializedName("red_card")
    private int red_card =0;
    @SerializedName("rating")
    private double rating =0;

    /**
     * Parameterized Constructor
     * @param id ID of the player
     * @param name Name of the player
     * @param goal the no. of goals the player scored in 2023/24 Season
     * @param assist the no. of assist of the player assisted in 2023/24 Season
     * @param started no. of games the player started in 2023/24 Season
     * @param playing_minutes no. of games the player started in 2023/24 Season
     * @param yellow_card no. of yellow card the player received in 2023/24 Season
     * @param red_card no. of red card the player received in 2023/24 Season
     * @param rating the average rating of the player in 2023/24 Season
     */
    public Player(int id, String name, int goal, int assist, int started, int playing_minutes, int yellow_card, int red_card, double rating) {
        setId(id);
        setName(name);
        setGoal(goal);
        setAssist(assist);
        setStarted(started);
        setPlayingMinutes(playing_minutes);
        setYellowCard(yellow_card);
        setRedCard(red_card);
        setRating(rating);
    }

    // Default Constructor
    public Player() {

    }

    /**
     * Getters and Setters for all attributes
     * For all numeric attribute, they should be larger than 0
     * For rating, it capped at 10
     */

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPosition() {return position;}
    public void setPosition(String position) {this.position = position;}
    public String getHeight() {return height;}
    public void setHeight(String height) {this.height = height;}
    public String getShirtNo() {return shirtNo;}
    public void setShirtNo(String shirtNo) {this.shirtNo = shirtNo;}
    public String getBirthDate() {return birthDate;}
    public void setBirthDate(String birthDate) {this.birthDate = birthDate;}
    public String getNationality() {return nationality;}
    public void setNationality(String nationality) {this.nationality = nationality;}
    public int getGoal() {
        return goal;
    }
    public void setGoal(int goal) {
        this.goal = Math.max(goal, 0);
    }
    public int getAssist() {
        return assist;
    }
    public void setAssist(int assist) {this.assist = Math.max(assist, 0);}
    public double getRating() {return rating;}
    public void setRating(double rating) {this.rating = Math.clamp(rating, 0, 10);}
    public int getStarted() {return started;}
    public void setStarted(int started) {this.started = Math.max(started, 0);}
    public int getPlaying_minutes() {return playing_minutes;}
    public void setPlayingMinutes(int playing_minutes) {this.playing_minutes = Math.max(playing_minutes, 0);}
    public int getYellow_card() {return yellow_card;}
    public void setYellowCard(int yellow_card) {this.yellow_card = Math.max(yellow_card, 0);}
    public int getRed_card() {return red_card;}
    public void setRedCard(int red_card) {this.red_card = Math.max(red_card, 0);}

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", height='" + height + '\'' +
                ", shirtNo='" + shirtNo + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", nationality='" + nationality + '\'' +
                ", goal=" + goal +
                ", assist=" + assist +
                ", started=" + started +
                ", playing_minutes=" + playing_minutes +
                ", yellow_card=" + yellow_card +
                ", red_card=" + red_card +
                ", rating=" + rating +
                '}';
    }

    /**
     * Return the grade description of the current player
     * @return rating Grade in String
     */
    public String getRatingGrade(){
        if (rating > 7.5){
            return "Elite - Greater Than 7.5";
        }
        if (rating > 6.75){
            return "Nice - Greater Than 6.75";
        }
        return "Mediocre - 6.75 or Lower";
    }
}
