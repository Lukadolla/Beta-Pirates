package app.controllers;

import app.ImageLists;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import static javafx.geometry.Pos.CENTER;

public class MidScrollPaneController {

    ImageLists imageLists = new ImageLists();
    List<Image> characterImages;  //List of character Images
    List<Image> backgroundImages;  //List of backgrounds
    private Controller mainController;

    public MidScrollPaneController(Controller controller) {
        this.mainController = controller;
    }

    void loadCharacterImages() throws IOException, URISyntaxException {  //Method that loads character images from the CharacterList class and displays them in the middle panel

        imageLists.getImageFiles("/images/characters");

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
                mainController.getButtonController().insertCharacter(characterImages.get(finalSelectedImage), imageLists.getCharacterImageNames().get(finalSelectedImage));
                event.consume();
            });

            AnchorPane characterAnchorPane = setUpMiddlePanel(selectedImage, imageview, region, "characterHbox");

            mainController.charactersGridPane.add(characterAnchorPane,columnIndex,rowIndex);

            columnIndex = (columnIndex == 0) ? 1 : 0;
        }
    }

    public void addCharacterPane() throws IOException, URISyntaxException {  //Method to display the characters in the middle panel

        mainController.getSizeScale(mainController.charactersGridPane);
        mainController.resize();

        swapMiddlePanel(mainController.backgroundGridPane, mainController.charactersGridPane);
        mainController.midScrollPane.setVvalue(0);
        mainController.midScrollPane.setVisible(true);

        if(characterImages == null){
            loadCharacterImages();
        }
    }

    public void addBackgroundPane() throws IOException, URISyntaxException { //Method to display the backgrounds in the middle panel

        mainController.getSizeScale(mainController.backgroundGridPane);
        mainController.resize();

        swapMiddlePanel(mainController.charactersGridPane, mainController.backgroundGridPane);
        mainController.midScrollPane.setVvalue(0);
        mainController.midScrollPane.setVisible(true);

        if(backgroundImages == null){
            loadBackgroundImages();
        }
    }

    void swapMiddlePanel(GridPane currentPane, GridPane newPane) {  //Method that hides the old content of the middle panel and displays new content
        currentPane.setDisable(true);
        currentPane.setVisible(false);

        newPane.setDisable(false);
        newPane.setVisible(true);
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

    void loadBackgroundImages() throws IOException, URISyntaxException {  //Method that loads character images from the CharacterList class and displays them in the middle panel

        imageLists.getImageFiles("/images/backgrounds");

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
                mainController.getComicController().insertBackground(backgroundImages.get(finalSelectedImage));
                mainController.comic.setChosenBackground(imageLists.getBackgroundImageNames().get(finalSelectedImage));
                event.consume();
            });

            AnchorPane characterAnchorPane = setUpMiddlePanel(selectedImage, imageview, region, "backgroundHbox");

            mainController.backgroundGridPane.add(characterAnchorPane,columnIndex,rowIndex);

            columnIndex = (columnIndex == 0) ? 1 : 0;
        }
    }
}
