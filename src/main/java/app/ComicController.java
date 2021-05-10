package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ComicController {

    Controller mainController;

    public ComicController(Controller mainController){
        this.mainController = mainController;
    }

    public void insertRightCharacter(Character character){  //Method that inserts a character into the right panel and adds character data to the Comic class
        mainController.comic.setRightCharacter(character);
        mainController.comic.setSelected(mainController.comic.getRightCharacter());
        mainController.comicSelection = mainController.bottomRightIV;
        mainController.comicCharacterSelection = mainController.bottomRightIV;
        mainController.bottomRightIV.setImage(mainController.comic.getRightCharacter().getImage());
        mainController.bottomRightIV.setScaleX(character.getFacing());
        mainController.bottomRightIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mainController.setBorder(mainController.bottomRightBorder);
            mainController.comicSelection = mainController.bottomRightIV;
            mainController.comicCharacterSelection = mainController.bottomRightIV;
            mainController.comic.setSelected(mainController.comic.getRightCharacter());
            mainController.getButtonController().switchButtonState(true);
            event.consume();
        });

        mainController.getButtonController().switchButtonState(true);
        mainController.getColourController().removeHairAA();
        mainController.getCharacterController().clearBackground();
        mainController.getColourController().removeAAPixels();
    }

    public void insertLeftCharacter(Character character){ //Method that inserts a character into the left panel and adds character data to the Comic class
        mainController.comic.setLeftCharacter(character);
        mainController.comic.setSelected(mainController.comic.getLeftCharacter());
        mainController.comicSelection = mainController.bottomLeftIV;
        mainController.comicCharacterSelection = mainController.bottomLeftIV;
        mainController.bottomLeftIV.setScaleX(character.getFacing());
        mainController.bottomLeftIV.setImage(mainController.comic.getLeftCharacter().getImage());
        mainController.bottomLeftIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mainController.setBorder(mainController.bottomLeftBorder);
            mainController.comicSelection = mainController.bottomLeftIV;
            mainController.comicCharacterSelection = mainController.bottomLeftIV;
            mainController.comic.setSelected(mainController.comic.getLeftCharacter());
            mainController.getButtonController().switchButtonState(true);
            event.consume();
        });
        mainController.getButtonController().switchButtonState(true);
        mainController.getColourController().removeHairAA();
        mainController.getCharacterController().clearBackground();
        mainController.getColourController().removeAAPixels();
    }

    void insertBubble(ImageView imageView) { //Method that inserts the thought/speech bubble into the correct section of the comic
        if (mainController.comic.getSelected().equals(mainController.comic.getLeftCharacter())) {
            mainController.comic.setCentreLeft(imageView);
            mainController.comic.getCentreLeft().setScaleX(-1);
            mainController.centreLeft.setImage(mainController.comic.getCentreLeft().getImage());
            mainController.centreLeft.setScaleX(mainController.comic.getCentreLeft().getScaleX());
            mainController.leftTextField.setDisable(false);
            mainController.leftTextField.setVisible(true);
        }
        else{
            mainController.comic.setCentreRight(imageView);
            mainController.centreRight.setImage(mainController.comic.getCentreRight().getImage());
            mainController.rightTextField.setDisable(false);
            mainController.rightTextField.setVisible(true);
        }
    }

    void insertLeftTextGraphic(String leftText){
        TextGraphic leftTextGraphic = new TextGraphic(leftText);
        mainController.leftTextImageview.setImage(leftTextGraphic.getImage());
        mainController.leftTextImageview.setVisible(true);
        mainController.leftHbox.setVisible(true);
        mainController.leftTextField.setVisible(false);
    }

    void insertRightTextGraphic(String rightText){
        TextGraphic rightTextGraphic = new TextGraphic(rightText);
        mainController.rightTextImageview.setImage(rightTextGraphic.getImage());
        mainController.rightHbox.setVisible(true);
        mainController.rightTextImageview.setVisible(true);
        mainController.rightTextField.setVisible(false);
    }

    void insertBackground(Image selectedImage){  //Method that places the background into the comic panel
        mainController.comic.setBackground(new ImageView(selectedImage));
        mainController.background.setImage(mainController.comic.getBackground().getImage());
    }
}
