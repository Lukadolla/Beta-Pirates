package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class ImageLists {

  private List<Image> characterImages;
  private List<Image> backgroundImages;

  public ImageLists() {
    characterImages = new ArrayList<>();
    backgroundImages = new ArrayList<>();
  }

  public void loadCharacterImages() throws IOException {  //Loads images currently in the image directory

    BufferedReader txtReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/images/characters/directoryList.txt")));

    for (String line; (line = txtReader.readLine()) != null;) {
        URL url = getClass().getResource(line);
        String currentPath = url.toString();
        this.addCharacterImage(new Image(currentPath));
    }
  }

  public void loadBackgroundImages() throws IOException {  //Loads images currently in the image directory

    BufferedReader txtReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/images/characters/directoryList.txt")));

    for (String line; (line = txtReader.readLine()) != null;) {
      URL url = getClass().getResource(line);
      String currentPath = url.toString();
      this.addCharacterImage(new Image(currentPath));
    }
  }

  private void addCharacterImage(Image image) {
    characterImages.add(image);
  }

  private void addBackgroundImage(Image image) {
    backgroundImages.add(image);
  }

  public List<Image> getCharacterImages() {
    return characterImages;
  }

  public List<Image> getBackgroundImages() {
    return backgroundImages;
  }

}
