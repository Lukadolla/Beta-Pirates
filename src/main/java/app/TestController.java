package app;

import java.util.LinkedList;

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

public class TestController {

    Comic comic = new Comic();
    private LinkedList<Comic> comicPanelList = new LinkedList<>();
    private MidScrollPaneController midScrollPaneController = new MidScrollPaneController(this);
    private ComicController comicController = new ComicController(this);
    private CharacterController characterController = new CharacterController(this);
    private ButtonController buttonController = new ButtonController(this);
    private ColourController colourController = new ColourController(this);

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
    private AnchorPane backgroundImageScale;

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
    private ImageView PanelIV1;
    
    @FXML
    HBox leftHbox;

    @FXML
    private MenuItem savePanel;

    @FXML
    private HBox testHBox;

    @FXML
    private HBox testHBox2;

    @FXML
    private GridPane bottomGridPane;

    private Help help;

    CharacterController getCharacterController(){ return characterController; }

    ComicController getComicController(){ return comicController; }

    @FXML
    private void resize(){  //Method to resize the middle anchor pane
        characterMenuAnchorPane.setPrefHeight(buttonsGridPane.getHeight() * 4);
        scrollPaneAnchorPane.setPrefWidth(buttonsGridPane.getHeight() * 30);
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
    private void changeGender(ActionEvent event) throws IOException {
        buttonController.changeGender();
        event.consume();
    }

    @FXML
    private void addSpeechBubble(ActionEvent event) throws IOException {
        buttonController.addSpeechBubble();
        event.consume();
    }

    @FXML
    private void addThoughtBubble(ActionEvent event) throws IOException {
        buttonController.addThoughtBubble();
        event.consume();
    }

    @FXML
    private void deleteCharacter(ActionEvent event) throws IOException {
        buttonController.deleteCharacter();
        event.consume();
    }

    @FXML
    private void enableToolTips(MouseEvent event) throws IOException {
        buttonController.enableToolTips();
        event.consume();
    }

    @FXML
    private void changeHairColour(ActionEvent event) throws IOException {
        colourController.changeHairColour();
        event.consume();
    }

    @FXML
    private void changeSkinColour(ActionEvent event) throws IOException {
        colourController.changeSkinColour();
        event.consume();
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
    private void changeBackground() throws IOException {  //Method called when change background button is pressed
        midScrollPaneController.addBackgroundPane();
    }

    public MidScrollPaneController getMidScrollPaneController() {
        return midScrollPaneController;
    }

    ButtonController getButtonController() {
        return buttonController;
    }

    ColourController getColourController() {
        return colourController;
    }

    @FXML
    private void addToPanelList(){  //Method called when the save panel button is pressed

        comic.setComicImage(getPanelAsImage());
        selectedBorder.setVisible(true);
        //PanelIV1.setImage(comic.getComicImage());

        comicPanelList.add(comic);
        loadBottomPanel();
    }

    private Image getPanelAsImage(){
        selectedBorder.setVisible(false);
        return backgroundImageScale.snapshot(null, null);
    }

    private void loadBottomPanel(){

        ImageView image = new ImageView(comicPanelList.getLast().getComicImage());

        HBox comicImageHbox = new HBox(image);
        comicImageHbox.setId("comicImageHbox" + comicPanelList.size());
        comicImageHbox.setAlignment(CENTER);
        AnchorPane panelAnchorPane = new AnchorPane(comicImageHbox);

        image.fitWidthProperty().bind(panelAnchorPane.widthProperty());
        image.fitHeightProperty().bind(panelAnchorPane.heightProperty());
        image.setManaged(false);
        image.setPickOnBounds(true);
        image.setVisible(true);
        image.setPreserveRatio(false);

        bottomGridPane.add(panelAnchorPane,comicPanelList.size()-1,0);

    }

}