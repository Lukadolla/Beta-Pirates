package app;

import javafx.scene.image.Image;

public class Character {

  private int position;
  private Image image;
  private String gender;
  private String skinColour;
  private String hairColour;

  public Character(Image image, int position){
    this.position = position;
    gender = "female";
    this.image = image;
  }

  public Character(int position, Image image, String gender,
      String skinColour, String hairColour) {
    this.position = position;
    this.image = image;
    this.gender = gender;
    this.skinColour = skinColour;
    this.hairColour = hairColour;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    if (position == 1 || position == -1) {
      this.position = position;
    }
  }

  public void changePosition(){
    position *= -1;
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
