package app;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    Button rotateCharacter;

    @FXML
    Button changeGender;

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
    private AnchorPane toResize;

    @FXML
    private GridPane heightReference;

    @FXML
    private void resize(){
        toResize.setPrefHeight(heightReference.getHeight() * 4);
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
        Image image = new Image(selectImage());
        mainComic.setLeftCharacter(new Character(image, 0));
        bottomLeftIV.setImage(mainComic.getLeftCharacter().getImage());
    }

    @FXML
    public void clickLeft(){
        bottomLeftIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { //Event handler for bottom left image
            currentlySelected = bottomLeftIV;
            mainComic.setSelected(mainComic.getLeftCharacter());
            rotateCharacter.setDisable(false);  //Enable rotate function
            changeGender.setDisable(false);
            setBorder(bottomLeftBorder);
            event.consume();
        });
    }

    @FXML
    public void loadImageRight() {
        Image image = new Image(selectImage());
        mainComic.setRightCharacter(new Character(image, 1));
        bottomRightIV.setImage(mainComic.getRightCharacter().getImage());
        bottomRightIV.setScaleX(-1);
    }

    @FXML
    public void clickRight(){
        bottomRightIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { //Event handler for bottom left image
            currentlySelected = bottomRightIV;
            mainComic.setSelected(mainComic.getRightCharacter());
            rotateCharacter.setDisable(false);  //Enable rotate function
            changeGender.setDisable(false);
            setBorder(bottomRightBorder);
            event.consume();
        });
    }

    @FXML
    public String selectImage() {   //Method to get absolute path of desired image selected by the user
        String imagePath = "";

        FileChooser chooser = new FileChooser();
        URL url = getClass().getResource("/images/characters");
        String toTrim = url.toString();
        String currentPath = toTrim.substring(6);
        chooser.setInitialDirectory(new File(currentPath));

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

    public void changeSkinColour(Character character, ImageView iv) {
        Image image = character.getImage();
        int h = (int)image.getHeight();
        int w = (int)image.getWidth();
        WritableImage wImage = new WritableImage(w, h);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();

        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                Color color = PR.getColor(x, y);
                if(color.equals(Color.web("ffe8d8"))){
                    color = Color.web("ff0000");
                }
                PW.setColor(x, y, color);
            }
        }
        iv.setImage(wImage);
        character.setImage(wImage);
    }

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
                    color = Color.web("ffe8d9");
                }
                PW.setColor(x, y, color);
            }
        }
        character.setGender("male");
        if(character.getPosition()==0){
            bottomLeftIV.setImage(wImage);
        }
        else {
            bottomRightIV.setImage(wImage);
        }
    }

    private void setFemale(Character character){
        bottomRightIV.setImage(character.getImage());
        character.setGender("female");
    }

    private boolean isLips(Color color){

        boolean isItLips = false;

        if(color.getRed()==1 && 0.8>color.getGreen() && 0.8>color.getBlue()){
            isItLips = true;
        }
        else if(color.getBlue()==0 && color.getGreen()==0 && color.getRed()>0){
            isItLips = true;
        }

        return isItLips;
    }
}