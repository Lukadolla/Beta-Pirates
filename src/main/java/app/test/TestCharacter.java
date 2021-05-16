package app.test;

import app.Character;
import app.ImageLists;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class TestCharacter extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
  }

  @Test
  public void testCharacter() throws IOException, URISyntaxException {

    // test data
    URL url1 = getClass().getResource("/images/characters/accusing.png");
    URL url2 = getClass().getResource("/images/characters/angry.png");
    String currentPath1 = url1.toString();
    String currentPath2 = url2.toString();
    Image image1 = new Image(currentPath1);
    Image image2 = new Image(currentPath2);
    Character character = new Character(image1, "accusing.png");


    // check not null
    assertNotNull("character is null.", character);
    // check instance of Character
    assertTrue("Not a Character object.", character instanceof Character);
    // check contains correct image
    assertEquals(character.getImage() , image1);

    // check set image
    character.setImage(image2);
    assertEquals(character.getImage(), image2);

    // check facing correct direction
    assertEquals(character.getFacing(), 1);

    // check change facing
    character.changeFacing();
    assertEquals(character.getFacing(), -1);

    // check gender
    assertEquals(character.getGender(), "female");

    // check set gender
    character.setGender("male");
    assertEquals(character.getGender(), "male");

    // check skin colour
    assertEquals(character.getSkinColour(), Color.web("ffe8d8"));

    // check hair colour
    assertEquals(character.getMaleHairColour(), Color.web("f9ff00"));
    assertEquals(character.getFemaleHairColour(), Color.web("f0ff00"));

    // check lip colour
    assertEquals(character.getLipColour(), Color.web("ff0000"));

    // check set lip colour
    character.setLipColour(Color.web("ffe8d8"));
    assertEquals(character.getLipColour(), Color.web("ffe8d8"));

    // check get bubble
    assertEquals(character.getBubble(), "");

    // check set speech
    character.setSpeech();
    assertEquals(character.getBubble(), "speech");

    // check set thought
    character.setThought();
    assertEquals(character.getBubble(), "thought");
  }

}