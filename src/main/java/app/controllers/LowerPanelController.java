package app.controllers;

import app.Comic;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.io.IOException;
import java.net.URISyntaxException;
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

        mainController.getTextGraphicController().checkTextForGraphic();

        mainController.SaveXMLMenu.setDisable(false);
        mainController.SaveHTMLMenu.setDisable(false);
        mainController.SaveGIFMenu.setDisable(false);

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
        mainController.getComicController().clearComic();
    }

    private Image getPanelAsImage(){ //Converts the comic pane into an image to be returned
        if(mainController.selectedBorder != null) {
            mainController.selectedBorder.setVisible(false);
        }
        saveText();
        mainController.getComicController().removeNullText();
        return mainController.backgroundImageScale.snapshot(null, null);
    }

    private void saveText(){ //Saves the input text to the comic
        mainController.comic.setLeftText(mainController.leftTextField.getText());
        mainController.comic.setRightText(mainController.rightTextField.getText());
        mainController.comic.setTopText(mainController.topText.getText());
        mainController.comic.setBottomText(mainController.bottomText.getText());

    }

    void loadBottomPanel(){ //Loads the stored comics into the bottom panel

        for(int panelImage=0; panelImage < comicPanelList.size(); panelImage++) {

            ImageView image = new ImageView(comicPanelList.get(panelImage).getComicImage());

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

            HBox comicImageHbox = new HBox(image);
            comicImageHbox.setId("comicImageHbox" + panelImage);
            comicImageHbox.setAlignment(CENTER);

            AnchorPane panelAnchorPane = new AnchorPane(comicImageHbox, region);
            AnchorPane.setLeftAnchor(region, 0.0);
            AnchorPane.setRightAnchor(region, 0.0);
            AnchorPane.setTopAnchor(region, 0.0);
            AnchorPane.setBottomAnchor(region, 0.0);

            image.fitWidthProperty().bind(panelAnchorPane.widthProperty());
            image.fitHeightProperty().bind(panelAnchorPane.heightProperty());
            image.setManaged(false);
            image.setPickOnBounds(true);
            image.setVisible(true);
            image.setPreserveRatio(false);

            mainController.bottomGridPane.add(panelAnchorPane, panelImage, 0);
        }
    }


    void overwritePanel() throws CloneNotSupportedException, IOException, URISyntaxException {
        mainController.getTextGraphicController().checkTextForGraphic();
        overwrite = true;
        addToPanelList();
    }

    void deletePanel(){ //Deletes selected panel
        if(selectedPanelIndex == -1){
            return;
        }
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
                mainController.SaveGIFMenu.setDisable(true);
                mainController.getButtonController().lowerButtonState(false);
            }

            loadBottomPanel();
        }
    }

    void importPanel() throws CloneNotSupportedException { //Imports selected panel to the main comic panel
        mainController.getComicController().clearComic();
        mainController.getComicController().drawComic(comicPanelList.get(selectedPanelIndex));
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
        if(topText.getText().trim().equals("")){
            topText.setVisible(false);
        }

        if(bottomText.getText().trim().equals("")){
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
            bottomLeftImage.setScaleX(comicPanelList.get(panelImage).getLeftCharacter().getFacing());
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
            bottomRightImage.setScaleX(comicPanelList.get(panelImage).getRightCharacter().getFacing());
            AnchorPane bottomRightAnchor = new AnchorPane(bottomRightImage);

            bottomRightImage.fitWidthProperty().bind(bottomRightAnchor.widthProperty());
            bottomRightImage.fitHeightProperty().bind(bottomRightAnchor.heightProperty());
            bottomRightImage.setManaged(false);
            bottomRightImage.setPickOnBounds(true);

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
