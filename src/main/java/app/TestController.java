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

    private MidScrollPaneController midScrollPaneController = new MidScrollPaneController(this);

    private ComicController comicController = new ComicController(this);

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
    public void changeGender() { //Method called when user presses the change gender button

        if(comic.getSelected().getGender().equals("female")){
            setMale();
        }
        else{
            setFemale();
        }
    }

    void setMale() {  //Method that sets the current character to be male (removing female hair + lipstick)
        Image image = comic.getSelected().getImage();
        int imageHeight = (int)image.getHeight();
        int imageWidth = (int)image.getWidth();
        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();

        for(int i = 0; i < imageWidth; i++){
            for(int j = 0; j < imageHeight; j++){
                Color colour = PR.getColor(i, j);
                if(isLips(colour)){
                    colour = colourController.changeTone(comic.getSelected().getSkinColour());
                    comic.getSelected().setLipColour(colour);
                }
                else if(isHair(colour)){
                    colour = Color.web("fffffe");
                    colour = new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), 0);
                }
                else if(isBows(colour)){
                    colour = Color.web("feffff");
                    colour = new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), 0.02);
                }
                PW.setColor(i, j, colour);
            }
        }

        comic.getSelected().setGender("male");
        comicSelection.setImage(wImage);
        comic.getSelected().setImage(wImage);
    }

    void setFemale() { //Method that sets the current character to be female (adding female hair + lipstick)
        Image image = comic.getSelected().getImage();
        int imageHeight = (int)image.getHeight();
        int imageWidth = (int)image.getWidth();
        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();

        for(int i = 0; i < imageWidth; i++){
            for(int j = 0; j < imageHeight; j++){
                Color colour = PR.getColor(i, j);
                if(colourController.compareColours(colour, comic.getSelected().getLipColour())){
                    colour = Color.web("ff0000");
                }
                else if(colour.getOpacity() == 0){
                    colour = comic.getSelected().getFemaleHairColour();
                }
                else if(colour.getOpacity() < 0.03 && colour.getOpacity() > 0.015){
                    colour = Color.web("ecb4b5");
                }
                PW.setColor(i, j, colour);
            }
        }

        comic.getSelected().setGender("female");
        comicSelection.setImage(wImage);
        comic.getSelected().setImage(wImage);
    }

    private boolean isLips(Color color){ //Method to check if the colour passed and colours close to it are the lip colour

        boolean isItLips = false;

        if(color.getRed()==1 && color.getGreen()<0.6 && color.getBlue()>=color.getGreen()/2){
            isItLips = true;
        }
        else if(color.getGreen()==0 && color.getBlue()==0 && color.getRed()>0.8){
            isItLips = true;
        }

        if(color.equals(Color.web("ff6666")) || color.equals(Color.web("ff8080")) || color.equals(Color.web("ff9999")) || color.equals(Color.web("ffb3b3")) || color.equals(Color.web("ffcccc"))) {
            isItLips = false;
        }

        return isItLips;

    }

    private boolean isHair(Color colour){ //Method to check if the colour passed and colours close to it are the current hair colour
        Character character = comic.getSelected();
        boolean isItHair = false;
        if(colour.getRed()>=0.85 && colour.getGreen()>=0.85 && colour.getBlue()<0.3){
            isItHair = true;
        }
        else if(colour.getRed()>=0.95 && colour.getGreen()>=0.95 && colour.getBlue()<0.5){
            isItHair = true;
        }

        if(colourController.compareColours(colour, character.getMaleHairColour()) || colour.equals(Color.web("fbff5e")) || colour.equals(Color.web("ffff66")) || colour.equals(Color.web("ffff4d"))){
            isItHair = false;
        }
        if(colourController.compareColours(colour, character.getFemaleHairColour())){
            isItHair = true;
        }
        return isItHair;
    }

    boolean isBows(Color colour){  //Method to check if the colour passed and colours close to it are the bow colour
        Character character = comic.getSelected();
        boolean isItBows = false;
        if(colour.equals(Color.web("ecb4b5"))){
            isItBows = true;
        }
        else if(colour.getRed()>=0.9 && colour.getRed()<1 && colour.getGreen()-colour.getBlue()<0.02 && colour.getBlue()-colour.getGreen()<0.02 && colour.getGreen()<colour.getRed()){
            isItBows = true;
        }
        else if(colour.getRed()>=0.9 && colour.getRed()<1 && colour.getGreen()>=0.75 && colour.getGreen()<=0.85 && colour.getBlue()<=0.5 && colour.getBlue()>0.35){
            isItBows = true;
        }

        if(colour.equals(Color.web("e64d4d"))){
            isItBows=false;
        }
        return isItBows;
    }




    @FXML
    private void deleteCharacter() {  //Method called when the user presses the delete button which removes characters and text from the selected half of the comic
        if(comic.getSelected().equals(comic.getLeftCharacter())){
            bottomLeftIV.setImage(null);
            comic.setLeftCharacter(null);
            centreLeft.setImage(null);
            leftTextField.clear();
            leftTextField.setVisible(false);
        }
        else{
            bottomRightIV.setImage(null);
            comic.setRightCharacter(null);
            centreRight.setImage(null);
            rightTextField.clear();
            rightTextField.setVisible(false);
        }
        selectedBorder.setVisible(false);
        comic.setSelected(null);
        comicSelection = null;
        comicCharacterSelection = null;
        buttonController.switchButtonState(false);
    }

    void clearBackground() {  //Method that removes the white background in the character images and makes it transparent instead
        Image image = comic.getSelected().getImage();
        int imageHeight = (int)image.getHeight();
        int imageWidth = (int)image.getWidth();
        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();

        for(int i = 0; i < imageWidth; i++){
            for(int j = 0; j < imageHeight; j++){
                Color colour = PR.getColor(i, j);
                if(colour.equals(Color.web("ffffff"))){
                    colour = new Color(1, 1, 1, 0.01);
                }
                PW.setColor(i, j, colour);
            }
        }

        comicSelection.setImage(wImage);
        comic.getSelected().setImage(wImage);
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