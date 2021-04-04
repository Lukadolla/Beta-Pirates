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

  public void loadImages(File directory) throws MalformedURLException {  //Loads images currently in the image directory
    File[] images = directory.listFiles();

    if (characterImages != null)
      for (File image : images) {
        try {
          this.addImage(new Image(image.toURI().toURL().toExternalForm(), 1920, 1080, true, true));
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
