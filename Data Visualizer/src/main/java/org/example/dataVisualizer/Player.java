package org.example.dataVisualizer;
//Import Statement

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.0
 * @since       1.0
 */


public class Player {
    /**
     * Position of the player
     * FW (Forward), MF (Midfielder), DF (Defender), and GK (Goalkeeper)
     */
    public enum Position {
        FW, MF, DF, GK
    }

    private String name;
    private int goal;
    private int assist;
    private double rating;
    private Position position;

    /**
     * Parameterized Constructor
     * @param name Name of the player
     * @param position Position of the player played
     * @param goal the no. of goals the player scored in 2023/24 Season
     * @param assist the no. of assist of the player assisted in 2023/24 Season
     * @param rating the average rating of the player in 2023/24 Season
     */
    public Player(String name, Position position, int goal, int assist, double rating) {
        setName(name);
        setPosition(position);
        setGoal(goal);
        setAssist(assist);
        setRating(rating);
    }

    /**
     * Getters and Setters for all attributes
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
        this.goal = goal;
    }
    public int getAssist() {
        return assist;
    }
    public void setAssist(int assist) {
        this.assist = assist;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        //The rating must be in range of 0 to 10
        if(rating >= 0 && rating <= 10) {
            this.rating = rating;
        } else {
            this.rating = 0.0;
        }
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    //Helper Function - to print the playerInfo and check error
    //    public String printPlayerInfo(){
    //        return "Name: " + name + "Position" + position.toString() + "Goal: " + goal + ", Assist: " + assist + ", Rating: " + rating;
    //    }
}
