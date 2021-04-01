package app;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.scene.layout.Region;



public class TestController {

    private Comic mainComic = new Comic();

    @FXML
    ImageView currentlySelected; //Global variable to track which section of the panel is currently selected

    @FXML
    Region selectedBorder = null; //Global variable to track which border is currently selected

    @FXML
    Button rotateCharacterButton;

    @FXML
    Button changeGenderButton;

    @FXML
    private ImageView bottomLeftIV;

    @FXML
    private ImageView bottomRightIV;

    @FXML
    private MenuItem helpMenu;

    @FXML
    private Region bottomLeftBorder;

    @FXML
    private Region bottomRightBorder;

    @FXML
    private AnchorPane scrollAnchorPane;

    @FXML
    private GridPane buttonsGridPane;

    @FXML
    private ColorPicker bodyColourPicker;

    @FXML
    private ColorPicker hairColourPicker;

    @FXML
    private void resize(){
        scrollAnchorPane.setPrefHeight(buttonsGridPane.getHeight() * 4);
    }

    @FXML
    private void insertCharacterLeft(ActionEvent event) {
        event.consume();
        loadImageLeft();
        clickLeft();
    }

    @FXML
    private void insertCharacterRight(ActionEvent event) {
        event.consume();
        loadImageRight();
        clickRight();
    }

    @FXML
    public void loadImageLeft() {
        Image characterImage = new Image(selectImage());
        mainComic.setLeftCharacter(new Character(characterImage, 0));
        bottomLeftIV.setImage(mainComic.getLeftCharacter().getImage());
    }

