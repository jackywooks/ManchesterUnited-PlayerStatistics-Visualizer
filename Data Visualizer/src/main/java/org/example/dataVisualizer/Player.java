package org.example.dataVisualizer;
//Import Statement

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.0
 * @since       1.0
 */


public class Player {
    private String name;
    private int goal;
    private int assist;
    private int started;
    private int playing_minutes;
    private int yellow_card;
    private int red_card;
    private double rating;

    /**
     * Parameterized Constructor
     * @param name Name of the player
     * @param goal the no. of goals the player scored in 2023/24 Season
     * @param assist the no. of assist of the player assisted in 2023/24 Season
     * @param started no. of games the player started in 2023/24 Season
     * @param playing_minutes no. of games the player started in 2023/24 Season
     * @param yellow_card no. of yellow card the player received in 2023/24 Season
     * @param red_card no. of red card the player received in 2023/24 Season
     * @param rating the average rating of the player in 2023/24 Season
     */
    public Player(String name, int goal, int assist, int started, int playing_minutes, int yellow_card, int red_card, double rating) {
        setName(name);
        setGoal(goal);
        setAssist(assist);
        setStarted(started);
        setPlaying_minutes(playing_minutes);
        setYellow_card(yellow_card);
        setRed_card(red_card);
        setRating(rating);
    }

    /**
     * Getters and Setters for all attributes
     * For all numeric attribute, they should be larger than 0
     * For rating, it capped at 10
     */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
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
    public void setPlaying_minutes(int playing_minutes) {this.playing_minutes = Math.max(playing_minutes, 0);}
    public int getYellow_card() {return yellow_card;}
    public void setYellow_card(int yellow_card) {this.yellow_card = Math.max(yellow_card, 0);}
    public int getRed_card() {return red_card;}
    public void setRed_card(int red_card) {this.red_card = Math.max(red_card, 0);}
}
