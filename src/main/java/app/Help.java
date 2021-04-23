package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Help {

    @FXML
    public void help() throws IOException {  //Method called when user wants to display help menu
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/help.fxml"));
        Stage helpStage = new Stage();
        Scene helpScene = new Scene(root);
        helpStage.setHeight(500.0);
        helpStage.setWidth(800.0);
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.show();
    }
}
