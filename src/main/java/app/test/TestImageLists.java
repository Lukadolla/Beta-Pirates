package app.test;

import app.ImageLists;
import java.io.IOException;
import java.util.List;
import javafx.scene.image.Image;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestImageLists {

  @Test
  public void testImageLists() throws IOException {

    // test data
    ImageLists imageLists = new ImageLists();

    List<Image> characterImages = imageLists.getCharacterImages();
    List<Image> backgroundImages = imageLists.getBackgroundImages();


    // check that imageList is not null
    assertNotNull(imageLists);

    // check imageLists is instance of imageLists
    assertTrue("test", imageLists instanceof ImageLists);

    // check that characterImages is not null
    assertNotNull(characterImages);

    // check number of character images
    assertTrue(characterImages.size() == 32);

    // check that imageList is not null
    assertNotNull(backgroundImages);

    // check the number of background images
    assertTrue(backgroundImages.size() == 16);

    // check that object equals
    assertEquals(imageLists, imageLists);

  }

}