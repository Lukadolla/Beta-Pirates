package app;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import app.controllers.Controller;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class TextGraphic {

  private Controller mainController;

  public TextGraphic(Controller mainController) { this.mainController = mainController; }


  private BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
  private Graphics2D g2d = img.createGraphics();
  private Font font = new Font("Arial", Font.PLAIN, 48);
  private FontMetrics fm;
  private int width;
  private int height;
  private int lines;
  //  private ImageView imageview;
  private Image image;
  private String text;

  public TextGraphic(String text) {

    if (text.length() > 16) {
      this.text = addNewLines(text);
    }

    else {
      while (text.length() < 15) {

        StringBuilder builder = new StringBuilder(text);
        builder.append(" ");
        builder.insert(0, " ");
        text = builder.toString();
      }
      this.text = text;
    }

    StringBuilder sb = new StringBuilder(this.text);
    this.text = sb.toString();

    String[] text_array = this.text.split("[\\r?\\n]");

    g2d.setFont(font);
    fm = g2d.getFontMetrics();
    width = fm.stringWidth(getLongestLine(text_array));
    lines = getLineCount(this.text);
    height = fm.getHeight() * (lines);
    g2d.dispose();
    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    g2d = img.createGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g2d.setFont(font);
    fm = g2d.getFontMetrics();
    g2d.setColor(Color.WHITE);
    g2d.fillRoundRect(15, 5, img.getWidth()-30, img.getHeight()-10, 90, 90);
    g2d.setColor(Color.BLACK);
    for (int i = 1; i <= lines; ++i) {
      g2d.drawString(text_array[i - 1], 0, fm.getAscent() * i);
    }
    g2d.dispose();
    this.image = SwingFXUtils.toFXImage(img, null);
  }

  private String addNewLines(String text) {
    String[] words = text.split(" ");
    int numWords = words.length;
    int middle = (numWords / 2) + (numWords % 2);
    String firstLine = Stream.of(words).limit(middle).collect(Collectors.joining(" "));
    String secondLine = Stream.of(words).skip(middle).collect(Collectors.joining(" "));

    StringBuilder sb1 = new StringBuilder(firstLine);
    sb1.append(" ");
    sb1.insert(0, " ");
    firstLine = sb1.toString();

    StringBuilder sb2 = new StringBuilder(secondLine);
    sb2.append(" ");
    sb2.insert(0, " ");
    secondLine = sb2.toString();

    if (secondLine.length() < firstLine.length()){
      secondLine = padText(secondLine, (firstLine.length()-secondLine.length()));
    }

    String multipleLines = firstLine + "\n" + secondLine;
    return multipleLines;
  }

  private String padText(String text, int n) {

    StringBuilder sb = new StringBuilder(text);
    for (int i = 0; i <= n/2; i++)
      sb.insert(0, " ");
      sb.append(" ");
    return sb.toString();
  }

  private static String getLongestLine(String[] arr) {
    String max = arr[0];
    for (int i = 1; i < arr.length; i++) {
      if (max.length() < arr[i].length()) {
        max = arr[i];
      }
    }
    return max;
  }

  public int getLineCount(String text) {
    return text.split("[\n]").length;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) { this.image = image; }

  public void checkTextForGraphic(){
    if (mainController.comic != null) {
      if (mainController.leftTextField.getText() != null && !mainController.leftTextField.getText().trim().equals("")) {
        mainController.getComicController()
                .insertLeftTextGraphic(mainController.leftTextField.getText());
        mainController.leftTextRegion.setVisible(true);
      }
      if (mainController.rightTextField.getText() != null && !mainController.rightTextField.getText().trim().equals("")) {
        mainController.getComicController()
                .insertRightTextGraphic(mainController.rightTextField.getText());
        mainController.rightTextRegion.setVisible(true);
      }
    }
  }

}