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

public class TestController {

    private Comic comic = new Comic();
    private ImageLists imageLists = new ImageLists();
    private int charactersMenuSelectionId;
    private List<Image> characterImages;
    private List<Image> backgroundImages;


    private ImageView comicCharacterSelection; // Track character selection independent of comic selection

    @FXML
    private Region characterMenuBorder; //Global variable to track where the border is within the character menu

    @FXML
    ImageView comicSelection; //Global variable to track which section of the panel is currently selected

    @FXML
    private ImageView background;

    @FXML
    Region selectedBorder = null; //Global variable to track which border is currently selected

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
    private AnchorPane characterMenuAnchorPane;

    @FXML
    private GridPane buttonsGridPane;

    @FXML
    private GridPane charactersGridPane;

    @FXML
    private GridPane backgroundGridPane;

    @FXML
    private ColorPicker bodyColourPicker;

    @FXML
    private ColorPicker hairColourPicker;

    @FXML
    private ScrollPane midScrollPane;

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
    private Button addCharacterLeftButton;

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

    public void setCharactersMenuSelectionId(int charactersMenuSelectionId) {  //Sets the character selected variable
        this.charactersMenuSelectionId = charactersMenuSelectionId;
    }

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

  @FXML
    private void loadCharacterImages() throws IOException {  //Method that loads character images from the CharacterList class and displays them in the middle panel

        imageLists.loadCharacterImagesList();

        this.characterImages = imageLists.getCharacterImages();

        int columnIndex = 0;

        for (int selectedImage = 0; selectedImage < characterImages.size(); selectedImage++) {
            int rowIndex = (selectedImage/2);
            ImageView imageview = new ImageView(characterImages.get(selectedImage));
            int finalSelectedImage = selectedImage;

            Region region = new Region();
            region.setVisible(true);
            region.setStyle("-fx-border-color: #bbc4c4");
            region.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                setCharactersMenuSelectionId(finalSelectedImage);
                insertCharacter(finalSelectedImage);
                event.consume();
            });

            AnchorPane characterAnchorPane = setUpMiddlePanel(selectedImage, imageview, region, "characterHbox");

            charactersGridPane.add(characterAnchorPane,columnIndex,rowIndex);

