package sample;

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
    }
    
    public void mainPage(Stage mainStage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        Scene mainScene = new Scene(root);
        mainStage.setTitle("Excelsior");
        mainStage.setScene(mainScene);
        mainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
