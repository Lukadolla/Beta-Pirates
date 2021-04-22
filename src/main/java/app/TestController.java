package app;

import java.net.URL;
import java.util.LinkedList;
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

public class TestController {

    private Comic comic = new Comic();

    private LinkedList<Comic> comicPanelList = new LinkedList<>();


    private ImageView comicCharacterSelection; // Track character selection independent of comic selection

    @FXML
    ImageView comicSelection; //Global variable to track which section of the panel is currently selected

    @FXML
    private ImageView background;  //ImageView containing the background image

    @FXML
    Region selectedBorder = null; //Global variable to track which border is currently selected

    @FXML
    private ImageView bottomLeftIV; //Bottom left ImageView where the character is inserted

    @FXML
    private ImageView bottomRightIV; //Bottom right ImageView where the character is inserted

    @FXML
    private Region bottomLeftBorder;

    @FXML
    private Region bottomRightBorder;

    @FXML
    private AnchorPane characterMenuAnchorPane;

    @FXML
    private AnchorPane backgroundImageScale;

    @FXML
    private AnchorPane scrollPaneAnchorPane;

    @FXML
    private GridPane buttonsGridPane;

    @FXML
    protected GridPane charactersGridPane;

    @FXML
    private GridPane comicImageGridPane;

    @FXML
    protected GridPane backgroundGridPane;

    @FXML
    private ColorPicker bodyColourPicker;

    @FXML
    private ColorPicker hairColourPicker;

    @FXML
    protected ScrollPane midScrollPane;

    @FXML
    private ImageView centreRight;

    @FXML
    private ImageView centreLeft;

    @FXML
    private Button rotateCharacterButton;

    @FXML
    private Button changeGenderButton;

    @FXML
    private Button speechBubbleButton;

    @FXML
    private Button thoughtBubbleButton;

    @FXML
    private Button deleteCharacterButton;

    @FXML
    private Button backgroundButton;

    @FXML
    public Button addCharacterLeftButton;

    @FXML
    private Button addCharacterRightButton;

    @FXML
    private TextField leftTextField;

    @FXML
    private TextField rightTextField;

    @FXML
    private ImageView leftTextImageview;

    @FXML
    private ImageView rightTextImageview;

    @FXML
    private ImageView PanelIV1;
    
    @FXML
    private HBox leftHbox;

    @FXML
    private MenuItem savePanel;

    @FXML
    private HBox testHBox;

    @FXML
    private HBox testHBox2;

    @FXML
    private GridPane bottomGridPane;

    private MidScrollPaneController midScrollPaneController = new MidScrollPaneController(this);

    public void setCharactersMenuSelectionId(int charactersMenuSelectionId) {  //Sets the character selected variable
    }

    @FXML
    private void resize(){  //Method to resize the middle anchor pane
        characterMenuAnchorPane.setPrefHeight(buttonsGridPane.getHeight() * 4);
        scrollPaneAnchorPane.setPrefWidth(buttonsGridPane.getHeight() * 30);

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

    @FXML
    private void addCharacterRight(ActionEvent event) throws IOException { //Method called when button is pressed to add a character into the right panel

        midScrollPaneController.addCharacterPane();

        if(bottomRightIV.getImage() == null){
            switchButtonState(false);
        } else {
            switchButtonState(true);
        }

        setBorder(bottomRightBorder);
        comicSelection = bottomRightIV;
        comicCharacterSelection = bottomRightIV;
        event.consume();
    }

    public void insertCharacter(Image selectedImage) {
        if (comicCharacterSelection == bottomLeftIV) {
            insertLeftCharacter(selectedImage);
        } else if (comicCharacterSelection == bottomRightIV) {
            insertRightCharacter(selectedImage);
        }
    }

    @FXML
    public void insertRightCharacter(Image selectedImage){  //Method that inserts a character into the right panel and adds character data to the Comic class
        comic.setRightCharacter(new Character(selectedImage, 1));
        comic.setSelected(comic.getRightCharacter());
        bottomRightIV.setImage(comic.getRightCharacter().getImage());
        bottomRightIV.setScaleX(-1);
        bottomRightIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            setBorder(bottomRightBorder);
            comicSelection = bottomRightIV;
            comicCharacterSelection = bottomRightIV;
            comic.setSelected(comic.getRightCharacter());
            switchButtonState(true);
            event.consume();
        });

