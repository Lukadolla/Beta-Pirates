package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;

import static javafx.geometry.Pos.CENTER;

public class utilityController {

    private testMainController testController;

   public void injectMain(testMainController testController){
       this.testController = testController;
   }

    private buttonController buttonController;

    public void injectButtonController(buttonController buttonController){
        this.buttonController = buttonController;
    }

    Comic comic = new Comic();
    ImageLists imageLists = new ImageLists();
    List<Image> characterImages;  //List of character Images
    List<Image> backgroundImages;  //List of backgrounds

    @FXML
    GridPane charactersGridPane;

    @FXML
    GridPane backgroundGridPane;

    @FXML
    ImageView bottomLeftIV; //Bottom left ImageView where the character is inserted

    @FXML
    ImageView bottomRightIV; //Bottom right ImageView where the character is inserted

    @FXML
    ScrollPane midScrollPane;

    @FXML
    AnchorPane characterMenuAnchorPane;

    ImageView comicCharacterSelection; // Track character selection independent of comic selection

    @FXML
    Region selectedBorder = null; //Global variable to track which border is currently selected

    @FXML
    Region bottomLeftBorder;

    @FXML
    Region bottomRightBorder;

    @FXML
    ImageView comicSelection; //Global variable to track which section of the panel is currently selected

    @FXML
    ImageView centreRight;

    @FXML
    ImageView centreLeft;

    @FXML
    TextField leftTextField;

    @FXML
    TextField rightTextField;

    @FXML
    private ImageView background;  //ImageView containing the background image


    void swapMiddlePanel(GridPane currentPane, GridPane newPane) {  //Method that hides the old content of the middle panel and displays new content
        currentPane.setDisable(true);
        currentPane.setVisible(false);

        newPane.setDisable(false);
        newPane.setVisible(true);
    }

    @FXML
    void loadCharacterImages() throws IOException {  //Method that loads character images from the CharacterList class and displays them in the middle panel

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

    public void setCharactersMenuSelectionId(int charactersMenuSelectionId) {  //Sets the character selected variable
    }

    public void insertCharacter(int selectedImage) {
        if (comicCharacterSelection == bottomLeftIV) {
            insertLeftCharacter(selectedImage);
        } else if (comicCharacterSelection == bottomRightIV) {
            insertRightCharacter(selectedImage);
        }
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
            buttonController.switchButtonState(true);
            event.consume();
        });
        buttonController.switchButtonState(true);
        removeHairAA();
        clearBackground();
        removeAAPixels();
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
            buttonController.switchButtonState(true);
            event.consume();
        });

        buttonController.switchButtonState(true);
        removeHairAA();
        clearBackground();
        removeAAPixels();
    }

    @FXML
    void setBorder(Region newBorder) { //Method that sets the border on a selected component
        if(selectedBorder != null){
            selectedBorder.setVisible(false);
        }
        selectedBorder = newBorder;
        selectedBorder.setVisible(true);
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

    private void removeHairAA(){  //Method to remove hair anti-aliasing by calling the setMale and setFemale methode which handle the AA
        setMale();
        removeAAPixels();
        setFemale();
    }

    void insertBubble(ImageView imageView) { //Method that inserts the thought/speech bubble into the correct section of the comic
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


    private void removeAAPixels() {  //Method to remove anti-aliasing pixels in the image
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

    boolean compareColours(Color colour_1, Color colour_2){  //Method to compare 2 colours to see if they are within the same rough colour range

        return (Math.abs(colour_1.getRed() - colour_2.getRed()) < 0.01) && (Math.abs(colour_1.getGreen() - colour_2.getGreen()) < 0.01) && (Math.abs(colour_1.getBlue() - colour_2.getBlue()) < 0.01) && (colour_1.getOpacity() == colour_2.getOpacity());
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

    Color changeTone(Color colour){ //Method to change the tone slightly of the colour being passed in. This is done to ease colour changing and removing female hair
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

    @FXML
    void loadBackgroundImages() throws IOException {  //Method that loads character images from the CharacterList class and displays them in the middle panel

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

    private void insertBackground(int selectedImage){  //Method that places the background into the comic panel
        comic.setBackground(new ImageView(backgroundImages.get(selectedImage)));
        background.setImage(comic.getBackground().getImage());
    }

}
