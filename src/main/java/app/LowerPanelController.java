package app;

import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import static javafx.geometry.Pos.CENTER;

public class LowerPanelController {

    private Controller mainController;

    public LowerPanelController(Controller TC){
        mainController = TC;
    }

    private int loadedPanelIndex = -1;
    LinkedList<Comic> comicPanelList = new LinkedList<>();

    private int selectedPanelIndex;

    private Region selectedPanelRegion;

    private Region loadedPanelRegion;

    @FXML
    void addToPanelList() throws CloneNotSupportedException {  //Method called when the save panel button is pressed

        if(comicPanelList.size() == 50){ //limit
            return;
        }

        mainController.comic.setComicImage(getPanelAsImage());

        if(mainController.selectedBorder != null) {
            mainController.selectedBorder.setVisible(true);
        }

        Comic comicClone = (Comic) mainController.comic.clone();
        if(loadedPanelIndex != -1){
            comicPanelList.set(loadedPanelIndex, (Comic) mainController.comic.clone());
            loadedPanelIndex = -1;
        }
        else {
            comicPanelList.add(comicClone);
        }

        loadBottomPanel();
        clearComic();
        selectedPanelIndex = -1;
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

    private void loadBottomPanel(){ //Loads the stored comics into the bottom panel

        for(int panelImage=0; panelImage < comicPanelList.size(); panelImage++) {

            ImageView image = new ImageView(comicPanelList.get(panelImage).getComicImage());

            Region region = new Region();
            region.setStyle("-fx-border-opacity: 1");
            int finalPanelImage = panelImage;
            region.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if(selectedPanelRegion != null && selectedPanelRegion != loadedPanelRegion){
                    selectedPanelRegion.setStyle("-fx-border-opacity: 1");
                }
                selectedPanelIndex = finalPanelImage;
                selectedPanelRegion = region;
                if(selectedPanelRegion != loadedPanelRegion) {
                    selectedPanelRegion.setStyle("-fx-border-color: #2a52be; -fx-border-width: 5px; -fx-border-opacity: 0");
                }
                event.consume();
            });

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
                if(loadedPanelRegion != null) {
                    loadedPanelRegion.setStyle("-fx-border-opacity: 1");
                    loadedPanelRegion = null;
                }
                importPanel();
            }
        }
        else if(event.getCode().equals(KeyCode.S)){ //Saves the comic as a panel
            if(mainController.comic != null) {
                loadedPanelRegion = null;
                addToPanelList();
            }
        }
    }

    private void deletePanel(){ //Deletes selected panel
        mainController.bottomGridPane.getChildren().clear();
        comicPanelList.remove(selectedPanelIndex);
        if(selectedPanelIndex == loadedPanelIndex){
            loadedPanelIndex = -1;
        }
        loadBottomPanel();
    }

    private void importPanel() throws CloneNotSupportedException { //Imports selected panel to the main comic panel
        clearComic();
        drawPanel();
        loadedPanelIndex = selectedPanelIndex;
        selectedPanelRegion.setStyle("-fx-border-color: #eb7134; -fx-border-width: 5px");
        loadedPanelRegion = selectedPanelRegion;
    }

    private void clearComic(){ //Removes all elements from a comic
        if(mainController.comic.getLeftCharacter() != null){
            mainController.bottomLeftIV.setImage(null);
            mainController.comic.setLeftCharacter(null);
            mainController.centreLeft.setImage(null);
            mainController.comic.setCentreLeft(null);
            mainController.leftTextField.clear();
            mainController.leftTextField.setVisible(false);
        }
        if (mainController.comic.getRightCharacter() != null){
            mainController.bottomRightIV.setImage(null);
            mainController.comic.setRightCharacter(null);
            mainController.centreRight.setImage(null);
            mainController.comic.setCentreRight(null);
            mainController.rightTextField.clear();
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

    private void drawPanel() throws CloneNotSupportedException { //Updates the main comic with a previously saved comic from the bottom pane
        Comic panelComic = (Comic) comicPanelList.get(selectedPanelIndex).clone();

        if(panelComic.getLeftCharacter() != null) {
            redrawLeftCharacter(panelComic.getLeftCharacter());
        }
        if(panelComic.getRightCharacter() != null) {
            redrawRightCharacter(panelComic.getRightCharacter());
        }

        if(panelComic.getCentreLeft() != null){
            redrawBubbles(panelComic.getCentreLeft(), 0);
        }

        if(panelComic.getCentreRight() != null){
            redrawBubbles(panelComic.getCentreRight(), 1);
        }

        if(panelComic.getBackground() != null) {
            mainController.comic.setBackground(panelComic.getBackground());
            mainController.background.setImage(mainController.comic.getBackground().getImage());
        }

        mainController.comic.setLeftText(panelComic.getLeftText());
        mainController.leftTextField.setText(panelComic.getLeftText());

        mainController.comic.setRightText(panelComic.getRightText());
        mainController.rightTextField.setText(panelComic.getRightText());

        mainController.comic.setTopText(panelComic.getTopText());
        mainController.topText.setText(panelComic.getTopText());

        mainController.comic.setBottomText(panelComic.getBottomText());
        mainController.bottomText.setText(panelComic.getBottomText());

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

    private void redrawBubbles(ImageView imageView, int side) { //Method that inserts the thought/speech bubble into the correct section of the comic
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
        if(mainController.leftTextField.getText().trim().equals("")){
            mainController.leftTextField.clear();
            mainController.centreLeft.setImage(null);
            mainController.leftTextField.setVisible(false);
        }
        if(mainController.rightTextField.getText().trim().equals("")){
            mainController.rightTextField.clear();
            mainController.centreRight.setImage(null);
            mainController.rightTextField.setVisible(false);
        }
        if(mainController.topText.getText().trim().equals("")){
            mainController.topText.setVisible(false);
        }
        if(mainController.bottomText.getText().trim().equals("")){
            mainController.bottomText.setVisible(false);
        }
    }

    @FXML
    void createXML() throws IOException {

        TextInputDialog fileNameInput = new TextInputDialog();
        fileNameInput.setTitle("Name your comic");
        fileNameInput.setHeaderText("");
        fileNameInput.setContentText("Enter a file name:");
        fileNameInput.showAndWait();
        String fileName = fileNameInput.getEditor().getText();

        System.out.println(fileName);
        if(!fileName.equals("")){
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.showSaveDialog(null);
            String filePath = chooser.getSelectedFile().toString();

            File file = new File(filePath + "\\" + fileName + ".xml");
            file.createNewFile();
        }

        for(int node = 0; node < comicPanelList.size(); node++){


            comicPanelList.get(node);
        }
    }
}
