package app;

import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class ImageLists {

  private List<Image> characterImages;
  private List<Image> backgroundImages;
  private ArrayList<String> characterImageNames = new ArrayList<>();
  private ArrayList<String> backgroundImageNames = new ArrayList<>();

  public ImageLists() {
    characterImages = new ArrayList<>();
    backgroundImages = new ArrayList<>();
  }

  void getImageFiles(String subDir) throws URISyntaxException, IOException {
    URI uri = LowerPanelController.class.getResource(subDir).toURI();

    if (uri.getScheme().equals("jar")) {
      handleJar(uri, subDir);

    } else {
      handleIDE(uri, subDir);
    }
  }

  private void handleJar(URI uri, String subDir) throws IOException {
    FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
    Path myPath = fileSystem.getPath(subDir);
    Stream<Path> walk = Files.walk(myPath, 1);

    Iterator<Path> it = walk.iterator();

    while (it.hasNext()){
      String filePath = it.next().toString();
      if((filePath.endsWith(".txt")) || (!(filePath.endsWith(".png")) && !(filePath.endsWith(".jpg")))){
        continue;
      }
      String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
      URL url = getClass().getResource(subDir + "/" + fileName);
      String currentPath = url.toString();
      if(subDir.equals("/images/characters")) {
        this.addCharacterImage(new Image(currentPath));
      }
      else{
        this.addBackgroundImage(new Image(currentPath));
      }
    }
    fileSystem.close();
  }

  private void handleIDE(URI uri, String subDir) throws IOException {
    Path myPath = Paths.get(uri);
    Stream<Path> walk = Files.walk(myPath, 1);

    Iterator<Path> it = walk.iterator();

    while (it.hasNext()){
      String filePath = it.next().toString();
      if(!(filePath.endsWith(".png") || filePath.endsWith(".jpg") || filePath.endsWith(".gif"))){
        continue;
      }
      String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
      URL url = getClass().getResource(subDir + "\\" + fileName);
      String currentPath = url.toString();
      String name = currentPath.substring(currentPath.lastIndexOf("%5c")+3);
      if(subDir.equals("/images/characters")) {
        this.addCharacterImage(new Image(currentPath));
        characterImageNames.add(name);
      }
      else{
        this.addBackgroundImage(new Image(currentPath));
        backgroundImageNames.add(name);
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
