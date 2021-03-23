package app;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.*;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ImageView bottomLeftIV;

    @FXML
    private void insertCharacterLeft(ActionEvent event) throws IOException{
        event.consume();
        loadImages();
    }

    @FXML
    public void loadImages() throws IOException{
        Image image = new Image(selectImage());
        bottomLeftIV.setImage(image);
    }

    @FXML
    public String selectImage() throws IOException {
       String output = "";
       Runtime.getRuntime().exec("explorer /select,../images/characters");


       return output;
    }
}
