package app;

import javafx.scene.image.Image;

public class Character {

  private int facing;
  private Image image;
  private String gender;
  private String skinColour;
  private String hairColour;

  public Character(Image image){
    facing = 1;
    gender = "female";
    this.image = image;
  }

  public Character(int facing, Image image, String gender,
      String skinColour, String hairColour) {
    this.facing = facing;
    this.image = image;
    this.gender = gender;
    this.skinColour = skinColour;
    this.hairColour = hairColour;
  }

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

  public String getSkinColour() {
    return skinColour;
  }

  public void setSkinColour(String skinColour) {
    this.skinColour = skinColour;
  }

  public String getHairColour() {
    return hairColour;
  }

  public void setHairColour(String hairColour) {
    this.hairColour = hairColour;
  }
}
