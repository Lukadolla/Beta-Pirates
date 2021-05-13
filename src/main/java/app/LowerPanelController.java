package app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static javafx.geometry.Pos.CENTER;

public class LowerPanelController {

    private Controller mainController;

    public LowerPanelController(Controller TC){
        mainController = TC;
    }

    public boolean overwrite = false;
    LinkedList<Comic> comicPanelList = new LinkedList<>();

    private int selectedPanelIndex = -1;

    private Region selectedPanelRegion;

    @FXML
    void addToPanelList() throws CloneNotSupportedException, IOException, URISyntaxException {  //Method called when the save panel button is pressed

        if(comicPanelList.size() == 50){ //limit
            return;
        }

        mainController.SaveXMLMenu.setDisable(false);
        mainController.SaveHTMLMenu.setDisable(false);

        mainController.comic.setComicImage(getPanelAsImage());

        if(mainController.selectedBorder != null) {
            mainController.selectedBorder.setVisible(true);
        }

        Comic comicClone = (Comic) mainController.comic.clone();
        if(overwrite){
            comicPanelList.set(selectedPanelIndex, (Comic) mainController.comic.clone());
            overwrite = false;
        }
        else {
            comicPanelList.add(comicClone);
        }

        loadBottomPanel();
        clearComic();
    }

    private Image getPanelAsImage(){ //Converts the comic pane into an image to be returned
        if(mainController.selectedBorder != null) {
            mainController.selectedBorder.setVisible(false);
        }
        saveText();
        removeNullText();
        return mainController.backgroundImageScale.snapshot(null, null);
    }

    private void saveText(){ //Saves the input text to the comic
        mainController.comic.setLeftText(mainController.leftTextField.getText());
        mainController.comic.setRightText(mainController.rightTextField.getText());
        mainController.comic.setTopText(mainController.topText.getText());
        mainController.comic.setBottomText(mainController.bottomText.getText());

    }

    void loadBottomPanel(){ //Loads the stored comics into the bottom panel

        mainController.bottomGridPane.getChildren().clear();

        for(int panelImage=0; panelImage < comicPanelList.size(); panelImage++) {

            Region region = new Region();
            region.setStyle("-fx-border-opacity: 1");
            int finalPanelImage = panelImage;
            region.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                if(event.isPrimaryButtonDown()) {
                    if (selectedPanelIndex != -1) {
                        selectedPanelRegion.setStyle("-fx-border-opacity: 1");
                    }
                    mainController.getButtonController().lowerButtonState(true);
                    selectedPanelIndex = finalPanelImage;
                    selectedPanelRegion = region;
                    selectedPanelRegion.setStyle("-fx-border-color: #2a52be; -fx-border-width: 5px; -fx-border-opacity: 0");
                }
                else if(event.isSecondaryButtonDown()){
                    swapPanels(finalPanelImage, region);
                }

                event.consume();
            });

            if(panelImage == selectedPanelIndex){
                region.setStyle("-fx-border-color: #2a52be; -fx-border-width: 5px; -fx-border-opacity: 0");
                selectedPanelRegion = region;
            }