        switchButtonState(true);
        removeHairAA();
        clearBackground();
        removeAAPixels();
    }

    @FXML
    private void addCharacterLeft(ActionEvent event) throws IOException { //Method called when button is pressed to add a character into the left panel

        midScrollPaneController.addCharacterPane();

        if(bottomLeftIV.getImage() == null){
            switchButtonState(false);
        } else {
            switchButtonState(true);
        }

        setBorder(bottomLeftBorder);
        comicSelection = bottomLeftIV;
        comicCharacterSelection = bottomLeftIV;
        event.consume();
    }

    @FXML
    public void insertLeftCharacter(Image selectedImage){ //Method that inserts a character into the left panel and adds character data to the Comic class
        comic.setLeftCharacter(new Character(selectedImage, 1));
        comic.setSelected(comic.getLeftCharacter());
        bottomLeftIV.setImage(comic.getLeftCharacter().getImage());
        bottomLeftIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            setBorder(bottomLeftBorder);
            comicSelection = bottomLeftIV;
            comicCharacterSelection = bottomLeftIV;
            comic.setSelected(comic.getLeftCharacter());
            switchButtonState(true);
            event.consume();
        });
        switchButtonState(true);
        removeHairAA();
        clearBackground();
        removeAAPixels();
    }

    private void switchButtonState(boolean areEnabled){
        rotateCharacterButton.setDisable(!areEnabled);
        changeGenderButton.setDisable(!areEnabled);
        bodyColourPicker.setDisable(!areEnabled);
        hairColourPicker.setDisable(!areEnabled);
        speechBubbleButton.setDisable(!areEnabled);
        thoughtBubbleButton.setDisable(!areEnabled);
        deleteCharacterButton.setDisable(!areEnabled);
    }


    @FXML
    public void rotate(){ //Method to rotate a character
        comic.getSelected().changeFacing();
        comicSelection.setScaleX(comicSelection.getScaleX() * -1);
    }

    @FXML
    private void setBorder(Region newBorder) { //Method that sets the border on a selected component
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
        helpStage.setHeight(500.0);
        helpStage.setWidth(800.0);
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.show();
    }

    @FXML
    public Color getChosenBodyColour(){  //Method that fetches the body colour chosen by the user from the ColourPicker
        Color chosenColour = checkColour(bodyColourPicker.getValue());
        return chosenColour;
    }

    @FXML
    public void changeSkinColour() {  //Method called when user wants to change the character's skin colour
        Image image = comicSelection.getImage();
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
                if(compareColours(colour, comic.getSelected().getSkinColour())){
                    colour = getChosenBodyColour();
                    changed = true;
                }
                else if(comic.getSelected().getGender().equals("male") && compareColours(colour, comic
                        .getSelected().getLipColour())){
                    colour = changeTone(getChosenBodyColour());
                    lipColour = colour;
                }
                PW.setColor(i, j, colour);
            }
        }
        if(changed)
        {
            comic.getSelected().setLipColour(lipColour);
            comic.getSelected().setSkinColour(getChosenBodyColour());
        }
        comic.getSelected().setImage(wImage);
        comicSelection.setImage(wImage);
    }

    @FXML
    public Color getChosenHairColour(){ //Method that fetches the hair colour chosen by the user from the ColourPicker
        Color chosenColour = checkColour(hairColourPicker.getValue());
        return chosenColour;
    }

    @FXML
    public void changeHairColour() { //Method called when user wants to change the character's hair colour
        Image image = comicSelection.getImage();
        int imageHeight = (int)image.getHeight();
        int imageWidth = (int)image.getWidth();
        boolean changed = false;

        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();
        Color colour;
        Color maleHairColour = new Color(0, 0, 0, 0);

        if(comic.getSelected().getGender().equals("male"))  //Changing male hair only if the character is male
        {
            for(int i = 0; i < imageWidth; i++){
                for(int j = 0; j < imageHeight; j++){
                    colour = PR.getColor(i, j);
                    if(compareColours(colour, comic.getSelected().getMaleHairColour())){
                        colour = getChosenHairColour();
                        changed = true;
                    }
                    PW.setColor(i, j, colour);
                }
            }
            if(changed)
            {
                comic.getSelected().setMaleHairColour(getChosenHairColour());
                comic.getSelected().setFemaleHairColour(changeTone(getChosenHairColour()));
            }
        }

        if(comic.getSelected().getGender().equals("female")) //Changing both male and female hair only if the character is female
        {
            for(int i = 0; i < imageWidth; i++){
                for(int j = 0; j < imageHeight; j++){
                    colour = PR.getColor(i, j);
                    if(compareColours(colour, comic.getSelected().getFemaleHairColour())){
                        colour = getChosenHairColour();
                    }

                    else if(compareColours(colour, comic.getSelected().getMaleHairColour()))
                    {
                        colour = changeTone(getChosenHairColour());
                        maleHairColour = colour;
                    }

                    PW.setColor(i, j, colour);
                }
            }
            comic.getSelected().setFemaleHairColour(getChosenHairColour());
            comic.getSelected().setMaleHairColour(maleHairColour);

        }
        comic.getSelected().setImage(wImage);
        comicSelection.setImage(wImage);
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

    private void setMale() {  //Method that sets the current character to be male (removing female hair + lipstick)
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
                    colour = changeTone(comic.getSelected().getSkinColour());
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

    private void setFemale() { //Method that sets the current character to be female (adding female hair + lipstick)
        Image image = comic.getSelected().getImage();
        int imageHeight = (int)image.getHeight();
        int imageWidth = (int)image.getWidth();
        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();

        for(int i = 0; i < imageWidth; i++){
            for(int j = 0; j < imageHeight; j++){
                Color colour = PR.getColor(i, j);
                if(compareColours(colour, comic.getSelected().getLipColour())){
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

        if(compareColours(colour, character.getMaleHairColour()) || colour.equals(Color.web("fbff5e")) || colour.equals(Color.web("ffff66")) || colour.equals(Color.web("ffff4d"))){
            isItHair = false;
        }
        if(compareColours(colour, character.getFemaleHairColour())){
            isItHair = true;
        }
        return isItHair;
    }

    private boolean isBows(Color colour){  //Method to check if the colour passed and colours close to it are the bow colour
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

    private Color changeTone(Color colour){ //Method to change the tone slightly of the colour being passed in. This is done to ease colour changing and removing female hair
        Double[] colourList = new Double[3];
        colourList[0] = colour.getRed();
        colourList[1] = colour.getGreen();
        colourList[2] = colour.getBlue();

        if(colourList[0] <= 0.98 && colourList[0] != 0){
            colourList[0] += 0.02;
        }
        else if(colourList[1] <= 0.98 && colourList[1] != 0){
            colourList[1] += 0.02;
        }
        else{
            if(colourList[2] <= 0.98){
                colourList[2] += 0.02;
            }
            else{
                colourList[2] -= 0.02;
            }
        }

        return new Color(colourList[0], colourList[1], colourList[2], colour.getOpacity());
    }

    private boolean compareColours(Color colour_1, Color colour_2){  //Method to compare 2 colours to see if they are within the same rough colour range

        return (Math.abs(colour_1.getRed() - colour_2.getRed()) < 0.01) && (Math.abs(colour_1.getGreen() - colour_2.getGreen()) < 0.01) && (Math.abs(colour_1.getBlue() - colour_2.getBlue()) < 0.01) && (colour_1.getOpacity() == colour_2.getOpacity());
    }

    private void removeHairAA(){  //Method to remove hair anti-aliasing by calling the setMale and setFemale methode which handle the AA
        setMale();
        removeAAPixels();
        setFemale();
    }

    @FXML
    private void addSpeechBubble(){ //Method called when user presses the speech bubble button

        URL url = getClass().getResource("/images/buttons/speech.png");
        String currentPath = url.toString();

        ImageView imageView = new ImageView(currentPath);

        insertBubble(imageView);
    }


    @FXML
    private void addThoughtBubble(){ //Method called when user presses the thought bubble button

        URL url = getClass().getResource("/images/buttons/thought.PNG");
        String currentPath = url.toString();

        ImageView imageView = new ImageView(currentPath);

        insertBubble(imageView);
    }

    private void insertBubble(ImageView imageView) { //Method that inserts the thought/speech bubble into the correct section of the comic
        if (comic.getSelected().equals(comic.getLeftCharacter())) {
            comic.setCentreLeft(imageView);
            comic.getCentreLeft().setScaleX(-1);
            centreLeft.setImage(comic.getCentreLeft().getImage());
            centreLeft.setScaleX(comic.getCentreLeft().getScaleX());
            leftTextField.setDisable(false);
            leftTextField.setVisible(true);
        }
        else{
            comic.setCentreRight(imageView);
            centreRight.setImage(comic.getCentreRight().getImage());
            rightTextField.setDisable(false);
            rightTextField.setVisible(true);
        }
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
        switchButtonState(false);
    }

    @FXML
    private void insertTextGraphic(){
        TextGraphic textGraphic = new TextGraphic("the quick brown fox jumped over the lazy dog");
        leftTextImageview.setImage(textGraphic.getImage());
        leftHbox.setVisible(true);
    }

    private void clearBackground() {  //Method that removes the white background in the character images and makes it transparent instead
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

    protected void insertBackground(Image selectedImage){  //Method that places the background into the comic panel
        System.out.println("hello");
        comic.setBackground(new ImageView(selectedImage));
        background.setImage(comic.getBackground().getImage());
    }

    private void removeAAPixels() {  //Mthod to remove anti-aliasing pixels in the image
        Image image = comic.getSelected().getImage();
        int imageHeight = (int) image.getHeight();
        int imageWidth = (int) image.getWidth();
        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();

        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                Color colour = PR.getColor(i, j);
                if (!colour.equals(Color.web("000000")) && !compareColours(colour, comic.getSelected().getFemaleHairColour()) && !isBows(colour) && !compareColours(colour, comic.getSelected().getMaleHairColour()) && colour.getOpacity() > 0.02) {

                    Color leftPixel;
                    Color rightPixel;
                    Color bottomPixel;
                    Color topPixel;

                    if(i == 0){
                        leftPixel = PR.getColor(i, j);
                    }
                    else {
                        leftPixel = PR.getColor(i - 1, j);
                    }

                    if(i == imageWidth-1){
                        rightPixel = PR.getColor(i, j);
                    }
                    else{
                        rightPixel = PR.getColor(i + 1, j);
                    }

                    if(j == 0){
                        bottomPixel = PR.getColor(i, j);
                    }
                    else{
                        bottomPixel = PR.getColor(i, j - 1);
                    }

                    if(j == imageHeight-1){
                        topPixel = PR.getColor(i, j);
                    }
                    else {
                        topPixel = PR.getColor(i, j + 1);
                    }

                    if (leftPixel.getOpacity() > 0.04 && leftPixel.getOpacity() < 0.06 || leftPixel.getOpacity() < 0.2) {
                        colour = new Color(1, 1, 1, 0.05);
                    } else if (rightPixel.getOpacity() > 0.04 && rightPixel.getOpacity() < 0.06 || rightPixel.getOpacity() < 0.2) {
                        colour = new Color(1, 1, 1, 0.05);
                    } else if (topPixel.getOpacity() > 0.04 && topPixel.getOpacity() < 0.06 || topPixel.getOpacity() < 0.2) {
                        colour = new Color(1, 1, 1, 0.05);
                    } else if (bottomPixel.getOpacity() > 0.04 && bottomPixel.getOpacity() < 0.06 || bottomPixel.getOpacity() < 0.2) {
                        colour = new Color(1, 1, 1, 0.05);
                    }
                }
                PW.setColor(i, j, colour);
            }
        }

        comicSelection.setImage(wImage);
        comic.getSelected().setImage(wImage);
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