package app;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class CharacterList {

  private List<Image> characterImages;

  public CharacterList() {
    characterImages = new ArrayList<>();
  }


  // investigate replacing directory parameter with something like:
  // URL imageUrl = getClass().getResource("/images/characters");

  public void loadImages(File directory) throws MalformedURLException {
    File[] images = directory.listFiles();

    if (characterImages != null)
      for (File image : images) {
        try {
          this.addImage(new Image(image.toURI().toURL().toExternalForm(), 100, 100, true, true));
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
      }
    else System.out.println("Directory error");

  }

  private void addImage(Image image) {
    characterImages.add(image);
  }

  public List<Image> getImages() {
    return characterImages;
  }

}
