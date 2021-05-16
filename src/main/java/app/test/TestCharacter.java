package app.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import app.Character;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

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
    assertEquals("Image is incorrect", character.getImage() , image1);

    // check set image
    character.setImage(image2);
    assertEquals("Image is incorrect", character.getImage(), image2);

    // check facing correct direction
    assertEquals("Direction is incorrect", character.getFacing(), 1);

    // check change facing
    character.changeFacing();
    assertEquals("Direction is incorrect after changeFacing", character.getFacing(), -1);

    // check gender
    assertEquals("Gender is incorrect", character.getGender(), "female");

    // check set gender
    character.setGender("male");
    assertEquals("Gender is incorrect after setGender", character.getGender(), "male");

    // check skin colour
    assertEquals("Skin colour is incorrect", character.getSkinColour(), Color.web("ffe8d8"));

    // check hair colour
    assertEquals("Male hair colour is incorrect", character.getMaleHairColour(), Color.web("f9ff00"));
    assertEquals("Female hair is incorrect", character.getFemaleHairColour(), Color.web("f0ff00"));

    // check lip colour
    assertEquals("Lip colour is incorrect", character.getLipColour(), Color.web("ff0000"));

    // check set lip colour
    character.setLipColour(Color.web("ffe8d8"));
    assertEquals("Lip colour is incorrect after setLipColour", character.getLipColour(), Color.web("ffe8d8"));

    // check get bubble
    assertEquals("Bubble is not empty string", character.getBubble(), "");

    // check set speech
    character.setSpeech();
    assertEquals("Bubble is not speech", character.getBubble(), "speech");

    // check set thought
    character.setThought();
    assertEquals("Bubble is not thought", character.getBubble(), "thought");
  }

}