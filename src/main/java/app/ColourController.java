package app;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ColourController {

  private TestController mainController;

  public ColourController(TestController controller) { this.mainController = controller;}

  @FXML
  public Color getChosenBodyColour(){  //Method that fetches the body colour chosen by the user from the ColourPicker
    return checkColour(mainController.bodyColourPicker.getValue());
  }

  @FXML
  public void changeSkinColour() {  //Method called when user wants to change the character's skin colour
    Image image = mainController.comicSelection.getImage();
    int imageHeight = (int)image.getHeight();
    int imageWidth = (int)image.getWidth();
    boolean changed = false;

    WritableImage wImage = new WritableImage(imageWidth, imageHeight);
    PixelWriter PW = wImage.getPixelWriter();
    PixelReader PR = image.getPixelReader();
    Color colour;
    Color lipColour = new Color(0,0,0,0);

    for(int i = 0; i < imageWidth; i++){
      for(int j = 0; j < imageHeight; j++){
        colour = PR.getColor(i, j);
        if(compareColours(colour, mainController.comic.getSelected().getSkinColour())){
          colour = getChosenBodyColour();
          changed = true;
        }
        else if(mainController.comic.getSelected().getGender().equals("male") && compareColours(colour, mainController.comic
            .getSelected().getLipColour())){
          colour = changeTone(getChosenBodyColour());
          lipColour = colour;
        }
        PW.setColor(i, j, colour);
      }
    }
    if(changed)
    {
      mainController.comic.getSelected().setLipColour(lipColour);
      mainController.comic.getSelected().setSkinColour(getChosenBodyColour());
    }
    mainController.comic.getSelected().setImage(wImage);
    mainController.comicSelection.setImage(wImage);
  }

  @FXML
  public Color getChosenHairColour(){ //Method that fetches the hair colour chosen by the user from the ColourPicker
    return checkColour(mainController.hairColourPicker.getValue());
  }

  @FXML
  public void changeHairColour() { //Method called when user wants to change the character's hair colour
    Image image = mainController.comicSelection.getImage();
    int imageHeight = (int)image.getHeight();
    int imageWidth = (int)image.getWidth();
    boolean changed = false;

    WritableImage wImage = new WritableImage(imageWidth, imageHeight);
    PixelWriter PW = wImage.getPixelWriter();
    PixelReader PR = image.getPixelReader();
    Color colour;
    Color maleHairColour = new Color(0, 0, 0, 0);

    if(mainController.comic.getSelected().getGender().equals("male"))  //Changing male hair only if the character is male
    {
      for(int i = 0; i < imageWidth; i++){
        for(int j = 0; j < imageHeight; j++){
          colour = PR.getColor(i, j);
          if(compareColours(colour, mainController.comic.getSelected().getMaleHairColour())){
            colour = getChosenHairColour();
            changed = true;
          }
          PW.setColor(i, j, colour);
        }
      }
      if(changed)
      {
        mainController.comic.getSelected().setMaleHairColour(getChosenHairColour());
        mainController.comic.getSelected().setFemaleHairColour(changeTone(getChosenHairColour()));
      }
    }

    if(mainController.comic.getSelected().getGender().equals("female")) //Changing both male and female hair only if the character is female
    {
      for(int i = 0; i < imageWidth; i++){
        for(int j = 0; j < imageHeight; j++){
          colour = PR.getColor(i, j);
          if(compareColours(colour, mainController.comic.getSelected().getFemaleHairColour())){
            colour = getChosenHairColour();
          }

          else if(compareColours(colour, mainController.comic.getSelected().getMaleHairColour()))
          {
            colour = changeTone(getChosenHairColour());
            maleHairColour = colour;
          }

          PW.setColor(i, j, colour);
        }
      }
      mainController.comic.getSelected().setFemaleHairColour(getChosenHairColour());
      mainController.comic.getSelected().setMaleHairColour(maleHairColour);

    }
    mainController.comic.getSelected().setImage(wImage);
    mainController.comicSelection.setImage(wImage);
  }

  Color changeTone(Color colour){ //Method to change the tone slightly of the colour being passed in. This is done to ease colour changing and removing female hair
    Double[] colourList = new Double[3];
    colourList[0] = colour.getRed();
    colourList[1] = colour.getGreen();
    colourList[2] = colour.getBlue();

    if(colourList[0] <= 0.98 && colourList[0] != 0){
      colourList[0] += 0.02;
    }
    else if(colourList[1] <= 0.98 && colourList[1] != 0){
      colourList[1] += 0.02;
    }
    else{
      if(colourList[2] <= 0.98){
        colourList[2] += 0.02;
      }
      else{
        colourList[2] -= 0.02;
      }
    }

    return new Color(colourList[0], colourList[1], colourList[2], colour.getOpacity());
  }

  boolean compareColours(Color colour_1, Color colour_2){  //Method to compare 2 colours to see if they are within the same rough colour range

    return (Math.abs(colour_1.getRed() - colour_2.getRed()) < 0.01) && (Math.abs(colour_1.getGreen() - colour_2.getGreen()) < 0.01) && (Math.abs(colour_1.getBlue() - colour_2.getBlue()) < 0.01) && (colour_1.getOpacity() == colour_2.getOpacity());
  }

  void removeAAPixels() {  //Mthod to remove anti-aliasing pixels in the image
    Image image = mainController.comic.getSelected().getImage();
    int imageHeight = (int) image.getHeight();
    int imageWidth = (int) image.getWidth();
    WritableImage wImage = new WritableImage(imageWidth, imageHeight);
    PixelWriter PW = wImage.getPixelWriter();
    PixelReader PR = image.getPixelReader();

    for (int i = 0; i < imageWidth; i++) {
      for (int j = 0; j < imageHeight; j++) {
        Color colour = PR.getColor(i, j);
        if (!colour.equals(Color.web("000000")) && !compareColours(colour, mainController.comic.getSelected().getFemaleHairColour()) && !mainController.getCharacterController().isBows(colour) && !compareColours(colour, mainController.comic.getSelected().getMaleHairColour()) && colour.getOpacity() > 0.02) {

          Color leftPixel;
          Color rightPixel;
          Color bottomPixel;
          Color topPixel;

          if(i == 0){
            leftPixel = PR.getColor(i, j);
          }
          else {
            leftPixel = PR.getColor(i - 1, j);
          }

          if(i == imageWidth-1){
            rightPixel = PR.getColor(i, j);
          }
          else{
            rightPixel = PR.getColor(i + 1, j);
          }

          if(j == 0){
            bottomPixel = PR.getColor(i, j);
          }
          else{
            bottomPixel = PR.getColor(i, j - 1);
          }

          if(j == imageHeight-1){
            topPixel = PR.getColor(i, j);
          }
          else {
            topPixel = PR.getColor(i, j + 1);
          }

          if (leftPixel.getOpacity() > 0.04 && leftPixel.getOpacity() < 0.06 || leftPixel.getOpacity() < 0.2) {
            colour = new Color(1, 1, 1, 0.05);
          } else if (rightPixel.getOpacity() > 0.04 && rightPixel.getOpacity() < 0.06 || rightPixel.getOpacity() < 0.2) {
            colour = new Color(1, 1, 1, 0.05);
          } else if (topPixel.getOpacity() > 0.04 && topPixel.getOpacity() < 0.06 || topPixel.getOpacity() < 0.2) {
            colour = new Color(1, 1, 1, 0.05);
          } else if (bottomPixel.getOpacity() > 0.04 && bottomPixel.getOpacity() < 0.06 || bottomPixel.getOpacity() < 0.2) {
            colour = new Color(1, 1, 1, 0.05);
          }
        }
        PW.setColor(i, j, colour);
      }
    }

    mainController.comicSelection.setImage(wImage);
    mainController.comic.getSelected().setImage(wImage);
  }

  void removeHairAA(){  //Method to remove hair anti-aliasing by calling the setMale and setFemale methode which handle the AA
    mainController.getCharacterController().setMale();
    removeAAPixels();
    mainController.getCharacterController().setFemale();
  }

  public Color checkColour(Color colour){  //Method to check if the chosen colour is pure white/black and if so, changing the tone slightly
    if(colour.equals(Color.web("000000"))){
      colour = Color.web("00000f");
    }
    else if(colour.equals(Color.web("ffffff"))){
      colour = Color.web("fffff0");
    }

    return colour;
  }

}