    @FXML
    public void clickLeft(){
        bottomLeftIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { //Event handler for bottom left image
            currentlySelected = bottomLeftIV;
            mainComic.setSelected(mainComic.getLeftCharacter());
            rotateCharacterButton.setDisable(false);  //Enable rotate function
            changeGenderButton.setDisable(false);
            bodyColourPicker.setDisable(false);
            hairColourPicker.setDisable(false);
            setBorder(bottomLeftBorder);
            event.consume();
        });
    }

    @FXML
    public void loadImageRight() {
        Image characterImage = new Image(selectImage());
        mainComic.setRightCharacter(new Character(characterImage, 1));
        bottomRightIV.setImage(mainComic.getRightCharacter().getImage());
        bottomRightIV.setScaleX(-1);
    }

    @FXML
    public void clickRight(){
        bottomRightIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { //Event handler for bottom left image
            currentlySelected = bottomRightIV;
            mainComic.setSelected(mainComic.getRightCharacter());
            rotateCharacterButton.setDisable(false);  //Enable rotate function
            changeGenderButton.setDisable(false);
            bodyColourPicker.setDisable(false);
            hairColourPicker.setDisable(false);
            setBorder(bottomRightBorder);
            event.consume();
        });
    }

    @FXML
    public String selectImage() {   //Method to get absolute path of desired image selected by the user

        FileChooser chooser = new FileChooser();
        URL imageUrl = getClass().getResource("/images/characters");
        String toTrim = imageUrl.toString();
        String imagePath = toTrim.substring(6);
        chooser.setInitialDirectory(new File(imagePath));

        imagePath = chooser.showOpenDialog(new Stage()).toString();

        return "file:" + imagePath;
    }

    @FXML
    public void rotate(){
        mainComic.getSelected().changeFacing();
        currentlySelected.setScaleX(currentlySelected.getScaleX() * -1);
    }

    @FXML
    private void setBorder(Region newBorder) {
        if(selectedBorder != null){
            selectedBorder.setVisible(false);
        }
        selectedBorder = newBorder;
        selectedBorder.setVisible(true);
    }

    @FXML
    public void help() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/help.fxml"));
        Stage helpStage = new Stage();
        Scene helpScene = new Scene(root);
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.show();
    }

    @FXML
    public Color getChosenBodyColour(){
        return bodyColourPicker.getValue();
    }

    @FXML
    public void changeSkinColour() {
        Image image = currentlySelected.getImage();
        int imageHeight = (int)image.getHeight();
        int imageWidth = (int)image.getWidth();
        boolean changed = false;

        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();
        Color colour;

        for(int i = 0; i < imageWidth; i++){
            for(int j = 0; j < imageHeight; j++){
                colour = PR.getColor(i, j);
                if(colour.equals(mainComic.getSelected().getSkinColour())){
                    colour = getChosenBodyColour();
                    changed = true;
                }
                PW.setColor(i, j, colour);
            }
        }
        if(changed)
        {
            mainComic.getSelected().setSkinColour(getChosenBodyColour());
        }
        mainComic.getSelected().setImage(wImage);
        currentlySelected.setImage(wImage);
    }


    @FXML
    public Color getChosenHairColour(){
        return hairColourPicker.getValue();
    }

    @FXML
    public void changeHairColour() {
        Image image = currentlySelected.getImage();
        int imageHeight = (int)image.getHeight();
        int imageWidth = (int)image.getWidth();
        boolean changed = false;

        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();
        Color colour;
        Color maleHairColour = new Color(0, 0, 0, 0);

        if(mainComic.getSelected().getGender().equals("male"))
        {
            for(int i = 0; i < imageWidth; i++){
                for(int j = 0; j < imageHeight; j++){
                    colour = PR.getColor(i, j);
                    if(colour.toString().equals(mainComic.getSelected().getMaleHairColour().toString())){
                        colour = getChosenHairColour();
                        changed = true;
                    }
                    PW.setColor(i, j, colour);
                }
            }
            if(changed)
            {
                mainComic.getSelected().setMaleHairColour(getChosenHairColour());
                mainComic.getSelected().setFemaleHairColour(changeTone(getChosenHairColour()));
            }
        }

        if(mainComic.getSelected().getGender().equals("female"))
        {
            for(int i = 0; i < imageWidth; i++){
                for(int j = 0; j < imageHeight; j++){
                    colour = PR.getColor(i, j);
                    if(colour.toString().equals(mainComic.getSelected().getFemaleHairColour().toString())){
                        colour = getChosenHairColour();
                    }

                    else if(colour.toString().equals(mainComic.getSelected().getMaleHairColour().toString()))
                    {
                        colour = changeTone(getChosenHairColour());
                        maleHairColour = colour;
                    }

                    PW.setColor(i, j, colour);
                }
            }
            mainComic.getSelected().setFemaleHairColour(getChosenHairColour());
            mainComic.getSelected().setMaleHairColour(maleHairColour);

        }
        mainComic.getSelected().setImage(wImage);
        currentlySelected.setImage(wImage);
    }

    @FXML
    public void changeGender() {
        Character character = mainComic.getSelected();

        if(character.getGender().equals("female")){
            setMale(character);
        }
        else{
            setFemale(character);
        }
    }

    private void setMale(Character character) {
        Image image = character.getImage();
        int h = (int)image.getHeight();
        int w = (int)image.getWidth();
        WritableImage wImage = new WritableImage(w, h);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();

        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                Color color = PR.getColor(x, y);
                if(isLips(color)){
                    color = changeTone(mainComic.getSelected().getSkinColour());
                    mainComic.getSelected().setLipColour(color);
                }
                else if(color.toString().equals(character.getFemaleHairColour().toString())){
                    color = Color.web("fffffe");
                }
                else if(color.equals(Color.web("ecb4b5"))){
                    color = Color.web("feffff");
                }
                PW.setColor(x, y, color);
            }
        }

        character.setGender("male");
        currentlySelected.setImage(wImage);
        character.setImage(wImage);
    }

    private void setFemale(Character character) {
        Image image = character.getImage();
        int h = (int)image.getHeight();
        int w = (int)image.getWidth();
        WritableImage wImage = new WritableImage(w, h);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();

        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                Color color = PR.getColor(x, y);
                if(color.toString().equals(mainComic.getSelected().getLipColour().toString())){
                    color = Color.web("ff0000");
                }
                else if(color.equals(Color.web("fffffe"))){
                    color = character.getFemaleHairColour();
                }
                else if(color.equals(Color.web("feffff"))){
                    color = Color.web("ecb4b5");
                }
                PW.setColor(x, y, color);
            }
        }

        character.setGender("female");
        currentlySelected.setImage(wImage);
        character.setImage(wImage);
    }

    private boolean isLips(Color color){

        boolean isItLips = false;

        if(color.getRed()==1 && color.getGreen()<0.6 && color.getBlue()>=color.getGreen()/2){
            isItLips = true;
        }
        else if(color.getGreen()==0 && color.getBlue()==0 && color.getRed()>0.8){
            isItLips = true;
        }

        return isItLips;
    }

    private Color changeTone(Color colour){
        Double[] colourList = new Double[3];
        colourList[0] = colour.getRed();
        colourList[1] = colour.getGreen();
        colourList[2] = colour.getBlue();

        if(colourList[0] < 0.990 && colourList[0] != 0){
            colourList[0] += 0.01;
        }
        else if(colourList[1] < 0.990 && colourList[1] != 0){
            colourList[1] += 0.01;
        }
        else{
            if(colourList[2] < 0.990){
                colourList[2] += 0.01;
            }
            else{
                colourList[2] -= 0.01;
            }
        }

        return new Color(colourList[0], colourList[1], colourList[2], colour.getOpacity());
    }
}