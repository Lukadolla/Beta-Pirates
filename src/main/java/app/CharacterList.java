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

public class CharacterList {

  private List<Image> characterImages;

  public CharacterList() {
    characterImages = new ArrayList<>();
  }

  public void loadImages() throws IOException {  //Loads images currently in the image directory

    BufferedReader txtReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/images/characters/directoryList.txt")));

    for (String line; (line = txtReader.readLine()) != null;) {
        URL url = getClass().getResource(line);
        String currentPath = url.toString();
        this.addImage(new Image(currentPath));
    }
  }

  private void addImage(Image image) {
    characterImages.add(image);
  }

  public List<Image> getImages() {
    return characterImages;
  }

}
