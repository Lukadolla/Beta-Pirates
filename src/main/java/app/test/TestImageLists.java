package app.test;

import app.ImageLists;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class TestImageLists extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
  }

  @Test
  public void testImageLists() throws IOException, URISyntaxException {

    // test data
    ImageLists imageLists = new ImageLists();
    List<Image> characterImages = imageLists.getCharacterImages();
    imageLists.getImageFiles("/images/characters");
    imageLists.getImageFiles("/images/backgrounds");
    List<Image> backgroundImages = imageLists.getBackgroundImages();
    List<String> characterImageNames = imageLists.getCharacterImageNames();
    List<String> backgroundImageNames = imageLists.getBackgroundImageNames();

    // check that imageList is not null
    assertNotNull("imageList is null.");
    // check imageLists is instance of imageLists
    assertTrue("Not an ImageLists object.", imageLists instanceof ImageLists);

    // check that characterImages is not null
    assertNotNull("characterImages is null.", characterImages);
    // check characterImages are images
    assertTrue("characterImages does not contain image objects.", characterImages.get(0) instanceof Image);

    // check that backgroundImages is not null
    assertNotNull("backgroundImages is null.", backgroundImages);
    // check backgroundImages are images
    assertTrue("characterImages does not contain image objects.", backgroundImages.get(0) instanceof Image);

    // check that characterImagesNames is not null
    assertNotNull("characterImageNames is null.", characterImages);
    // check characterImagesNames are strings
    assertTrue("characterImageNames does not contain string objects.", characterImageNames.get(0) instanceof String);

    // check that backgroundImagesNames is not null
    assertNotNull("backgroundImagesNames is null.", backgroundImageNames);
    // check backgroundImagesNames are strings
    assertTrue("backgroundImageNames does not contain string objects.", backgroundImageNames.get(0) instanceof String);

    // check that characterImagesNames is not null
    assertNotNull("characterImagesNames is null.", characterImageNames);
    // check characterImagesNames are strings
    assertTrue("characterImageNames does not contain string objects.", characterImageNames.get(0) instanceof String);
  }

}