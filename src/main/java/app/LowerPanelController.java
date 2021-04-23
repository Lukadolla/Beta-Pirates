package app;

import javafx.fxml.FXML;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.LinkedList;

import static javafx.geometry.Pos.CENTER;

public class LowerPanelController {

    private TestController mainController;

    public LowerPanelController(TestController TC){
        mainController = TC;
    }

    private int loadedPanelIndex = -1;
    private LinkedList<Comic> comicPanelList = new LinkedList<>();

    private int selectedPanelIndex;



        @FXML
        void addToPanelList() throws CloneNotSupportedException {  //Method called when the save panel button is pressed

            mainController.comic.setComicImage(getPanelAsImage());
            mainController.selectedBorder.setVisible(true);

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
        }

        private Image getPanelAsImage(){
            mainController.selectedBorder.setVisible(false);
            saveText();
            return mainController.backgroundImageScale.snapshot(null, null);
        }

        private void saveText(){
            mainController.comic.setLeftText(mainController.leftTextField.getText());
            mainController.comic.setRightText(mainController.rightTextField.getText());
            mainController.comic.setTopText(mainController.topText.getText());
            mainController.comic.setBottomText(mainController.bottomText.getText());

        }

        private void loadBottomPanel(){

            for(int panelImage=0; panelImage < comicPanelList.size(); panelImage++) {

                ImageView image = new ImageView(comicPanelList.get(panelImage).getComicImage());

                Region region = new Region();
                region.setVisible(true);
                region.setStyle("-fx-border-color: #2a52be");
                int finalPanelImage = panelImage;
                region.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    selectedPanelIndex = finalPanelImage;
                    region.setVisible(true);
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
        void keyPressed(KeyEvent event) throws CloneNotSupportedException {

            if(event.getCode().equals(KeyCode.DELETE)){
                if(!comicPanelList.isEmpty()){
                    deletePanel();
                }
            }
            else if(event.getCode().equals(KeyCode.L)){
                if(!comicPanelList.isEmpty()){
                    importPanel();
                }
            }
            else if(event.getCode().equals(KeyCode.S)){
                addToPanelList();
            }
        }

        private void deletePanel(){
            mainController.bottomGridPane.getChildren().clear();
            comicPanelList.remove(selectedPanelIndex);
            if(selectedPanelIndex == loadedPanelIndex){
                loadedPanelIndex = -1;
            }
            loadBottomPanel();
        }

        private void importPanel() throws CloneNotSupportedException {
            clearComic();
            drawPanel();
            loadedPanelIndex = selectedPanelIndex;
        }

        private void clearComic(){
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

            mainController.selectedBorder.setVisible(false);
            mainController.comic.setSelected(null);
            mainController.comicSelection = null;
            mainController.comicCharacterSelection = null;
            mainController.getButtonController().switchButtonState(false);


            URL url = getClass().getResource("/images/backgrounds/default.png");
            String currentPath = url.toString();
            ImageView backgroundImage = new ImageView(currentPath);
            mainController.background.setImage(backgroundImage.getImage());
            mainController.comic.setBackground(backgroundImage);

            mainController.topText.setText("");
            mainController.bottomText.setText("");
        }

        private void drawPanel() throws CloneNotSupportedException {
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


        }

        public void redrawRightCharacter(Character rightCharacter){  //Method that inserts a character into the right panel and adds character data to the Comic class
            mainController.comic.setRightCharacter(rightCharacter);
            mainController.bottomRightIV.setImage(mainController.comic.getRightCharacter().getImage());
            mainController.bottomRightIV.setScaleX(-1);
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





}
