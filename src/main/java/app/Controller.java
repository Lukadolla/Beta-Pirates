package app;


import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    //Creating instances of other controllers responsible for different aspects of the application.
    Comic comic = new Comic();
    private MidScrollPaneController midScrollPaneController = new MidScrollPaneController(this);
    private ComicController comicController = new ComicController(this);
    private CharacterController characterController = new CharacterController(this);
    private ButtonController buttonController = new ButtonController(this);
    private ColourController colourController = new ColourController(this);
    private LowerPanelController lowerPanelController = new LowerPanelController(this);
    private SaveComicController saveComicController = new SaveComicController(this);
    private LoadComicController loadComicController = new LoadComicController(this);

    ImageView comicCharacterSelection; // Track character selection independent of comic selection

    @FXML
    ImageView comicSelection; //Global variable to track which section of the panel is currently selected

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
    Region selectedBorder = null; //Global variable to track which border is currently selected

    @FXML
    protected ImageView bottomLeftIV; //Bottom left ImageView where the character is inserted

    @FXML
    protected ImageView bottomRightIV; //Bottom right ImageView where the character is inserted

    @FXML
    Region bottomLeftBorder;

    @FXML
    Region bottomRightBorder;

    @FXML
    private AnchorPane characterMenuAnchorPane;

    @FXML
    AnchorPane backgroundImageScale;

    @FXML
    private AnchorPane scrollPaneAnchorPane;

    @FXML
    protected TextField topText;

    @FXML
    protected TextField bottomText;

    @FXML
    private GridPane buttonsGridPane;

    @FXML
    protected GridPane charactersGridPane;

    @FXML
    protected ImageView background;  //ImageView containing the background image

    @FXML
    private GridPane comicImageGridPane;

    @FXML
    protected GridPane backgroundGridPane;

    @FXML
    ColorPicker bodyColourPicker;

    @FXML
    ColorPicker hairColourPicker;

    @FXML
    protected ScrollPane midScrollPane;

    @FXML
    ImageView centreRight;

    @FXML
    ImageView centreLeft;

    @FXML
    TextField leftTextField;

    @FXML
    TextField rightTextField;

    @FXML
    ImageView leftTextImageview;

    @FXML
    ImageView rightTextImageview;

    @FXML
    HBox leftHbox;

    @FXML
    GridPane bottomGridPane;

    @FXML
    MenuItem SaveXMLMenu;

    public int sizeScale=10;

    // Methods that call the other controllers
    CharacterController getCharacterController(){ return characterController; }

    ComicController getComicController(){ return comicController; }

    ButtonController getButtonController() {
        return buttonController;
    }

    ColourController getColourController() {
        return colourController;
    }

    LowerPanelController getLowerPanelController(){
        return lowerPanelController;
    }

    SaveComicController getSaveComicController() { return saveComicController; }

    LoadComicController getLoadComicController() { return loadComicController; }

    @FXML
    void resize(){  //Method to resize the middle anchor pane
        characterMenuAnchorPane.setPrefHeight(buttonsGridPane.getHeight() * sizeScale);
        scrollPaneAnchorPane.setPrefWidth(buttonsGridPane.getHeight() * 60);
    }

    @FXML
    public void rotate(){ //Method to rotate a character
        comic.getSelected().changeFacing();
        comicSelection.setScaleX(comicSelection.getScaleX() * -1);
    }

    @FXML
    void setBorder(Region newBorder) { //Method that sets the border on a selected component
        if(selectedBorder != null){
            selectedBorder.setVisible(false);
        }
        selectedBorder = newBorder;
        selectedBorder.setVisible(true);
    }

    @FXML
    public void help() throws IOException{  //Method called when user wants to display help menu
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/help.fxml"));
        Stage helpStage = new Stage();
        Scene helpScene = new Scene(root);
        helpStage.setHeight(650.0);
        helpStage.setWidth(1500.0);
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.show();
    }

    //All methods below are proxy methods that are called in the main.fxml and then call the corresponding methods in the other controllers
    @FXML
    private void addCharacterRight(ActionEvent event) throws IOException {
        buttonController.addCharacterRight();
        event.consume();
    }

    @FXML
    private void addCharacterLeft(ActionEvent event) throws IOException {
        buttonController.addCharacterLeft();
        event.consume();
    }

    @FXML
    private void changeGender(ActionEvent event) {
        buttonController.changeGender();
        event.consume();
    }

    @FXML
    private void addSpeechBubble(ActionEvent event) {
        buttonController.addSpeechBubble();
        event.consume();
    }

    @FXML
    private void addThoughtBubble(ActionEvent event) {
        buttonController.addThoughtBubble();
        event.consume();
    }

    @FXML
    private void deleteCharacter(ActionEvent event) {
        buttonController.deleteCharacter();
        event.consume();
    }

    @FXML
    private void enableToolTips(MouseEvent event) {
        buttonController.enableToolTips();
        event.consume();
    }

    @FXML
    private void changeHairColour(ActionEvent event) {
        colourController.changeHairColour();
        event.consume();
    }

    @FXML
    private void changeSkinColour(ActionEvent event) {
        colourController.changeSkinColour();
        event.consume();
    }

    @FXML
    private void changeBackground() throws IOException {  //Method called when change background button is pressed
        midScrollPaneController.addBackgroundPane();
    }

    public MidScrollPaneController getMidScrollPaneController() {
        return midScrollPaneController;
    }


    @FXML
    private void addToPanelList(ActionEvent event) throws CloneNotSupportedException {
        lowerPanelController.addToPanelList();
        event.consume();
    }

    @FXML
    private void keyPressed(KeyEvent event) throws CloneNotSupportedException {
        lowerPanelController.keyPressed(event);
        event.consume();
    }

    @FXML
    private void createXML(ActionEvent event) throws IOException {
        saveComicController.createXML();
        event.consume();
    }

    @FXML
    private void loadXML(ActionEvent event) {
        loadComicController.loadXML();
        event.consume();
    }
}