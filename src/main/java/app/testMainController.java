package app;

import javafx.fxml.FXML;

public class testMainController {

    @FXML private utilityController utController;
    @FXML private buttonController bController;

    @FXML private void initialize(){
        utController.injectMain(this);
        bController.injectMain(this);
    }
}
