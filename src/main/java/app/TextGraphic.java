package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

public class TextGraphic {

  private BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
  private Graphics2D g2d = img.createGraphics();
  private Font font = new Font("Arial", Font.PLAIN, 48);
  private FontMetrics fm;
  private int width;
  private int height;
  private ImageView imageview;

  public TextGraphic(String text) {
    System.out.println("TextGraphic fired!");

    g2d.setFont(font);
    fm = g2d.getFontMetrics();
    width = fm.stringWidth(text);
    height = fm.getHeight();
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
    g2d.drawString(text, 0, fm.getAscent());
    g2d.dispose();
    Image image = SwingFXUtils.toFXImage(img, null);
    imageview.setImage(image);
  }

  public ImageView setImageview() {
    return imageview;
  }

  public ImageView getImageview() {
    return imageview;
  }

  }