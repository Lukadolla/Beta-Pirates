package app;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

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
    ImageView comicSelection; //Global variable to track which section of the panel is currently selected
    ImageView comicCharacterSelection=null; // Track character selection independent of comic selection
    int sizeScale = 10;

    @FXML AnchorPane backgroundImageScale;
    @FXML AnchorPane characterMenuAnchorPane;
    @FXML AnchorPane scrollPaneAnchorPane;
    @FXML Button addCharacterLeftButton;
    @FXML Button addCharacterRightButton;
    @FXML Button backgroundButton;
    @FXML Button changeGenderButton;
    @FXML Button deleteCharacterButton;
    @FXML Button rotateCharacterButton;
    @FXML Button speechBubbleButton;
    @FXML Button thoughtBubbleButton;
    @FXML Button loadPanelButton;
    @FXML Button deletePanelButton;
    @FXML Button replacePanelButton;
    @FXML ColorPicker bodyColourPicker;
    @FXML ColorPicker hairColourPicker;
    @FXML GridPane backgroundGridPane;
    @FXML GridPane bottomGridPane;
    @FXML GridPane buttonsGridPane;
    @FXML GridPane charactersGridPane;
    @FXML HBox leftHbox;
    @FXML HBox rightHbox;
    @FXML ImageView background;  //ImageView containing the background image
    @FXML ImageView bottomLeftIV; //Bottom left ImageView where the character is inserted
    @FXML ImageView bottomRightIV; //Bottom right ImageView where the character is inserted
    @FXML ImageView centreLeft;
    @FXML ImageView centreRight;
    @FXML ImageView leftTextImageview;
    @FXML ImageView rightTextImageview;
    @FXML MenuItem SaveXMLMenu;
    @FXML MenuItem SaveHTMLMenu;
    @FXML Region leftTextRegion;
    @FXML Region rightTextRegion;
    @FXML Region bottomLeftBorder;
    @FXML Region bottomRightBorder;
    @FXML Region selectedBorder = null; //Global variable to track which border is currently selected
    @FXML ScrollPane midScrollPane;
    @FXML TextField bottomText;
    @FXML TextField leftTextField;
    @FXML TextField rightTextField;
    @FXML TextField topText;


    @FXML void resize(){  //Method to resize the middle anchor pane
        characterMenuAnchorPane.setPrefHeight(buttonsGridPane.getHeight() * sizeScale);
        scrollPaneAnchorPane.setPrefWidth(buttonsGridPane.getHeight() * 60);
    }


    @FXML public void rotate(){ //Method to rotate a character
        comic.getSelected().changeFacing();
        comicSelection.setScaleX(comicSelection.getScaleX() * -1);
    }


    @FXML void setBorder(Region newBorder) { //Method that sets the border on a selected component
        if(selectedBorder != null){
            selectedBorder.setVisible(false);
        }
        selectedBorder = newBorder;
        selectedBorder.setVisible(true);
    }


    @FXML public void help() throws IOException{  //Method called when user wants to display help menu
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/help.fxml"));
        Stage helpStage = new Stage();
        Scene helpScene = new Scene(root);

        URL url = getClass().getResource("/images/buttons/help-icon.png");
        String imagePath = url.toString();
        helpStage.getIcons().add(new Image(imagePath));

        helpStage.setHeight(650.0);
        helpStage.setWidth(1500.0);
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.show();
    }

    //All methods below are proxy methods that are called in the main.fxml and then call the corresponding methods in the other controllers
    @FXML private void addCharacterRight(ActionEvent event) throws IOException, URISyntaxException {
        buttonController.addCharacterRight();
        event.consume();
    }

    @FXML private void addCharacterLeft(ActionEvent event) throws IOException, URISyntaxException {
        buttonController.addCharacterLeft();
        event.consume();
    }

    @FXML private void changeGender(ActionEvent event) {
        buttonController.changeGender();
        event.consume();
    }

    @FXML private void addSpeechBubble(ActionEvent event) {
        buttonController.addSpeechBubble();
//        comicController.insertTextGraphic("The quick brown fox jumps over the lazy dog");
        event.consume();
    }

    @FXML private void swapLeftVisibility() {
        leftTextField.setVisible(true);
        leftTextImageview.setVisible(false);
    }

    @FXML private void swapRightVisibility() {
        rightTextField.setVisible(true);
        rightTextImageview.setVisible(false);
    }

    @FXML private void addThoughtBubble(ActionEvent event) {
        buttonController.addThoughtBubble();
        event.consume();
    }

    @FXML private void deleteCharacter(ActionEvent event) {
        buttonController.deleteCharacter();
        event.consume();
    }

    @FXML private void changeHairColour(ActionEvent event) {
        colourController.changeHairColour();
        event.consume();
    }

    @FXML private void changeSkinColour(ActionEvent event) {
        colourController.changeSkinColour();
        event.consume();
    }

    @FXML private void changeBackground() throws IOException, URISyntaxException {  //Method called when change background button is pressed
        midScrollPaneController.addBackgroundPane();
    }

    @FXML private void addToPanelList(ActionEvent event) throws CloneNotSupportedException, IOException, URISyntaxException {
        lowerPanelController.addToPanelList();
        event.consume();
    }

    @FXML private void importPanel(ActionEvent event) throws CloneNotSupportedException {
        lowerPanelController.importPanel();
        event.consume();
    }

    @FXML private void deletePanel(ActionEvent event) throws CloneNotSupportedException {
        lowerPanelController.deletePanel();
        event.consume();
    }

    @FXML private void overwritePanel(ActionEvent event) throws CloneNotSupportedException, IOException, URISyntaxException {
        lowerPanelController.overwritePanel();
        event.consume();
    }

    @FXML private void keyPressed(KeyEvent event) throws CloneNotSupportedException, IOException, URISyntaxException {
        lowerPanelController.keyPressed(event);
        event.consume();
    }

    @FXML private void createXML(ActionEvent event) {
        saveComicController.createXML();
        event.consume();
    }

    @FXML private void createHTML(ActionEvent event) throws IOException {
        saveComicController.createHTML();
        event.consume();
    }

    @FXML private void loadXML(ActionEvent event) throws CloneNotSupportedException {
        loadComicController.loadXML();
        event.consume();
    }

    // Methods that call the other controllers
    CharacterController getCharacterController(){ return characterController; }
    ComicController getComicController(){ return comicController; }
    MidScrollPaneController getMidScrollPaneController() { return midScrollPaneController; }
    ButtonController getButtonController() { return buttonController; }
    ColourController getColourController() { return colourController; }
    LowerPanelController getLowerPanelController(){ return lowerPanelController; }
}
