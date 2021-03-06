package app.controllers;

import app.Character;
import app.Comic;
import app.TextGraphic;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;

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
        mainController.bottomRightIV.setDisable(false);
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
        mainController.bottomLeftIV.setDisable(false);
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

    public void insertLeftTextGraphic(String leftText){  //Method that inserts the image of user inputted text into the left speech/thought bubble
        TextGraphic leftTextGraphic = new TextGraphic(leftText);
        mainController.comic.setLeftGraphic(leftTextGraphic);
        mainController.leftTextImageview.setImage(leftTextGraphic.getImage());
        mainController.leftTextImageview.setVisible(true);
        mainController.leftHbox.setVisible(true);
        mainController.leftTextField.setVisible(false);
    }

    public void insertRightTextGraphic(String rightText){ //Method that inserts the image of user inputted text into the right speech/thought bubble
        TextGraphic rightTextGraphic = new TextGraphic(rightText);
        mainController.comic.setRightGraphic(rightTextGraphic);
        mainController.rightTextImageview.setImage(rightTextGraphic.getImage());
        mainController.rightHbox.setVisible(true);
        mainController.rightTextImageview.setVisible(true);
        mainController.rightTextField.setVisible(false);
    }

    void insertBackground(Image selectedImage){  //Method that places the background into the comic panel
        mainController.comic.setBackground(new ImageView(selectedImage));
        mainController.background.setImage(mainController.comic.getBackground().getImage());
    }

    void clearComic(){ //Removes all elements from a comic
        if(mainController.comic.getLeftCharacter() != null){
            mainController.bottomLeftIV.setImage(null);
            mainController.comic.setLeftCharacter(null);
            mainController.centreLeft.setImage(null);
            mainController.comic.setCentreLeft(null);
            mainController.leftTextField.clear();
            mainController.leftTextImageview.setImage(null);
            mainController.comic.setLeftGraphic(null);
            mainController.comic.setRightGraphic(null);
            mainController.leftTextRegion.setVisible(false);
            mainController.leftTextField.setVisible(false);
            mainController.bottomLeftIV.setDisable(true);
        }
        if (mainController.comic.getRightCharacter() != null){
            mainController.bottomRightIV.setImage(null);
            mainController.comic.setRightCharacter(null);
            mainController.centreRight.setImage(null);
            mainController.comic.setCentreRight(null);
            mainController.rightTextField.clear();
            mainController.rightTextImageview.setImage(null);
            mainController.rightTextRegion.setVisible(false);
            mainController.rightTextField.setVisible(false);
            mainController.bottomRightIV.setDisable(true);
        }

        if(mainController.selectedBorder != null) {
            mainController.selectedBorder.setVisible(false);
        }

        mainController.comic.setSelected(null);
        mainController.comicSelection = null;
        mainController.comicCharacterSelection = null;
        mainController.getButtonController().switchButtonState(false);


        URL url = getClass().getResource("/images/backgrounds/default.png");
        String currentPath = url.toString();
        ImageView backgroundImage = new ImageView(currentPath);
        mainController.background.setImage(backgroundImage.getImage());
        mainController.comic.setBackground(backgroundImage);
        mainController.comic.setChosenBackground("default.png");

        mainController.topText.setVisible(true);
        mainController.topText.setText("");
        mainController.bottomText.setVisible(true);
        mainController.bottomText.setText("");
    }

    void drawComic(Comic comicCopy) { //Updates the main comic with a previously saved comic from the bottom pane

        if(comicCopy.getLeftCharacter() != null) {
            redrawLeftCharacter(comicCopy.getLeftCharacter());
            if(comicCopy.getCentreLeft() != null){
                ImageView centreLeftCopy = new ImageView(comicCopy.getCentreLeft().getImage());
                mainController.comic.setCentreLeft(centreLeftCopy);
                mainController.centreLeft.setImage(centreLeftCopy.getImage());
            }
        }

        if(comicCopy.getRightCharacter() != null) {
            redrawRightCharacter(comicCopy.getRightCharacter());
            if(comicCopy.getCentreRight() != null){
                ImageView centreRightCopy = new ImageView(comicCopy.getCentreRight().getImage());
                mainController.comic.setCentreRight(centreRightCopy);
                mainController.centreRight.setImage(centreRightCopy.getImage());
            }
        }

        if(comicCopy.getBackground() != null) {
            mainController.comic.setBackground(comicCopy.getBackground());
            mainController.background.setImage(mainController.comic.getBackground().getImage());
        }

        mainController.comic.setLeftText(comicCopy.getLeftText());
        mainController.leftTextField.setText(comicCopy.getLeftText());

        mainController.comic.setRightText(comicCopy.getRightText());
        mainController.rightTextField.setText(comicCopy.getRightText());

        mainController.getTextGraphicController().checkTextForGraphic();

        mainController.comic.setTopText(comicCopy.getTopText());
        mainController.topText.setText(comicCopy.getTopText());

        mainController.comic.setBottomText(comicCopy.getBottomText());
        mainController.bottomText.setText(comicCopy.getBottomText());

        mainController.topText.setVisible(true);
        mainController.bottomText.setVisible(true);
    }

    public void redrawRightCharacter(Character rightCharacter){  //Method that inserts a character into the right panel and adds character data to the Comic class
        mainController.comic.setRightCharacter(rightCharacter);
        mainController.bottomRightIV.setImage(mainController.comic.getRightCharacter().getImage());
        mainController.bottomRightIV.setScaleX(rightCharacter.getFacing());
        mainController.bottomRightIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mainController.setBorder(mainController.bottomRightBorder);
            mainController.comicSelection = mainController.bottomRightIV;
            mainController.comicCharacterSelection = mainController.bottomRightIV;
            mainController.comic.setSelected(mainController.comic.getRightCharacter());
            mainController.getButtonController().switchButtonState(true);
            event.consume();
        });
        mainController.bottomRightIV.setDisable(false);
    }

    public void redrawLeftCharacter(Character leftCharacter){  //Method that inserts a character into the right panel and adds character data to the Comic class
        mainController.comic.setLeftCharacter(leftCharacter);
        mainController.bottomLeftIV.setImage(mainController.comic.getLeftCharacter().getImage());
        mainController.bottomRightIV.setScaleX(leftCharacter.getFacing());
        mainController.bottomLeftIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mainController.setBorder(mainController.bottomLeftBorder);
            mainController.comicSelection = mainController.bottomLeftIV;
            mainController.comicCharacterSelection = mainController.bottomLeftIV;
            mainController.comic.setSelected(mainController.comic.getLeftCharacter());
            mainController.getButtonController().switchButtonState(true);
            event.consume();
        });
        mainController.bottomLeftIV.setDisable(false);
    }

    void removeNullText(){ //Hides empty text fields
        try {
            if (mainController.leftTextField.getText() != null && mainController.leftTextField.getText().trim().equals("")) {
                mainController.leftTextField.clear();
                mainController.centreLeft.setImage(null);
                mainController.comic.setCentreLeft(null);
                mainController.leftTextField.setVisible(false);
                mainController.leftTextRegion.setVisible(false);
                mainController.comic.getLeftCharacter().setNullBubble();
            }
        } catch(NullPointerException e){
        }

        try {
            if (mainController.rightTextField.getText() != null && mainController.rightTextField.getText().trim().equals("")) {
                mainController.rightTextField.clear();
                mainController.centreRight.setImage(null);
                mainController.comic.setCentreRight(null);
                mainController.rightTextField.setVisible(false);
                mainController.rightTextRegion.setVisible(false);
                mainController.comic.getLeftCharacter().setNullBubble();
            }
        } catch(NullPointerException e){
        }

        if(mainController.topText.getText() != null && mainController.topText.getText().trim().equals("")){
            mainController.topText.setVisible(false);
        }
        if(mainController.bottomText.getText() != null && mainController.bottomText.getText().trim().equals("")){
            mainController.bottomText.setVisible(false);
        }
    }

    void removeBubble(){
        if(mainController.comic.getSelected().equals(mainController.comic.getLeftCharacter())){
            mainController.leftTextField.clear();
            mainController.leftTextField.setDisable(true);
            mainController.leftTextField.setVisible(false);
            mainController.centreLeft.setImage(null);
            mainController.comic.setLeftText("");
            mainController.comic.setCentreLeft(null);
            mainController.leftTextRegion.setVisible(false);
            mainController.leftTextImageview.setVisible(false);
        }
        else{
            mainController.rightTextField.clear();
            mainController.rightTextField.setDisable(true);
            mainController.rightTextField.setVisible(false);
            mainController.centreRight.setImage(null);
            mainController.comic.setRightText("");
            mainController.comic.setCentreRight(null);
            mainController.rightTextRegion.setVisible(false);
            mainController.rightTextImageview.setVisible(false);
        }
        mainController.comic.getSelected().setNullBubble();
    }
}
