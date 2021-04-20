package app;

import java.net.URL;
import java.util.List;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.geometry.Pos.CENTER;

public class buttonController {

    private testMainController testController;

    public void injectMain(testMainController testController){
        this.testController = testController;
    }

    private utilityController utilityController;

    @FXML
    Button rotateCharacterButton;

    @FXML
    Button changeGenderButton;

    @FXML
    Button speechBubbleButton;

    @FXML
    Button thoughtBubbleButton;

    @FXML
    Button deleteCharacterButton;

    @FXML
    Button backgroundButton;

    @FXML
    Button addCharacterLeftButton;

    @FXML
    Button addCharacterRightButton;

    @FXML
    ColorPicker bodyColourPicker;

    @FXML
    ColorPicker hairColourPicker;

    @FXML
    AnchorPane characterMenuAnchorPane;

    @FXML
    GridPane buttonsGridPane;

    @FXML
    private void resize(){  //Method to resize the middle anchor pane
        characterMenuAnchorPane.setPrefHeight(buttonsGridPane.getHeight() * 4);
    }

    @FXML
    private void enableToolTips(){ //Method to enable tool tips when mouse is hovered over the buttons
        addCharacterLeftButton.setTooltip(
                new Tooltip("Add a character to the left side")
        );

        addCharacterRightButton.setTooltip(
                new Tooltip("Add a character to the right side")
        );

        speechBubbleButton.setTooltip(
                new Tooltip("Add speech bubble")
        );

        thoughtBubbleButton.setTooltip(
                new Tooltip("Add thought bubble")
        );

        rotateCharacterButton.setTooltip(
                new Tooltip("Rotate character")
        );

        changeGenderButton.setTooltip(
                new Tooltip("Change gender of character")
        );

        deleteCharacterButton.setTooltip(
                new Tooltip("Delete character")
        );

        backgroundButton.setTooltip(
                new Tooltip("Add background")
        );

        hairColourPicker.setTooltip(
                new Tooltip("Change hair colour")
        );

        bodyColourPicker.setTooltip(
                new Tooltip("Change body colour")
        );
    }

    void switchButtonState(boolean areEnabled){
        rotateCharacterButton.setDisable(!areEnabled);
        changeGenderButton.setDisable(!areEnabled);
        bodyColourPicker.setDisable(!areEnabled);
        hairColourPicker.setDisable(!areEnabled);
        speechBubbleButton.setDisable(!areEnabled);
        thoughtBubbleButton.setDisable(!areEnabled);
        deleteCharacterButton.setDisable(!areEnabled);
    }

    @FXML
    private void addCharacterLeft(ActionEvent event) throws IOException { //Method called when button is pressed to add a character into the left panel

        utilityController.swapMiddlePanel(utilityController.backgroundGridPane, utilityController.charactersGridPane);

        if(utilityController.bottomLeftIV.getImage() == null){
            switchButtonState(false);
        } else {
            switchButtonState(true);
        }

        if(utilityController.characterImages == null){
            utilityController.midScrollPane.setVisible(true);
            utilityController.loadCharacterImages();
        }

        utilityController.midScrollPane.setVvalue(0);
        utilityController.setBorder(utilityController.bottomLeftBorder);
        utilityController.comicSelection = utilityController.bottomLeftIV;
        utilityController.comicCharacterSelection = utilityController.bottomLeftIV;
        event.consume();
    }

    @FXML
    private void addCharacterRight(ActionEvent event) throws IOException { //Method called when button is pressed to add a character into the right panel

        utilityController.swapMiddlePanel(utilityController.backgroundGridPane, utilityController.charactersGridPane);

        if(utilityController.bottomRightIV.getImage() == null){
            switchButtonState(false);
        } else {
            switchButtonState(true);
        }


        if(utilityController.characterImages == null){
            utilityController.midScrollPane.setVisible(true);
            utilityController.loadCharacterImages();
        }

        utilityController.midScrollPane.setVvalue(0);
        utilityController.setBorder(utilityController.bottomRightBorder);
        utilityController.comicSelection = utilityController.bottomRightIV;
        utilityController.comicCharacterSelection = utilityController.bottomRightIV;
        event.consume();
    }

    @FXML
    private void addSpeechBubble(){ //Method called when user presses the speech bubble button

        URL url = getClass().getResource("/images/buttons/speech.png");
        String currentPath = url.toString();

        ImageView imageView = new ImageView(currentPath);

        utilityController.insertBubble(imageView);
    }

    @FXML
    private void addThoughtBubble(){ //Method called when user presses the thought bubble button

        URL url = getClass().getResource("/images/buttons/thought.png");
        String currentPath = url.toString();

        ImageView imageView = new ImageView(currentPath);

        utilityController.insertBubble(imageView);
    }

    @FXML
    private void deleteCharacter() {  //Method called when the user presses the delete button which removes characters and text from the selected half of the comic
        if(utilityController.comic.getSelected().equals(utilityController.comic.getLeftCharacter())){
            utilityController.bottomLeftIV.setImage(null);
            utilityController.comic.setLeftCharacter(null);
            utilityController.centreLeft.setImage(null);
            utilityController.leftTextField.clear();
            utilityController.leftTextField.setVisible(false);
        }
        else{
            utilityController.bottomRightIV.setImage(null);
            utilityController.comic.setRightCharacter(null);
            utilityController.centreRight.setImage(null);
            utilityController.rightTextField.clear();
            utilityController.rightTextField.setVisible(false);
        }
        utilityController.selectedBorder.setVisible(false);
        utilityController.comic.setSelected(null);
        utilityController.comicSelection = null;
        utilityController.comicCharacterSelection = null;
        switchButtonState(false);
    }

