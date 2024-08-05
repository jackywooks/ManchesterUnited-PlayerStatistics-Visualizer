package org.example.dataVisualizer;
//import Statement
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.example.dataVisualizer.ApiClient.fetchCountryPic;
import static org.example.dataVisualizer.ApiClient.fetchProPic;
import static org.example.dataVisualizer.ViewController.switchView;

/**
 * @author      Jacky Woo jackywooksca@gmail.com
 * @version     1.0
 * @since       1.0
 */

public class PlayerDetailController {

    @FXML
    public Label name;
    @FXML
    public ImageView propic;
    @FXML
    public Label position;
    @FXML
    public Label height;
    @FXML
    public Label shirtNo;
    @FXML
    public Label bday;
    @FXML
    public Label nationality;
    @FXML
    public Label goal;
    @FXML
    public Label assist;
    @FXML
    public Label start;
    @FXML
    public Label minutes;
    @FXML
    public Label yellowCard;
    @FXML
    public Label redCard;
    @FXML
    public Label rating;
    @FXML
    public ImageView flag;

    /**
     * Initialize the Detail view and related components
     */


    @FXML
    private void initialize() throws IOException, ParseException {
        Player currentPlayer = PlayerStatController.getSelectedPlayer();
        SetText(currentPlayer);
        ratingColor(currentPlayer);
        setPropic(currentPlayer);
        setFlag(currentPlayer);
    }

    /**
     * Set the country flag of the active player's info
     * @param currentPlayer current active Player
     */
    private void setFlag(Player currentPlayer) throws IOException {
        BufferedImage imageBuffer = fetchCountryPic(currentPlayer.getNationality());
        Image image = null;
        if (imageBuffer != null) {
            image = SwingFXUtils.toFXImage(imageBuffer, null);
            flag.setImage(image);
        }
    }

    /**
     * Set the propic of the active player's info
     * @param currentPlayer current active Player
     */
    private void setPropic(Player currentPlayer) throws IOException {
        BufferedImage imageBuffer = fetchProPic(currentPlayer.getId());
        Image image = null;
        if (imageBuffer != null) {
            image = SwingFXUtils.toFXImage(imageBuffer, null);
            propic.setImage(image);
        }
    }

    /**
     * Set the text of JavaFx Component as per the active player's info
     * @param currentPlayer current active Player
     */
    private void SetText(Player currentPlayer) throws ParseException {
        name.setText(currentPlayer.getName());
        position.setText(currentPlayer.getPosition());
        height.setText(currentPlayer.getHeight());
        shirtNo.setText(currentPlayer.getShirtNo());
        parseBday(currentPlayer);
        nationality.setText(currentPlayer.getNationality());
        goal.setText(String.valueOf(currentPlayer.getGoal()));
        assist.setText(String.valueOf(currentPlayer.getAssist()));
        start.setText(String.valueOf(currentPlayer.getStarted()));
        minutes.setText(String.valueOf(currentPlayer.getPlaying_minutes()));
        yellowCard.setText(String.valueOf(currentPlayer.getYellow_card()));
        redCard.setText(String.valueOf(currentPlayer.getRed_card()));
        rating.setText(String.valueOf(currentPlayer.getRating()));
    }

    /**
     * parse the date in correct format and shown the age
     * @param currentPlayer current active Player
     */
    private void parseBday(Player currentPlayer) throws ParseException {
        // Parsing the bday in YYYY-MM-DD format
        String birthDateString = currentPlayer.getBirthDate();
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dff.parse(birthDateString);
        String formattedBirthDateString = df.format(birthDate);

        // Calculate the Age
        Date now = new Date();
        int age = now.getYear() - birthDate.getYear();

        // Format the display info as "YYYY-MM-DD (Age)
        formattedBirthDateString = String.format("%s (%d)",formattedBirthDateString,age);
        bday.setText(formattedBirthDateString);
    }

    /**
     * Change the current player's rating color
     * @param currentPlayer current active Player
     */
    private void ratingColor(Player currentPlayer) {
        if(currentPlayer.getRating() > 7){
            rating.getStyleClass().add("lbl-success");
        } else if(currentPlayer.getRating() > 6.5){
            rating.getStyleClass().add("lbl-warning");
        } else {
            rating.getStyleClass().add("lbl-danger");
        }
    }


    @FXML
    public void switchToTableView(ActionEvent event) throws IOException {
        switchView(event, "player-stat.fxml");
    }
}