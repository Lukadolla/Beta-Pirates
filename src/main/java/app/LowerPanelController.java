package app;

import javafx.fxml.FXML;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import java.net.URL;
import java.util.LinkedList;

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
    void addToPanelList() throws CloneNotSupportedException {  //Method called when the save panel button is pressed

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
        if(overwrite == true){
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

    @FXML
    void keyPressed(KeyEvent event) throws CloneNotSupportedException { //Handles key press events

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
            if(mainController.comic != null) {
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
                addToPanelList();
            }
        }
        else if(event.getCode().equals(KeyCode.O)){ //Saves the comic as a panel
            overwritePanel();
        }
        else if(event.getCode().equals(KeyCode.ENTER)) { //Saves the comic as a panel
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
    }

    void overwritePanel() throws CloneNotSupportedException {
        if(mainController.comic != null) {
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
            overwrite = true;
            addToPanelList();
        }
    }

    void deletePanel(){ //Deletes selected panel
        mainController.bottomGridPane.getChildren().clear();
        comicPanelList.remove(selectedPanelIndex);
        selectedPanelRegion.setStyle("-fx-border-opacity: 0");
        selectedPanelIndex = -1;
        mainController.getButtonController().lowerButtonState(false);

        if(comicPanelList.size() == 0){
            mainController.SaveXMLMenu.setDisable(true);
            mainController.SaveHTMLMenu.setDisable(true);
            mainController.getButtonController().lowerButtonState(false);
        }

        loadBottomPanel();
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
}
