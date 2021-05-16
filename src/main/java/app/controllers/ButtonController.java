package app.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import app.Character;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonController {

  private Controller mainController;

  public ButtonController(Controller controller) { this.mainController = controller; }

  void addCharacterLeft() throws IOException, URISyntaxException { //Method called when button is pressed to add a character into the left panel
    mainController.getMidScrollPaneController().addCharacterPane();
    switchButtonState(mainController.bottomLeftIV.getImage() != null);
    mainController.setBorder(mainController.bottomLeftBorder);
    mainController.comicSelection = mainController.bottomLeftIV;
    mainController.comicCharacterSelection = mainController.bottomLeftIV;
    if(mainController.comic.getLeftCharacter() != null) {
      mainController.comic.setSelected(mainController.comic.getLeftCharacter());
    }
  }

  void addCharacterRight() throws IOException, URISyntaxException { //Method called when button is pressed to add a character into the right panel
    mainController.getMidScrollPaneController().addCharacterPane();
    switchButtonState(mainController.bottomRightIV.getImage() != null);
    mainController.setBorder(mainController.bottomRightBorder);
    mainController.comicSelection = mainController.bottomRightIV;
    mainController.comicCharacterSelection = mainController.bottomRightIV;
    if(mainController.comic.getRightCharacter() != null) {
    mainController.comic.setSelected(mainController.comic.getRightCharacter());
    }
  }

  public void insertCharacter(Image selectedImage, String image) { //Method that inserts a character image into the corresponding side if the panel
    Character c = new Character(selectedImage, image);
    if (mainController.comicCharacterSelection == mainController.bottomLeftIV) {
      mainController.getComicController().insertLeftCharacter(c);
    } else if (mainController.comicCharacterSelection == mainController.bottomRightIV) {
      c.setFacing(-1);
      mainController.getComicController().insertRightCharacter(c);
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

  public void changeGender() { //Method called when user presses the change gender button
    if(mainController.comic.getSelected().getGender().equals("female")){
      mainController.getCharacterController().setMale();
    }
    else{
      mainController.getCharacterController().setFemale();
    }
  }

  void addSpeechBubble(){ //Method called when user presses the speech bubble button
    URL url = getClass().getResource("/images/buttons/speech.png");
    String currentPath = url.toString();
    ImageView imageView = new ImageView(currentPath);
    mainController.comic.getSelected().setSpeech();
    mainController.getComicController().insertBubble(imageView);
  }

  void addThoughtBubble(){ //Method called when user presses the thought bubble button
    URL url = getClass().getResource("/images/buttons/thought.png");
    String currentPath = url.toString();
    ImageView imageView = new ImageView(currentPath);
    mainController.comic.getSelected().setThought();
    mainController.getComicController().insertBubble(imageView);
  }

  void deleteCharacter() {  //Method called when the user presses the delete button which removes characters and text from the selected half of the comic
    if(mainController.comic.getSelected().equals(mainController.comic.getLeftCharacter())){
      mainController.bottomLeftIV.setImage(null);
      mainController.comic.setLeftCharacter(null);
      mainController.centreLeft.setImage(null);
      if(mainController.comic.getCentreLeft() != null) {
        mainController.comic.getCentreLeft().setImage(null);
      }
      mainController.leftTextField.clear();
      mainController.leftTextField.setVisible(false);
      mainController.leftTextField.setDisable(true);
      mainController.leftTextImageview.setImage(null);
      mainController.leftTextRegion.setVisible(false);
      mainController.comic.setLeftGraphic(null);
      mainController.bottomLeftIV.setDisable(true);
    }
    else{
      mainController.bottomRightIV.setImage(null);
      mainController.comic.setRightCharacter(null);
      mainController.centreRight.setImage(null);
      if(mainController.comic.getCentreRight() != null) {
        mainController.comic.getCentreRight().setImage(null);
      }
      mainController.rightTextField.clear();
      mainController.rightTextField.setVisible(false);
      mainController.rightTextField.setDisable(true);
      mainController.rightTextImageview.setImage(null);
      mainController.rightTextRegion.setVisible(false);
      mainController.comic.setRightGraphic(null);
      mainController.bottomRightIV.setDisable(true);
    }
    mainController.selectedBorder.setVisible(false);
    mainController.comic.setSelected(null);
    mainController.comicSelection = null;
    mainController.comicCharacterSelection = null;
    switchButtonState(false);
  }

  void lowerButtonState(boolean areEnabled){  //Method to enable/disable the buttons in the bottom panel responsible for deleting, loading and replacing a panel
    mainController.deletePanelButton.setDisable(!areEnabled);
    mainController.loadPanelButton.setDisable(!areEnabled);
    mainController.replacePanelButton.setDisable(!areEnabled);
  }
}
