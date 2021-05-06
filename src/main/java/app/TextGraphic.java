package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TextGraphic {

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
    System.out.println("TextGraphic fired!");

    this.text = text.toUpperCase();

    if (text.length() > 16) {
      this.text = addNewLines(text);
    }

    StringBuilder sb = new StringBuilder(this.text);
    sb.insert(0,"\n\n");
    this.text = sb.toString();

    System.out.println("- text after conditional = " + this.text);

    String[] text_array = this.text.split("[\\r?\\n]");

    System.out.println("- text after conditional = " + Arrays.toString(text_array));

    g2d.setFont(font);
    fm = g2d.getFontMetrics();
    width = fm.stringWidth(getLongestLine(text_array));
    lines = getLineCount(this.text);
    height = fm.getHeight() * (lines + 4);
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
    g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
    g2d.setColor(Color.BLACK);
    for (int i = 1; i <= lines; ++i) {
      g2d.drawString(text_array[i - 1], 0, fm.getAscent() * i);
    }
    g2d.dispose();
    this.image = SwingFXUtils.toFXImage(img, null);
  }

  private String addNewLines(String text) {
    System.out.println("-> addNewLines");
    String[] words = text.split(" ");
    System.out.println(words.toString());
    int numWords = words.length;
    System.out.println("-> number of words = " + numWords);
    int middle = (numWords / 2) + (numWords % 2);
    System.out.println("-> line break after word = " + middle);
    String firstLine = Stream.of(words).limit(middle).collect(Collectors.joining(" "));
    System.out.println("-> firstLine = " + firstLine);
    String secondLine = Stream.of(words).skip(middle).collect(Collectors.joining(" "));

    if (secondLine.length() < firstLine.length()){
      secondLine = padText(secondLine, (firstLine.length()-secondLine.length()));
    }

    System.out.println("-> secondLine = " + secondLine);
    String multipleLines = firstLine + "\n" + secondLine;
    System.out.println("-> multipleLines = " + multipleLines);



    return multipleLines;
  }

//  private int characterCount(String string) {
//    int count = 0;
//    for(int i = 0; i < string.length(); i++)
//      count += string[i].length();
//
//    return count;
//  }

  private String padText(String text, int n) {

    StringBuilder sb = new StringBuilder(text);
    for (int i = 0; i <= n/2; i++)
      sb.insert(0, " ");
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

}