package app;

import javafx.scene.image.Image;

public class Character {

  private int facing;
  private Image image;
  private boolean selected;
  private String gender;
  private String skinColour;
  private String hairColour;

  public Character(){
    facing = 1;
    selected = false;
    gender = "female";
  }

  public Character(int facing, Image image, boolean selected, String gender,
      String skinColour, String hairColour) {
    this.facing = facing;
    this.image = image;
    this.selected = selected;
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

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
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
