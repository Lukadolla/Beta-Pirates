package app;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Character {

  private int position;
  private int facing;
  private Image image;
  private String gender;
  private Color skinColour;
  private Color maleHairColour;
  private Color femaleHairColour;
  private Color lipColour;
  private int chosenImage;

  public Character(Image image, int position, int chosenImage){
    this.position=position;
    facing = 1;
    gender = "female";
    this.image = image;
    skinColour = Color.web("ffe8d8");
    maleHairColour = Color.web("f9ff00");
    femaleHairColour = Color.web("f0ff00");
    lipColour = Color.web("ff0000");
    this.chosenImage = chosenImage;
  }

  public int getChosenImage() {
    return chosenImage;
  }

  public int getPosition() { return position; }

  public void setPosition(int position) {this.position=position;}

  public int getFacing() {
    return facing;
  }

  public void setFacing(int facing) {
    if (facing == 1 || facing == -1) {
      this.facing = facing;
    }
  }

  public void changeFacing(){
    facing *= -1;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Color getSkinColour() {
    return skinColour;
  }

  public void setSkinColour(Color skinColour) {
    this.skinColour = skinColour;
  }

  public Color getMaleHairColour() {
    return maleHairColour;
  }

  public void setMaleHairColour(Color hairColour) {
    this.maleHairColour = hairColour;
  }

  public Color getFemaleHairColour() {
    return femaleHairColour;
  }

  public void setFemaleHairColour(Color hairColour) {
    this.femaleHairColour = hairColour;
  }

  public Color getLipColour(){ return lipColour;}

  public void setLipColour(Color lipColour){ this.lipColour = lipColour;}
}
