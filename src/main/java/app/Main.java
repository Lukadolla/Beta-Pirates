package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        Scene mainScene = new Scene(root);
        mainStage.setTitle("Excelsior");
        mainStage.setScene(mainScene);
        mainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
