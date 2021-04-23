package app;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public class ComicController {

    TestController mainController;

    public ComicController(TestController mainController){
        this.mainController = mainController;
    }

    public void insertRightCharacter(Image selectedImage){  //Method that inserts a character into the right panel and adds character data to the Comic class
        mainController.comic.setRightCharacter(new Character(selectedImage, 1));
        mainController.comic.setSelected(mainController.comic.getRightCharacter());
        mainController.bottomRightIV.setImage(mainController.comic.getRightCharacter().getImage());
        mainController.bottomRightIV.setScaleX(-1);
        mainController.bottomRightIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            setBorder(mainController.bottomRightBorder);
            System.out.println(mainController.comicSelection);
            System.out.println(mainController.comicCharacterSelection);
            System.out.println(mainController.comic.getSelected() +"\n\n");
            mainController.comicSelection = mainController.bottomRightIV;
            mainController.comicCharacterSelection = mainController.bottomRightIV;
            mainController.comic.setSelected(mainController.comic.getRightCharacter());
            System.out.println(mainController.comicSelection);
            System.out.println(mainController.comicCharacterSelection);
            System.out.println(mainController.comic.getSelected());
            mainController.getButtonsController().switchButtonState(true);
            event.consume();
        });

        mainController.getButtonsController().switchButtonState(true);
        mainController.removeHairAA();
        mainController.clearBackground();
        mainController.removeAAPixels();
    }

    public void insertLeftCharacter(Image selectedImage){ //Method that inserts a character into the left panel and adds character data to the Comic class
        mainController.comic.setLeftCharacter(new Character(selectedImage, 1));
        mainController.comic.setSelected(mainController.comic.getLeftCharacter());
        mainController.bottomLeftIV.setImage(mainController.comic.getLeftCharacter().getImage());
        mainController.bottomLeftIV.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            setBorder(mainController.bottomLeftBorder);
            mainController.comicSelection = mainController.bottomLeftIV;
            mainController.comicCharacterSelection = mainController.bottomLeftIV;
            mainController.comic.setSelected(mainController.comic.getLeftCharacter());
            mainController.getButtonsController().switchButtonState(true);
            event.consume();
        });
        mainController.getButtonsController().switchButtonState(true);
        mainController.removeHairAA();
        mainController.clearBackground();
        mainController.removeAAPixels();
    }

    protected void insertBubble(ImageView imageView) { //Method that inserts the thought/speech bubble into the correct section of the comic
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

    protected void insertBackground(Image selectedImage){  //Method that places the background into the comic panel
        mainController.comic.setBackground(new ImageView(selectedImage));
        mainController.background.setImage(mainController.comic.getBackground().getImage());
    }

    protected void setBorder(Region newBorder) { //Method that sets the border on a selected component
        if(mainController.selectedBorder != null){
            mainController.selectedBorder.setVisible(false);
        }
        mainController.selectedBorder = newBorder;
        mainController.selectedBorder.setVisible(true);
    }

    public void saveText(){
        mainController.comic.setLeftText(mainController.leftTextField.getText());
        mainController.comic.setBottomText(mainController.bottomText.getText());
        mainController.comic.setRightText(mainController.rightTextField.getText());
        mainController.comic.setTopText(mainController.topText.getText());
    }

    public void setNullTextInvisible(){
        if(mainController.topText.getText() == null){
            mainController.topText.setVisible(false);
        }
        if(mainController.bottomText.getText() == null){
            mainController.bottomText.setVisible(false);
        }
        if(mainController.leftTextField.getText() == null){
            mainController.leftTextField.setVisible(false);
            mainController.centreLeft.setVisible(false);
        }
        if(mainController.rightTextField.getText() == null){
            mainController.rightTextField.setVisible(false);
            mainController.centreRight.setVisible(false);
        }
    }

    public void setTextVisible(){
        mainController.topText.setVisible(true);
        mainController.bottomText.setVisible(true);
        if(mainController.centreLeft.getImage()!=null){
            mainController.leftTextField.setVisible(true);
            mainController.centreLeft.setVisible(true);
        }
        if(mainController.centreRight.getImage()!=null){
            mainController.rightTextField.setVisible(true);
            mainController.centreRight.setVisible(true);
        }
    }
}
