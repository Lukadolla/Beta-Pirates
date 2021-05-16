//package app.test;
//
//import static org.junit.Assert.assertEquals;
//
//import app.Character;
//import app.Comic;
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.net.URL;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.stage.Stage;
//import org.junit.Test;
//import org.testfx.framework.junit.ApplicationTest;
//
//public class TestComic extends ApplicationTest {
//
//  @Override
//  public void start(Stage stage) throws Exception {
//  }
//
//  @Test
//  public void testComic() throws IOException, URISyntaxException {
//
//    //data
//    Comic comic = new Comic();
//    URL url1 = getClass().getResource("/images/characters/accusing.png");
//    URL url2 = getClass().getResource("/images/characters/angry.png");
//    URL url3 = getClass().getResource("/images/buttons/thought.png");
//    URL url4 = getClass().getResource("/images/buttons/speech.png");
//    URL url5 = getClass().getResource("/images/backgrounds/mordor.jpg");
//    Image image1 = new Image(url5.toString());
//    ImageView imageView1 = new ImageView(url3.toString());
//    ImageView imageView2 = new ImageView(url4.toString());
//    Character character1 = new Character(new Image(url1.toString()), "accusing.png");
//    Character character2 = new Character(new Image(url2.toString()), "angry.png");
//    comic.setLeftCharacter(character1);
//    comic.setRightCharacter(character2);
//    comic.setSelected(character1);
//    comic.setTopText("12345");
//    comic.setBottomText("67890");
//    comic.setLeftText("aeiou");
//    comic.setRightText("abcde");
//    comic.setCentreLeft(imageView1);
//    comic.setCentreRight(imageView2);
//    comic.setComicImage(image1);
//    comic.setChosenBackground("couch.png");
//
//    // check characters
//    assertEquals("Left character is not character", comic.getLeftCharacter(), character1);
//    assertEquals("Right character is not null", comic.getRightCharacter(), character2);
//
//    // check selected
//    assertEquals("Character 1 is not selected", comic.getSelected(), character1);
//
//    // check top text
//    assertEquals("Top text is incorrect", comic.getTopText(), "12345");
//    // check bottom text
//    assertEquals("Bottom text is incorrect", comic.getBottomText(), "67890");
//    // check left text
//    assertEquals("Top left text is incorrect", comic.getLeftText(), "aeiou");
//    // check top text
//    assertEquals("Top right text is incorrect", comic.getRightText(), "abcde");
//
//    // check CentreLeft and CentreRight
//    assertEquals("Left imageview is not set to thought", comic.getCentreLeft(), imageView1);
//    assertEquals("Right imageview is not set to thought", comic.getCentreRight(), imageView2);
//
//    // check comicImage
//    assertEquals("Comic image is not populated", comic.getComicImage(), image1);
//
//    // check background
//    assertEquals("Background is incorrect", comic.getChosenBackground(), "couch.png");
//
//  }
//}
