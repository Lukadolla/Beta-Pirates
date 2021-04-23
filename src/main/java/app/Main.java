package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {  //Sets up necessary stages and scenes
        Stage mainStage = new Stage();
        Stage helpStage = new Stage();
        mainStage.setMaximized(true);
        helpStage.setHeight(500.0);
        helpStage.setWidth(800.0);
        mainStage.setMinHeight(192.0);
        mainStage.setMinWidth(256.0);
        mainPage(mainStage);
        helpPage(helpStage);

    }

    public void mainPage(Stage mainStage) throws IOException{ //Loads the main page of the application and displays it
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/test.fxml"));

        URL url = getClass().getResource("/images/buttons/icon.png");
        String currentPath = url.toString();

        mainStage.getIcons().add(new Image(currentPath));
        Scene mainScene = new Scene(root);
        mainScene.getRoot().requestFocus();
        mainStage.setTitle("Excelsior");
        mainStage.setScene(mainScene);
        mainStage.show();
    }

    public void helpPage(Stage helpStage) throws IOException{ //Loads the help window and displays it
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/help.fxml"));
        Scene helpScene = new Scene(root);
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
