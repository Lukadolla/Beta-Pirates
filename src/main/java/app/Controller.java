package app;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ImageView bottomLeftIV;

    @FXML
    private ImageView bottomRightIV;

    @FXML
    private void insertCharacterLeft(ActionEvent event) throws IOException{
        event.consume();
        loadImageLeft();
    }

    @FXML
    private void insertCharacterRight(ActionEvent event) throws IOException{
        event.consume();
        loadImageRight();
    }

    @FXML
    public void loadImageLeft() throws IOException{
        Image image = new Image(selectImage());
        bottomLeftIV.setImage(image);
    }

    @FXML
    public void loadImageRight() throws IOException{
        Image image = new Image(selectImage());
        bottomRightIV.setImage(image);
        bottomRightIV.setScaleX(-1);
    }

    @FXML
    public String selectImage() throws IOException {
       String imagePath = "";

        FileChooser chooser = new FileChooser();
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        currentPath += "/src/main/resources/images/characters";
        chooser.setInitialDirectory(new File(currentPath));

        imagePath = chooser.showOpenDialog(new Stage()).toString();

       return "file:" + imagePath;
    }

}