    @FXML
    private void changeBackground() throws IOException {  //Method called when change background button is pressed
        utilityController.swapMiddlePanel(utilityController.charactersGridPane, utilityController.backgroundGridPane);

        if(utilityController.backgroundImages == null)
        {
            utilityController.midScrollPane.setVisible(true);
            utilityController.loadBackgroundImages();
        }

        utilityController.midScrollPane.setVvalue(0);
    }

    @FXML
    public Color getChosenHairColour(){ //Method that fetches the hair colour chosen by the user from the ColourPicker
        Color chosenColour = checkColour(hairColourPicker.getValue());
        return chosenColour;
    }

    @FXML
    public Color getChosenBodyColour(){  //Method that fetches the body colour chosen by the user from the ColourPicker
        Color chosenColour = checkColour(bodyColourPicker.getValue());
        return chosenColour;
    }

    public Color checkColour(Color colour){  //Method to check if the chosen colour is pure white/black and if so, changing the tone slightly
        if(colour.equals(Color.web("000000"))){
            colour = Color.web("00000f");
        }
        else if(colour.equals(Color.web("ffffff"))){
            colour = Color.web("fffff0");
        }

        return colour;
    }

    @FXML
    public void changeGender() { //Method called when user presses the change gender button

        if(utilityController.comic.getSelected().getGender().equals("female")){
            utilityController.setMale();
        }
        else{
            utilityController.setFemale();
        }
    }

    @FXML
    public void changeHairColour() { //Method called when user wants to change the character's hair colour
        Image image = utilityController.comicSelection.getImage();
        int imageHeight = (int)image.getHeight();
        int imageWidth = (int)image.getWidth();
        boolean changed = false;

        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();
        Color colour;
        Color maleHairColour = new Color(0, 0, 0, 0);

        if(utilityController.comic.getSelected().getGender().equals("male"))  //Changing male hair only if the character is male
        {
            for(int i = 0; i < imageWidth; i++){
                for(int j = 0; j < imageHeight; j++){
                    colour = PR.getColor(i, j);
                    if(utilityController.compareColours(colour, utilityController.comic.getSelected().getMaleHairColour())){
                        colour = getChosenHairColour();
                        changed = true;
                    }
                    PW.setColor(i, j, colour);
                }
            }
            if(changed)
            {
                utilityController.comic.getSelected().setMaleHairColour(getChosenHairColour());
                utilityController.comic.getSelected().setFemaleHairColour(utilityController.changeTone(getChosenHairColour()));
            }
        }

        if(utilityController.comic.getSelected().getGender().equals("female")) //Changing both male and female hair only if the character is female
        {
            for(int i = 0; i < imageWidth; i++){
                for(int j = 0; j < imageHeight; j++){
                    colour = PR.getColor(i, j);
                    if(utilityController.compareColours(colour, utilityController.comic.getSelected().getFemaleHairColour())){
                        colour = getChosenHairColour();
                    }

                    else if(utilityController.compareColours(colour, utilityController.comic.getSelected().getMaleHairColour()))
                    {
                        colour = utilityController.changeTone(getChosenHairColour());
                        maleHairColour = colour;
                    }

                    PW.setColor(i, j, colour);
                }
            }
            utilityController.comic.getSelected().setFemaleHairColour(getChosenHairColour());
            utilityController.comic.getSelected().setMaleHairColour(maleHairColour);

        }
        utilityController.comic.getSelected().setImage(wImage);
        utilityController.comicSelection.setImage(wImage);
    }

    @FXML
    public void changeSkinColour() {  //Method called when user wants to change the character's skin colour
        Image image = utilityController.comicSelection.getImage();
        int imageHeight = (int)image.getHeight();
        int imageWidth = (int)image.getWidth();
        boolean changed = false;

        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();
        Color colour;
        Color lipColour = new Color(0,0,0,0);

        for(int i = 0; i < imageWidth; i++){
            for(int j = 0; j < imageHeight; j++){
                colour = PR.getColor(i, j);
                if(utilityController.compareColours(colour, utilityController.comic.getSelected().getSkinColour())){
                    colour = getChosenBodyColour();
                    changed = true;
                }
                else if(utilityController.comic.getSelected().getGender().equals("male") && utilityController.compareColours(colour, utilityController.comic
                        .getSelected().getLipColour())){
                    colour = utilityController.changeTone(getChosenBodyColour());
                    lipColour = colour;
                }
                PW.setColor(i, j, colour);
            }
        }
        if(changed)
        {
            utilityController.comic.getSelected().setLipColour(lipColour);
            utilityController.comic.getSelected().setSkinColour(getChosenBodyColour());
        }
        utilityController.comic.getSelected().setImage(wImage);
        utilityController.comicSelection.setImage(wImage);
    }

    @FXML
    public void help() throws IOException{  //Method called when user wants to display help menu
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/help.fxml"));
        Stage helpStage = new Stage();
        Scene helpScene = new Scene(root);
        helpStage.setHeight(500.0);
        helpStage.setWidth(800.0);
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.show();
    }

    @FXML
    public void rotate(){ //Method to rotate a character
        utilityController.comic.getSelected().changeFacing();
        utilityController.comicSelection.setScaleX(utilityController.comicSelection.getScaleX() * -1);
    }


}