            columnIndex = (columnIndex == 0) ? 1 : 0;
        }
    }

    private AnchorPane setUpMiddlePanel(int selectedImage, ImageView imageview, Region region, String hBoxName) { //Method to set up structure of middle panel

        HBox characterHbox = new HBox(imageview);
        characterHbox.setId(hBoxName + selectedImage);
        characterHbox.setAlignment(CENTER);
        AnchorPane characterAnchorPane = new AnchorPane(characterHbox, region);
        AnchorPane.setLeftAnchor(region, 0.0);
        AnchorPane.setRightAnchor(region, 0.0);
        AnchorPane.setTopAnchor(region, 0.0);
        AnchorPane.setBottomAnchor(region, 0.0);
        imageview.fitWidthProperty().bind(characterAnchorPane.widthProperty());
        imageview.fitHeightProperty().bind(characterAnchorPane.heightProperty());
        imageview.setManaged(false);
        imageview.setPickOnBounds(true);
        imageview.setVisible(true);
        return characterAnchorPane;
    }

    @FXML
    private void addCharacterRight(ActionEvent event) throws IOException { //Method called when button is pressed to add a character into the right panel

        swapMiddlePanel(backgroundGridPane, charactersGridPane);

        if(bottomRightIV.getImage() == null){
            switchButtonState(false);
        }
        if(characterImages == null){
            midScrollPane.setVisible(true);
            loadCharacterImages();
        }

        midScrollPane.setVvalue(0);
        setBorder(bottomRightBorder);
        comicSelection = bottomRightIV;
        comicCharacterSelection = bottomRightIV;
        event.consume();
    }

    public void insertCharacter(int selectedImage) {
        if (comicCharacterSelection == bottomLeftIV) {
            insertLeftCharacter(selectedImage);
        } else insertRightCharacter(selectedImage);
    }

    @FXML
    public void insertRightCharacter(int selectedImage){  //Method that inserts a character into the right panel and adds character data to the Comic class
        comic.setRightCharacter(new Character(characterImages.get(selectedImage), 1));
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

        swapMiddlePanel(backgroundGridPane, charactersGridPane);

        if(bottomLeftIV.getImage() == null){
            switchButtonState(false);
        }

        if(characterImages == null){
            midScrollPane.setVisible(true);
            loadCharacterImages();
        }

        midScrollPane.setVvalue(0);
        setBorder(bottomLeftBorder);
        comicSelection = bottomLeftIV;
        comicCharacterSelection = bottomLeftIV;
        event.consume();
    }

    private void swapMiddlePanel(GridPane currentPane, GridPane newPane) {  //Method that hides the old content of the middle panel and displays new content
        currentPane.setDisable(true);
        currentPane.setVisible(false);

        newPane.setDisable(false);
        newPane.setVisible(true);
    }

    @FXML
    public void insertLeftCharacter(int selectedImage){ //Method that inserts a character into the left panel and adds character data to the Comic class
        comic.setLeftCharacter(new Character(characterImages.get(selectedImage), 1));
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

    private void switchButtonState(boolean desiredState){
        if(desiredState){
            rotateCharacterButton.setDisable(false);
            changeGenderButton.setDisable(false);
            bodyColourPicker.setDisable(false);
            hairColourPicker.setDisable(false);
            speechBubbleButton.setDisable(false);
            thoughtBubbleButton.setDisable(false);
            deleteCharacterButton.setDisable(false);
            backgroundButton.setDisable(false);
        }

        else{
            rotateCharacterButton.setDisable(true);
            changeGenderButton.setDisable(true);
            bodyColourPicker.setDisable(true);
            hairColourPicker.setDisable(true);
            speechBubbleButton.setDisable(true);
            thoughtBubbleButton.setDisable(true);
            deleteCharacterButton.setDisable(true);
            backgroundButton.setDisable(true);
        }
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
        return bodyColourPicker.getValue();
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
        return hairColourPicker.getValue();
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
                    colour = new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), 0.01);
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
                else if(colour.getOpacity() < 0.02){
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

        URL url = getClass().getResource("/images/buttons/thought.png");
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
        TextGraphic textGraphic = new TextGraphic("the quick brown fox \n jumped over the lazy hare");
        leftTextImageview.setImage(textGraphic.getImage());
    }

    private void clearBackground() {
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
                    colour = new Color(1, 1, 1, 0.05);
                }
                PW.setColor(i, j, colour);
            }
        }

        comicSelection.setImage(wImage);
        comic.getSelected().setImage(wImage);
    }

    @FXML
    private void changeBackground() throws IOException {
        swapMiddlePanel(charactersGridPane, backgroundGridPane);

        if(backgroundImages == null)
        {
            loadBackgroundImages();
        }

        midScrollPane.setVvalue(0);
    }

    @FXML
    private void loadBackgroundImages() throws IOException {  //Method that loads character images from the CharacterList class and displays them in the middle panel

        imageLists.loadBackgroundImagesList();

        this.backgroundImages = imageLists.getBackgroundImages();

        int columnIndex = 0;

        for (int selectedImage = 0; selectedImage < backgroundImages.size(); selectedImage++) {
            int rowIndex = (selectedImage/2);
            ImageView imageview = new ImageView(backgroundImages.get(selectedImage));
            int finalSelectedImage = selectedImage;

            Region region = new Region();
            region.setVisible(true);
            region.setStyle("-fx-border-color: #bbc4c4");

           region.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                insertBackground(finalSelectedImage);
                event.consume();
            });

            AnchorPane characterAnchorPane = setUpMiddlePanel(selectedImage, imageview, region, "backgroundHbox");

            backgroundGridPane.add(characterAnchorPane,columnIndex,rowIndex);

            columnIndex = (columnIndex == 0) ? 1 : 0;
        }
    }

    private void insertBackground(int selectedImage){
        comic.setBackground(new ImageView(backgroundImages.get(selectedImage)));
        background.setImage(comic.getBackground().getImage());
    }

    private void removeAAPixels() {
        Image image = comic.getSelected().getImage();
        int imageHeight = (int) image.getHeight();
        int imageWidth = (int) image.getWidth();
        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();

        for (int i = 1; i < imageWidth - 1; i++) {
            for (int j = 1; j < imageHeight - 1; j++) {
                Color colour = PR.getColor(i, j);
                if (!colour.equals(Color.web("000000")) && !compareColours(colour, comic.getSelected().getFemaleHairColour()) && !isBows(colour) && !compareColours(colour, comic.getSelected().getMaleHairColour()) && colour.getOpacity() > 0.02) {
                    Color leftPixel = PR.getColor(i - 1, j);
                    Color rightPixel = PR.getColor(i + 1, j);
                    Color topPixel = PR.getColor(i, j + 1);
                    Color bottomPixel = PR.getColor(i, j - 1);
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

}
