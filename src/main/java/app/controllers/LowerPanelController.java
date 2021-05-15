package app.controllers;

import app.Comic;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.util.*;

import static javafx.geometry.Pos.CENTER;

public class LowerPanelController {

    private Controller mainController;

    public LowerPanelController(Controller controller){
        mainController = controller;
    }

    public boolean overwrite = false;
    LinkedList<Comic> comicPanelList = new LinkedList<>();

    int selectedPanelIndex = -1; //Global variable to track which panel in the bottom section is selected.

    private Region selectedPanelRegion;

    @FXML
    void addToPanelList() throws CloneNotSupportedException {  //Method called when the save panel button is pressed

        if(comicPanelList.size() == 50){ //limit of panels that can be saved
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


    void overwritePanel() throws CloneNotSupportedException {  //Method called when a user overwrites the saved panel
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

    void importPanel() { //Imports selected panel to the main comic panel
        mainController.getComicController().clearComic();
        mainController.getComicController().drawComic(comicPanelList.get(selectedPanelIndex));
    }

    void swapPanels(int finalPanelImage, Region region){  //Method to swap 2 panels in the bottom section
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
}
