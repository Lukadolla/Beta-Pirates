package app;

public class Comic {

  private Character leftCharacter;
  private Character rightCharacter;
  private Character selected;
  private String topText;
  private String bottomText;
  private String leftText;
  private String rightText;

  // Add lines to bubbles

  public Comic(){
  }

  public Character getLeftCharacter() {
    return leftCharacter;
  }

  public void setLeftCharacter(Character leftCharacter) {
    this.leftCharacter = leftCharacter;
  }

  public Character getRightCharacter() {
    return rightCharacter;
  }

  public void setRightCharacter(Character rightCharacter) {
    this.rightCharacter = rightCharacter;
  }

  public Character getSelected() {
    return selected;
  }

  public void setSelected(Character selected) {
    this.selected = selected;
  }

  public String getTopText() {
    return topText;
  }

  public void setTopText(String topText) {
    this.topText = topText;
  }

  public String getBottomText() {
    return bottomText;
  }

  public void setBottomText(String bottomText) {
    this.bottomText = bottomText;
  }

  public String getLeftText() {
    return leftText;
  }

  public void setLeftText(String leftText) {
    this.leftText = leftText;
  }

  public String getRightText() {
    return rightText;
  }

  public void setRightText(String rightText) {
    this.rightText = rightText;
  }

}
