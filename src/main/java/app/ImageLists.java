package app;

import app.controllers.LowerPanelController;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class ImageLists {

  private List<Image> characterImages;  //List of character images
  private List<Image> backgroundImages;  //List of background images
  private ArrayList<String> characterImageNames = new ArrayList<>(); //List of file names corresponding to the character images
  private ArrayList<String> backgroundImageNames = new ArrayList<>(); //List of file names corresponding to the background images

  public ImageLists() {
    characterImages = new ArrayList<>();
    backgroundImages = new ArrayList<>();
  }

  public void getImageFiles(String subDir) throws URISyntaxException, IOException {  //Method that checks if the program is running from IDE or JAR and calls methods to handle files
    URI uri = LowerPanelController.class.getResource(subDir).toURI();

    if (uri.getScheme().equals("jar")) {
      handleJar(uri, subDir);

    } else {
      handleIDE(uri, subDir);
    }
  }

  private void handleJar(URI uri, String subDir) throws IOException { //If the program is running from a JAR, call loadImages which fetches the image files from their directory
    FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
    Path myPath = fileSystem.getPath(subDir);

    loadImages(myPath, subDir);
    fileSystem.close();
  }

  private void handleIDE(URI uri, String subDir) throws IOException { //If the program is running from the IDE, call loadImages which fetches the image files from their directory
    Path myPath = Paths.get(uri);
    loadImages(myPath, subDir);
  }

  void loadImages(Path myPath, String subDir) throws IOException { //Method that loads the images in a specified directory to the middle panel
    Stream<Path> walk = Files.walk(myPath, 1);
    Iterator<Path> it = walk.iterator();

    while (it.hasNext()){
      String filePath = it.next().toString();
      filePath = filePath.replaceAll("\\\\", "/");
      if(!(filePath.endsWith(".png") || filePath.endsWith(".jpg") || filePath.endsWith(".gif") || filePath.endsWith(".PNG") || filePath.endsWith(".JPG") || filePath.endsWith(".GIF"))){
        continue;
      }
      String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
      URL url = getClass().getResource(subDir + "/" + fileName);
      String currentPath = url.toString();
      if(subDir.equals("/images/characters")) {
        this.addCharacterImage(new Image(currentPath));
        characterImageNames.add(fileName);
      }
      else{
        this.addBackgroundImage(new Image(currentPath));
        backgroundImageNames.add(fileName);
      }
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

  public ArrayList<String> getCharacterImageNames(){ return characterImageNames; }

  public ArrayList<String> getBackgroundImageNames(){ return backgroundImageNames; }
}