            createPanelComic(panelImage, region);

//            ImageView image = new ImageView(comicPanelList.get(panelImage).getComicImage());
        }
    }

    @FXML
    void keyPressed(KeyEvent event) throws CloneNotSupportedException, IOException, URISyntaxException { //Handles key press events

        if(event.getCode().equals(KeyCode.DELETE)){ //Deletes the selected panel
            if(!comicPanelList.isEmpty()){
                deletePanel();
            }
        }
        else if(event.getCode().equals(KeyCode.L)){ //Loads the selected panel
            if(!comicPanelList.isEmpty()){
                importPanel();
            }
        }
        else if(event.getCode().equals(KeyCode.S)){ //Saves the comic as a panel
            checkTextForGraphic();
            addToPanelList();
        }
        else if(event.getCode().equals(KeyCode.O)){ //Saves the comic as a panel
            overwritePanel();
        }
        else if(event.getCode().equals(KeyCode.ENTER)) { //Saves the comic as a panel
            checkTextForGraphic();
        }
    }

    void checkTextForGraphic(){
        if (mainController.comic != null) {
            if (mainController.leftTextField.getText() != null && !mainController.leftTextField.getText().equals("")) {
                mainController.getComicController()
                        .insertLeftTextGraphic(mainController.leftTextField.getText());
                mainController.leftTextRegion.setVisible(true);
            }
            if (mainController.rightTextField.getText() != null && !mainController.rightTextField.getText().equals("")) {
                mainController.getComicController()
                        .insertRightTextGraphic(mainController.rightTextField.getText());
                mainController.rightTextRegion.setVisible(true);
            }
        }
    }

    void overwritePanel() throws CloneNotSupportedException, IOException, URISyntaxException {
        checkTextForGraphic();
        overwrite = true;
        addToPanelList();
    }

    void deletePanel(){ //Deletes selected panel

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete the panel?");
        alert.setContentText("");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;
        }
        else {
            mainController.bottomGridPane.getChildren().clear();
            comicPanelList.remove(selectedPanelIndex);
            selectedPanelRegion.setStyle("-fx-border-opacity: 0");
            selectedPanelIndex = -1;
            mainController.getButtonController().lowerButtonState(false);

            if (comicPanelList.size() == 0) {
                mainController.SaveXMLMenu.setDisable(true);
                mainController.SaveHTMLMenu.setDisable(true);
                mainController.getButtonController().lowerButtonState(false);
            }

            loadBottomPanel();
        }
    }

    void importPanel() throws CloneNotSupportedException { //Imports selected panel to the main comic panel
        clearComic();
        drawComic(comicPanelList.get(selectedPanelIndex));
    }

    void clearComic(){ //Removes all elements from a comic
        if(mainController.comic.getLeftCharacter() != null){
            mainController.bottomLeftIV.setImage(null);
            mainController.comic.setLeftCharacter(null);
            mainController.centreLeft.setImage(null);
            mainController.comic.setCentreLeft(null);
            mainController.leftTextField.clear();
            mainController.leftTextImageview.setImage(null);
            mainController.leftTextRegion.setVisible(false);
            mainController.leftTextField.setVisible(false);
        }
        if (mainController.comic.getRightCharacter() != null){
            mainController.bottomRightIV.setImage(null);
            mainController.comic.setRightCharacter(null);
            mainController.centreRight.setImage(null);
            mainController.comic.setCentreRight(null);
            mainController.rightTextField.clear();
            mainController.rightTextImageview.setImage(null);
            mainController.rightTextRegion.setVisible(false);
            mainController.rightTextField.setVisible(false);
        }

        if(mainController.selectedBorder != null) {
            mainController.selectedBorder.setVisible(false);
        }

        mainController.comic.setSelected(null);
        mainController.comicSelection = null;
        mainController.comicCharacterSelection = null;
        mainController.getButtonController().switchButtonState(false);


        URL url = getClass().getResource("/images/backgrounds/default.png");
        String currentPath = url.toString();
        ImageView backgroundImage = new ImageView(currentPath);
        mainController.background.setImage(backgroundImage.getImage());
        mainController.comic.setBackground(backgroundImage);

        mainController.topText.setVisible(true);
        mainController.topText.setText("");
        mainController.bottomText.setVisible(true);
        mainController.bottomText.setText("");
    }

    void drawComic(Comic comicCopy) throws CloneNotSupportedException { //Updates the main comic with a previously saved comic from the bottom pane

        if(comicCopy.getLeftCharacter() != null) {
            redrawLeftCharacter(comicCopy.getLeftCharacter());
        }
        if(comicCopy.getRightCharacter() != null) {
            redrawRightCharacter(comicCopy.getRightCharacter());
        }

        if(comicCopy.getCentreLeft() != null){
            redrawBubbles(comicCopy.getCentreLeft(), 0);
        }

        if(comicCopy.getCentreRight() != null){
            redrawBubbles(comicCopy.getCentreRight(), 1);
        }

        if(comicCopy.getBackground() != null) {
            mainController.comic.setBackground(comicCopy.getBackground());
            mainController.background.setImage(mainController.comic.getBackground().getImage());
        }

        mainController.comic.setLeftText(comicCopy.getLeftText());
        mainController.leftTextField.setText(comicCopy.getLeftText());

        mainController.comic.setRightText(comicCopy.getRightText());
        mainController.rightTextField.setText(comicCopy.getRightText());

        checkTextForGraphic();

        mainController.comic.setTopText(comicCopy.getTopText());
        mainController.topText.setText(comicCopy.getTopText());

        mainController.comic.setBottomText(comicCopy.getBottomText());
        mainController.bottomText.setText(comicCopy.getBottomText());

        mainController.topText.setVisible(true);
        mainController.bottomText.setVisible(true);
    }

    public void redrawRightCharacter(Character rightCharacter){  //Method that inserts a character into the right panel and adds character data to the Comic class
        mainController.comic.setRightCharacter(rightCharacter);
        mainController.bottomRightIV.setImage(mainController.comic.getRightCharacter().getImage());
        mainController.bottomRightIV.setScaleX(rightCharacter.getFacing());
        mainController.bottomRightIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mainController.setBorder(mainController.bottomRightBorder);
            mainController.comicSelection = mainController.bottomRightIV;
            mainController.comicCharacterSelection = mainController.bottomRightIV;
            mainController.comic.setSelected(mainController.comic.getRightCharacter());
            mainController.getButtonController().switchButtonState(true);
            event.consume();
        });
    }

    public void redrawLeftCharacter(Character leftCharacter){  //Method that inserts a character into the right panel and adds character data to the Comic class
        mainController.comic.setLeftCharacter(leftCharacter);
        mainController.bottomLeftIV.setImage(mainController.comic.getLeftCharacter().getImage());
        mainController.bottomRightIV.setScaleX(leftCharacter.getFacing());
        mainController.bottomLeftIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mainController.setBorder(mainController.bottomLeftBorder);
            mainController.comicSelection = mainController.bottomLeftIV;
            mainController.comicCharacterSelection = mainController.bottomLeftIV;
            mainController.comic.setSelected(mainController.comic.getLeftCharacter());
            mainController.getButtonController().switchButtonState(true);
            event.consume();
        });
    }

    public void redrawBubbles(ImageView imageView, int side) { //Method that inserts the thought/speech bubble into the correct section of the comic
        if (side == 0) {
            mainController.comic.setCentreLeft(imageView);
            mainController.comic.getCentreLeft().setScaleX(-1);
            mainController.centreLeft.setImage(mainController.comic.getCentreLeft().getImage());
            mainController.centreLeft.setScaleX(mainController.comic.getCentreLeft().getScaleX());
            mainController.leftTextField.setDisable(false);
            mainController.leftTextField.setVisible(true);
        }
        else{
            mainController.comic.setCentreRight(imageView);
            mainController.centreRight.setImage(mainController.comic.getCentreRight().getImage());
            mainController.rightTextField.setDisable(false);
            mainController.rightTextField.setVisible(true);
        }
    }

    private void removeNullText(){ //Hides empty text fields
        if(mainController.leftTextField.getText() != null && mainController.leftTextField.getText().trim().equals("")) {
            mainController.leftTextField.clear();
            mainController.centreLeft.setImage(null);
            mainController.leftTextField.setVisible(false);
        }
        if(mainController.rightTextField.getText() !=null && mainController.rightTextField.getText().trim().equals("")){
            mainController.rightTextField.clear();
            mainController.centreRight.setImage(null);
            mainController.rightTextField.setVisible(false);
        }
        if(mainController.topText.getText() != null && mainController.topText.getText().trim().equals("")){
            mainController.topText.setVisible(false);
        }
        if(mainController.bottomText.getText() != null && mainController.bottomText.getText().trim().equals("")){
            mainController.bottomText.setVisible(false);
        }
    }

    void swapPanels(int finalPanelImage, Region region){
        Comic tempComic;
        if (selectedPanelIndex == -1 || selectedPanelIndex == finalPanelImage) {
            return;
        }

        try {
            tempComic = (Comic) comicPanelList.get(selectedPanelIndex).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return;
        }

        try {
            comicPanelList.set(selectedPanelIndex, (Comic) comicPanelList.get(finalPanelImage).clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return;
        }

        try {
            comicPanelList.set(finalPanelImage, (Comic) tempComic.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return;
        }

        selectedPanelRegion.setStyle("-fx-border-opacity: 0");
        selectedPanelRegion = region;
        selectedPanelIndex = finalPanelImage;
        loadBottomPanel();
    }

    private void createPanelComic(int panelImage, Region region){
        //Setup
        ImageView background = new ImageView();

        try{
            background.setImage(comicPanelList.get(panelImage).getBackground().getImage());
        } catch(NullPointerException e){
        }

        HBox hbox = new HBox(background);
        TextField topText = new TextField(comicPanelList.get(panelImage).getTopText());
        TextField bottomText = new TextField(comicPanelList.get(panelImage).getBottomText());
        GridPane gridpane = new GridPane();
        AnchorPane container = new AnchorPane(hbox, topText, bottomText, gridpane, region);

        //TextFields
        if(topText.getText().equals("")){
            topText.setVisible(false);
        }

        if(bottomText.getText().equals("")){
            bottomText.setVisible(false);
        }

        //Region
        AnchorPane.setLeftAnchor(region, 0.0);
        AnchorPane.setRightAnchor(region, 0.0);
        AnchorPane.setTopAnchor(region, 0.0);
        AnchorPane.setBottomAnchor(region, 0.0);

        //GridPane
        AnchorPane.setLeftAnchor(gridpane, 5.0);
        AnchorPane.setRightAnchor(gridpane, 25.0);
        AnchorPane.setTopAnchor(gridpane, 36.0);
        AnchorPane.setBottomAnchor(gridpane, 35.0);

        ColumnConstraints column = new ColumnConstraints();
        column.setHgrow(Priority.SOMETIMES);
        column.setPercentWidth(42.5);
        gridpane.getColumnConstraints().add(column);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(Priority.SOMETIMES);
        column2.setPercentWidth(15.0);
        gridpane.getColumnConstraints().add(column2);

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHgrow(Priority.SOMETIMES);
        column3.setPercentWidth(42.5);
        gridpane.getColumnConstraints().add(column3);

        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.SOMETIMES);
        row.setPercentHeight(30.0);
        gridpane.getRowConstraints().add(row);

        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.SOMETIMES);
        row2.setPercentHeight(30.0);
        gridpane.getRowConstraints().add(row2);

        RowConstraints row3 = new RowConstraints();
        row3.setVgrow(Priority.SOMETIMES);
        row3.setPercentHeight(40.0);
        gridpane.getRowConstraints().add(row3);

        //Inside GridPane

        //Left Text
        try{
            //Top Left
            ImageView leftTextImage = new ImageView(comicPanelList.get(panelImage).getLeftGraphic().getImage());
            Region leftTextRegion = new Region();
            AnchorPane leftTextAnchor = new AnchorPane(leftTextRegion, leftTextImage);

            leftTextRegion.setStyle("-fx-border-radius: 20; -fx-border-color: #000000; -fx-border-width: 2; -fx-background-radius: 20; -fx-background-color: #ffffff");
            AnchorPane.setLeftAnchor(leftTextRegion, 0.0);
            AnchorPane.setRightAnchor(leftTextRegion, 0.0);
            AnchorPane.setTopAnchor(leftTextRegion, 0.0);
            AnchorPane.setBottomAnchor(leftTextRegion, 0.0);

            leftTextImage.fitWidthProperty().bind(leftTextAnchor.widthProperty());
            leftTextImage.fitHeightProperty().bind(leftTextAnchor.heightProperty());
            leftTextImage.setManaged(false);
            leftTextImage.setPickOnBounds(true);

            gridpane.add(leftTextAnchor, 0, 0);

            //Left Centre
            ImageView centreLeftImage = new ImageView(comicPanelList.get(panelImage).getCentreLeft().getImage());
            AnchorPane centreLeftScale = new AnchorPane(centreLeftImage);
            AnchorPane centreLeftAnchor = new AnchorPane(centreLeftScale);

            centreLeftImage.fitWidthProperty().bind(centreLeftScale.widthProperty());
            centreLeftImage.fitHeightProperty().bind(centreLeftScale.heightProperty());
            centreLeftImage.setScaleX(-1);
            centreLeftImage.setManaged(false);
            centreLeftImage.setPickOnBounds(true);

            AnchorPane.setBottomAnchor(centreLeftScale, 10.0);
            AnchorPane.setLeftAnchor(centreLeftScale, 26.0);
            AnchorPane.setRightAnchor(centreLeftScale, 30.0);
            AnchorPane.setTopAnchor(centreLeftScale, 19.0);

            gridpane.add(centreLeftAnchor, 0, 1);
        } catch(NullPointerException ignored){
        }

        //Right Text
        try{
            //Top Right
            ImageView rightTextImage = new ImageView(comicPanelList.get(panelImage).getRightGraphic().getImage());
            Region rightTextRegion = new Region();
            AnchorPane rightTextAnchor = new AnchorPane(rightTextRegion, rightTextImage);

            rightTextRegion.setStyle("-fx-border-radius: 20; -fx-border-color: #000000; -fx-border-width: 2; -fx-background-radius: 20; -fx-background-color: #ffffff");
            AnchorPane.setLeftAnchor(rightTextRegion, 0.0);
            AnchorPane.setRightAnchor(rightTextRegion, 0.0);
            AnchorPane.setTopAnchor(rightTextRegion, 0.0);
            AnchorPane.setBottomAnchor(rightTextRegion, 0.0);

            rightTextImage.fitWidthProperty().bind(rightTextAnchor.widthProperty());
            rightTextImage.fitHeightProperty().bind(rightTextAnchor.heightProperty());
            rightTextImage.setManaged(false);
            rightTextImage.setPickOnBounds(true);

            gridpane.add(rightTextAnchor, 2, 0);

            //Centre Right
            ImageView centreRightImage = new ImageView(comicPanelList.get(panelImage).getCentreRight().getImage());
            AnchorPane centreRightScale = new AnchorPane(centreRightImage);
            AnchorPane centreRightAnchor = new AnchorPane(centreRightScale);

            centreRightImage.fitWidthProperty().bind(centreRightScale.widthProperty());
            centreRightImage.fitHeightProperty().bind(centreRightScale.heightProperty());
            centreRightImage.setManaged(false);
            centreRightImage.setPickOnBounds(true);

            AnchorPane.setBottomAnchor(centreRightScale, 10.0);
            AnchorPane.setLeftAnchor(centreRightScale, 26.0);
            AnchorPane.setRightAnchor(centreRightScale, 30.0);
            AnchorPane.setTopAnchor(centreRightScale, 19.0);

            gridpane.add(centreRightAnchor, 2, 1);
        } catch(NullPointerException ignored){
        }

        //Bottom Left
        try{
            ImageView bottomLeftImage = new ImageView(comicPanelList.get(panelImage).getLeftCharacter().getImage());
            AnchorPane bottomLeftAnchor = new AnchorPane(bottomLeftImage);

            bottomLeftImage.fitWidthProperty().bind(bottomLeftAnchor.widthProperty());
            bottomLeftImage.fitHeightProperty().bind(bottomLeftAnchor.heightProperty());
            bottomLeftImage.setManaged(false);
            bottomLeftImage.setPickOnBounds(true);

            gridpane.add(bottomLeftAnchor, 0, 2);
        } catch(NullPointerException ignored){
        }

        //Bottom Right
        try{
            ImageView bottomRightImage = new ImageView(comicPanelList.get(panelImage).getRightCharacter().getImage());
            AnchorPane bottomRightAnchor = new AnchorPane(bottomRightImage);

            bottomRightImage.fitWidthProperty().bind(bottomRightAnchor.widthProperty());
            bottomRightImage.fitHeightProperty().bind(bottomRightAnchor.heightProperty());
            bottomRightImage.setManaged(false);
            bottomRightImage.setPickOnBounds(true);
            bottomRightImage.setScaleX(-1);

            gridpane.add(bottomRightAnchor, 2, 2);
        } catch(NullPointerException ignored){
        }

        //END OF GRID

        //Top/Bottom Text
        topText.setDisable(true);
        topText.setOpacity(1);
        bottomText.setDisable(true);
        bottomText.setOpacity(1);
        topText.setAlignment(CENTER);
        bottomText.setAlignment(CENTER);
        AnchorPane.setLeftAnchor(topText, 0.0);
        AnchorPane.setLeftAnchor(bottomText, 0.0);
        AnchorPane.setRightAnchor(topText, 0.0);
        AnchorPane.setRightAnchor(bottomText, 0.0);
        AnchorPane.setTopAnchor(topText, 0.0);
        AnchorPane.setBottomAnchor(bottomText, 0.0);

        //HBox
        hbox.setFillHeight(true);
        hbox.setAlignment(CENTER);
        AnchorPane.setLeftAnchor(hbox, 0.0);
        AnchorPane.setRightAnchor(hbox, 0.0);
        AnchorPane.setTopAnchor(hbox, 0.0);
        AnchorPane.setBottomAnchor(hbox, 0.0);

        //Background Image
        background.fitWidthProperty().bind(container.widthProperty());
        background.fitHeightProperty().bind(container.heightProperty());
        background.setManaged(false);
        background.setPickOnBounds(true);

        mainController.bottomGridPane.add(container, panelImage, 0);
    }
}
