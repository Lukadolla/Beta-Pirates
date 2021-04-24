package app;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonController {

  private TestController mainController;


  public ButtonController(TestController controller) { this.mainController = controller; }

  @FXML
  void enableToolTips(){ //Method to enable tool tips when mouse is hovered over the buttons
    mainController.addCharacterLeftButton.setTooltip(
        new Tooltip("Add a character to the left side")
    );

    mainController.addCharacterRightButton.setTooltip(
        new Tooltip("Add a character to the right side")
    );

    mainController.speechBubbleButton.setTooltip(
        new Tooltip("Add speech bubble")
    );

    mainController.thoughtBubbleButton.setTooltip(
        new Tooltip("Add thought bubble")
    );

    mainController.rotateCharacterButton.setTooltip(
        new Tooltip("Rotate character")
    );

    mainController.changeGenderButton.setTooltip(
        new Tooltip("Change gender of character")
    );

    mainController.deleteCharacterButton.setTooltip(
        new Tooltip("Delete character")
    );

    mainController.backgroundButton.setTooltip(
        new Tooltip("Add background")
    );

    mainController.hairColourPicker.setTooltip(
        new Tooltip("Change hair colour")
    );

    mainController.bodyColourPicker.setTooltip(
        new Tooltip("Change body colour")
    );
  }

  void addCharacterLeft() throws IOException { //Method called when button is pressed to add a character into the left panel

    mainController.getMidScrollPaneController().addCharacterPane();

    switchButtonState(mainController.bottomLeftIV.getImage() != null);

    mainController.setBorder(mainController.bottomLeftBorder);
    mainController.comicSelection = mainController.bottomLeftIV;
    mainController.comicCharacterSelection = mainController.bottomLeftIV;
    if(mainController.comic.getLeftCharacter() != null) {
      mainController.comic.setSelected(mainController.comic.getLeftCharacter());
    }
  }

  void addCharacterRight() throws IOException { //Method called when button is pressed to add a character into the right panel

    mainController.getMidScrollPaneController().addCharacterPane();

    switchButtonState(mainController.bottomRightIV.getImage() != null);

    mainController.setBorder(mainController.bottomRightBorder);
    mainController.comicSelection = mainController.bottomRightIV;
    mainController.comicCharacterSelection = mainController.bottomRightIV;
    if(mainController.comic.getRightCharacter() != null) {
    mainController.comic.setSelected(mainController.comic.getRightCharacter());
    }
  }


  public void insertCharacter(Image selectedImage) {
    if (mainController.comicCharacterSelection == mainController.bottomLeftIV) {
      mainController.getComicController().insertLeftCharacter(selectedImage);
    } else if (mainController.comicCharacterSelection == mainController.bottomRightIV) {
      mainController.getComicController().insertRightCharacter(selectedImage);
    }
  }

  void switchButtonState(boolean areEnabled){ //Method that switches the state of the buttons (active/inactive)
    mainController.rotateCharacterButton.setDisable(!areEnabled);
    mainController.changeGenderButton.setDisable(!areEnabled);
    mainController.bodyColourPicker.setDisable(!areEnabled);
    mainController.hairColourPicker.setDisable(!areEnabled);
    mainController.speechBubbleButton.setDisable(!areEnabled);
    mainController.thoughtBubbleButton.setDisable(!areEnabled);
    mainController.deleteCharacterButton.setDisable(!areEnabled);
  }

  @FXML
  public void changeGender() { //Method called when user presses the change gender button

    if(mainController.comic.getSelected().getGender().equals("female")){
      mainController.getCharacterController().setMale();
    }
    else{
      mainController.getCharacterController().setFemale();
    }
  }

  @FXML
  void addSpeechBubble(){ //Method called when user presses the speech bubble button

    URL url = getClass().getResource("/images/buttons/speech.png");
    String currentPath = url.toString();

    ImageView imageView = new ImageView(currentPath);

    mainController.getComicController().insertBubble(imageView);
  }


  @FXML
  void addThoughtBubble(){ //Method called when user presses the thought bubble button

    URL url = getClass().getResource("/images/buttons/thought.PNG");
    String currentPath = url.toString();

    ImageView imageView = new ImageView(currentPath);

    mainController.getComicController().insertBubble(imageView);
  }

  @FXML
  void deleteCharacter() {  //Method called when the user presses the delete button which removes characters and text from the selected half of the comic
    if(mainController.comic.getSelected().equals(mainController.comic.getLeftCharacter())){
      mainController.bottomLeftIV.setImage(null);
      mainController.comic.setLeftCharacter(null);
      mainController.centreLeft.setImage(null);
      mainController.leftTextField.clear();
      mainController.leftTextField.setVisible(false);
    }
    else{
      mainController.bottomRightIV.setImage(null);
      mainController.comic.setRightCharacter(null);
      mainController.centreRight.setImage(null);
      mainController.rightTextField.clear();
      mainController.rightTextField.setVisible(false);
    }
    mainController.selectedBorder.setVisible(false);
    mainController.comic.setSelected(null);
    mainController.comicSelection = null;
    mainController.comicCharacterSelection = null;
    switchButtonState(false);
  }

}
