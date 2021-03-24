package app;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    ImageView isSelected; //Global variable to track which section of the panel is currently selected

    @FXML
    Region selectedBorder = null; //Global variable to track which border is currently selected

    @FXML
    Button rotateCharacter;

    @FXML
    private ImageView bottomLeftIV;

    @FXML
    private ImageView bottomRightIV;

    @FXML
    private Region bottomLeftBorder;

    @FXML
    private Region bottomRightBorder;

    @FXML
    private void insertCharacterLeft(ActionEvent event) {
        event.consume();
        loadImageLeft();
        clickLeft();
    }

    @FXML
    private void insertCharacterRight(ActionEvent event) {
        event.consume();
        loadImageRight();
        clickRight();
    }

    @FXML
    public void loadImageLeft() {
        Image image = new Image(selectImage());
        bottomLeftIV.setImage(image);
    }

    @FXML
    public void clickLeft(){
        bottomLeftIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { //Event handler for bottom left image
            isSelected = bottomLeftIV;
            rotateCharacter.setDisable(false);  //Enable rotate function
            setBorder(bottomLeftBorder);
            event.consume();
        });
    }

    @FXML
    public void loadImageRight() {
        Image image = new Image(selectImage());
        bottomRightIV.setImage(image);
        bottomRightIV.setScaleX(-1);
    }

    @FXML
    public void clickRight(){
        bottomRightIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { //Event handler for bottom right image
            isSelected = bottomRightIV;
            rotateCharacter.setDisable(false);  //Enable rotate function
            setBorder(bottomRightBorder);
            event.consume();
        });
    }

    @FXML
    public String selectImage() {   //Method to get absolute path of desired image selected by the user
       String imagePath = "";

        FileChooser chooser = new FileChooser();
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        currentPath += "/src/main/resources/images/characters";
        chooser.setInitialDirectory(new File(currentPath));

        imagePath = chooser.showOpenDialog(new Stage()).toString();

       return "file:" + imagePath;
    }

    @FXML
    public void rotate(){
        isSelected.setScaleX(isSelected.getScaleX() * -1);
    }

    @FXML
    private void setBorder(Region newBorder) {
        if(selectedBorder != null){
            selectedBorder.setVisible(false);
        }
        selectedBorder = newBorder;
        selectedBorder.setVisible(true);
    }
}
