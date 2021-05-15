package app.controllers;

import app.Character;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class CharacterController {

    private Controller mainController;

    public CharacterController(Controller controller){
        mainController = controller;
    }

    void setMale() {  //Method that sets the current character to be male (removing female hair + lipstick)
        Image image = mainController.comic.getSelected().getImage();
        int imageHeight = (int)image.getHeight();
        int imageWidth = (int)image.getWidth();
        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();

        for(int i = 0; i < imageWidth; i++){
            for(int j = 0; j < imageHeight; j++){
                Color colour = PR.getColor(i, j);
                if(isLips(colour)){
                    colour = mainController.getColourController().changeTone(mainController.comic.getSelected().getSkinColour());
                    mainController.comic.getSelected().setLipColour(colour);
                }
                else if(isHair(colour)){
                    colour = Color.web("fffffe");
                    colour = new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), 0);
                }
                else if(isBows(colour)){
                    colour = Color.web("feffff");
                    colour = new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), 0.02);
                }
                PW.setColor(i, j, colour);
            }
        }

        mainController.comic.getSelected().setGender("male");
        mainController.comicSelection.setImage(wImage);
        mainController.comic.getSelected().setImage(wImage);
    }

    void setFemale() { //Method that sets the current character to be female (adding female hair + lipstick)
        Image image = mainController.comic.getSelected().getImage();
        int imageHeight = (int)image.getHeight();
        int imageWidth = (int)image.getWidth();
        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();

        for(int i = 0; i < imageWidth; i++){
            for(int j = 0; j < imageHeight; j++){
                Color colour = PR.getColor(i, j);
                if(mainController.getColourController().compareColours(colour, mainController.comic.getSelected().getLipColour())){
                    colour = Color.web("ff0000");
                }
                else if(colour.getOpacity() == 0){
                    colour = mainController.comic.getSelected().getFemaleHairColour();
                }
                else if(colour.getOpacity() < 0.03 && colour.getOpacity() > 0.015){
                    colour = Color.web("ecb4b5");
                }
                PW.setColor(i, j, colour);
            }
        }

        mainController.comic.getSelected().setGender("female");
        mainController.comicSelection.setImage(wImage);
        mainController.comic.getSelected().setImage(wImage);
    }

    private boolean isLips(Color color){ //Method to check if the colour passed and colours close to it are the lip colour

        boolean isItLips = false;

        if(color.getRed()==1 && color.getGreen()<0.6 && color.getBlue()>=color.getGreen()/2){
            isItLips = true;
        }
        else if(color.getGreen()==0 && color.getBlue()==0 && color.getRed()>0.8){
            isItLips = true;
        }

        if(mainController.getColourController().compareColours(color, mainController.comic.getSelected().getLipColour())) {
            isItLips = false;
        }

        return isItLips;

    }

    private boolean isHair(Color colour){ //Method to check if the colour passed and colours close to it are the current hair colour
        Character character = mainController.comic.getSelected();
        boolean isItHair = false;
        if(colour.getRed()>=0.85 && colour.getGreen()>=0.85 && colour.getBlue()<0.3){
            isItHair = true;
        }
        else if(colour.getRed()>=0.95 && colour.getGreen()>=0.95 && colour.getBlue()<0.5){
            isItHair = true;
        }

        if(mainController.getColourController().compareColours(colour, character.getMaleHairColour()) || mainController.getColourController().compareColours(colour, character.getSkinColour())){
            isItHair = false;
        }
        if(mainController.getColourController().compareColours(colour, character.getFemaleHairColour())){
            isItHair = true;
        }
        return isItHair;
    }

    boolean isBows(Color colour){  //Method to check if the colour passed and colours close to it are the bow colour
        boolean isItBows = false;
        if(colour.equals(Color.web("ecb4b5"))){
            isItBows = true;
        }
        else if(colour.getRed()>=0.9 && colour.getRed()<1 && colour.getGreen()-colour.getBlue()<0.02 && colour.getBlue()-colour.getGreen()<0.02 && colour.getGreen()<colour.getRed()){
            isItBows = true;
        }
        else if(colour.getRed()>=0.9 && colour.getRed()<1 && colour.getGreen()>=0.75 && colour.getGreen()<=0.85 && colour.getBlue()<=0.5 && colour.getBlue()>0.35){
            isItBows = true;
        }

        if(colour.equals(Color.web("e64d4d"))){
            isItBows=false;
        }
        return isItBows;
    }

    void clearBackground() {  //Method that removes the white background in the character images and makes it transparent instead
        Image image = mainController.comic.getSelected().getImage();
        int imageHeight = (int)image.getHeight();
        int imageWidth = (int)image.getWidth();
        WritableImage wImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter PW = wImage.getPixelWriter();
        PixelReader PR = image.getPixelReader();

        for(int i = 0; i < imageWidth; i++){
            for(int j = 0; j < imageHeight; j++){
                Color colour = PR.getColor(i, j);
                if(colour.equals(Color.web("ffffff"))){
                    colour = new Color(1, 1, 1, 0.01);
                }
                PW.setColor(i, j, colour);
            }
        }

        mainController.comicSelection.setImage(wImage);
        mainController.comic.getSelected().setImage(wImage);
    }
}
