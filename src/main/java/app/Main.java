package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Stage mainStage = new Stage();
        mainStage.setMaximized(true);
        mainPage(mainStage);
        mainStage.setMinHeight(192.0);
        mainStage.setMinWidth(256.0);
    }
    
    public void mainPage(Stage mainStage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/test.fxml"));

        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        currentPath += "/src/main/resources/images/buttons/icon.png";
        currentPath = "file:" + currentPath;

        mainStage.getIcons().add(new Image(currentPath));
        Scene mainScene = new Scene(root);
        mainStage.setTitle("Excelsior");
        mainStage.setScene(mainScene);
        mainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
