package app;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveComicController {

    Controller controller;

    public SaveComicController(Controller controller){
        this.controller = controller;
    }

    @FXML
    void createXML() {  //Method called when Save as XML menu item is pressed which prompts user to input file name and directory

        TextInputDialog fileNameInput = new TextInputDialog();
        fileNameInput.setTitle("Name your Comic");
        fileNameInput.setHeaderText("");
        fileNameInput.setContentText("Enter a file name:");
        fileNameInput.showAndWait();
        String fileName = fileNameInput.getEditor().getText();

        if(!fileName.equals("")){
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogTitle("Save Comic");
                chooser.showSaveDialog(null);
                String filePath = chooser.getSelectedFile().toString();

                File file = new File(filePath + "\\" + fileName + ".xml");
                saveAsXML(file);

            } catch(Exception e){
                return;
            }

        }
    }

    @FXML
    void createHTML() throws IOException {  //Method called when Save as HTML menu item is pressed which prompts user to input file name and directory

        TextInputDialog fileNameInput = new TextInputDialog();
        fileNameInput.setTitle("Name your Comic");
        fileNameInput.setHeaderText("");
        fileNameInput.setContentText("Enter a file name:");
        fileNameInput.showAndWait();
        String fileName = fileNameInput.getEditor().getText();

        if(!fileName.equals("")){
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogTitle("Save Comic");
                chooser.showSaveDialog(null);
                String filePath = chooser.getSelectedFile().toString();

                File directory = new File(filePath + "\\" + fileName);
                directory.mkdir();

                File file = new File(directory + "\\" + fileName + ".html");

                saveAsHTML(file, fileName);
                saveComicAsImages(directory.toString(), fileName);
            }
            catch(Exception e){
                return;
            }
        }
    }

    private void saveComicAsImages(String filePath, String fileName) throws IOException {

        File imageDirectory = new File(filePath + "\\" + fileName + "images");
        imageDirectory.mkdir();

        for(int image = 0; image < controller.getLowerPanelController().comicPanelList.size(); image++){

            File imageFile = new File(imageDirectory + "\\" + fileName + image + ".png");
            ImageIO.write(SwingFXUtils.fromFXImage(controller.getLowerPanelController().comicPanelList.get(image).getComicImage(), null), "png", imageFile);
        }
    }

    public void saveAsHTML(File file, String fileName) throws IOException {

        file.createNewFile();


        FileWriter writer = new FileWriter(file);

        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>"+ fileName +"</title>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<table style=\"margin-left: 20%; border-spacing: 20px\">\n");

        for(int image = 0; image < controller.getLowerPanelController().comicPanelList.size(); image++){

            String imagePath = fileName + "images" + "\\" + fileName + image + ".png";

            writer.write("\t<tr>\n" +
                    "        <td style=\"border: 5px solid cornflowerblue\">\n" +
                    "            <img src=\"" + imagePath + "\">\n" +
                    "        </td>\n" +
                    "    </tr>\n");
        }

        writer.write("</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>");
        writer.close();
    }


    public void saveAsXML(File file) {  //Method that takes the data saved in the lower panel and saves it to XML
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("comic");
            document.appendChild(root);

            for(int counter=0;counter<controller.getLowerPanelController().comicPanelList.size();counter++) {

                Element panel = document.createElement("panel");
                root.appendChild(panel);
                Comic comic = controller.getLowerPanelController().comicPanelList.get(counter);

                if (comic.getLeftCharacter() != null) {
                    // character element
                    Element character = document.createElement("characterLeft");

                    panel.appendChild(character);

                    // female hair colour element
                    Element femaleHairColour = document.createElement("femaleHairColour");
                    femaleHairColour.appendChild(document.createTextNode(comic.getLeftCharacter().getFemaleHairColour().toString()));
                    character.appendChild(femaleHairColour);

                    // male hair colour element
                    Element maleHairColour = document.createElement("maleHairColour");
                    maleHairColour.appendChild(document.createTextNode(comic.getLeftCharacter().getMaleHairColour().toString()));
                    character.appendChild(maleHairColour);

                    // skin colour elements
                    Element skinColour = document.createElement("skinColour");
                    skinColour.appendChild(document.createTextNode(comic.getLeftCharacter().getSkinColour().toString()));
                    character.appendChild(skinColour);

                    // gender elements
                    Element gender = document.createElement("gender");
                    gender.appendChild(document.createTextNode(comic.getLeftCharacter().getGender()));
                    character.appendChild(gender);

                    //chosenImage element
                    Element chosenImage = document.createElement("chosenImage");
                    chosenImage.appendChild(document.createTextNode(String.valueOf(comic.getLeftCharacter().getChosenImage())));
                    character.appendChild(chosenImage);

                    //facing element
                    Element direction = document.createElement("direction");
                    direction.appendChild(document.createTextNode(String.valueOf(comic.getLeftCharacter().getFacing())));
                    character.appendChild(direction);

                    if(comic.getLeftCharacter().getBubble()!=null) {
                        //speech/thought bubble element
                        Element bubble = document.createElement("bubble");
                        bubble.appendChild(document.createTextNode(comic.getLeftCharacter().getBubble()));
                        character.appendChild(bubble);
                    }
                }
                if (comic.getRightCharacter() != null) {
                    // character element
                    Element character = document.createElement("characterRight");

                    panel.appendChild(character);

                    // female hair colour element
                    Element femaleHairColour = document.createElement("femaleHairColour");
                    femaleHairColour.appendChild(document.createTextNode(comic.getRightCharacter().getFemaleHairColour().toString()));
                    character.appendChild(femaleHairColour);

                    // male hair colour element
                    Element maleHairColour = document.createElement("maleHairColour");
                    maleHairColour.appendChild(document.createTextNode(comic.getRightCharacter().getMaleHairColour().toString()));
                    character.appendChild(maleHairColour);

                    // skin colour elements
                    Element skinColour = document.createElement("skinColour");
                    skinColour.appendChild(document.createTextNode(comic.getRightCharacter().getSkinColour().toString()));
                    character.appendChild(skinColour);

                    // gender elements
                    Element gender = document.createElement("gender");
                    gender.appendChild(document.createTextNode(comic.getRightCharacter().getGender()));
                    character.appendChild(gender);

                    //chosenImage element
                    Element chosenImage = document.createElement("chosenImage");
                    chosenImage.appendChild(document.createTextNode(String.valueOf(comic.getRightCharacter().getChosenImage())));
                    character.appendChild(chosenImage);

                    //facing element
                    Element direction = document.createElement("direction");
                    direction.appendChild(document.createTextNode(String.valueOf(comic.getRightCharacter().getFacing())));
                    character.appendChild(direction);

                    if(comic.getRightCharacter().getBubble()!=null) {
                        //speech/thought bubble element
                        Element bubble = document.createElement("bubble");
                        bubble.appendChild(document.createTextNode(comic.getRightCharacter().getBubble()));
                        character.appendChild(bubble);
                    }
                }

                if (comic.getBackground() != null) {
                    Element background = document.createElement("background");
                    panel.appendChild(background);
                    background.appendChild(document.createTextNode(String.valueOf(comic.getChosenBackground())));
                }
                else{
                    Element background = document.createElement("background");
                    panel.appendChild(background);
                    background.appendChild(document.createTextNode("0"));
                }

                if (!(comic.getTopText().equals("")) || !(comic.getBottomText().equals("")) || !(comic.getLeftText().equals("")) || !(comic.getRightText().equals(""))) {
                    Element text = document.createElement("text");
                    panel.appendChild(text);
                    if (comic.getTopText() != null && !comic.getTopText().trim().equals("")) {
                        Element topText = document.createElement("topText");
                        text.appendChild(topText);
                        topText.appendChild(document.createTextNode(comic.getTopText().trim()));
                    }
                    if (comic.getBottomText() != null && !comic.getBottomText().trim().equals("")) {
                        Element bottomText = document.createElement("bottomText");
                        text.appendChild(bottomText);
                        bottomText.appendChild(document.createTextNode(comic.getBottomText().trim()));
                    }
                    if (comic.getLeftText() != null && !comic.getLeftText().trim().equals("")) {
                        Element leftText = document.createElement("leftText");
                        text.appendChild(leftText);
                        leftText.appendChild(document.createTextNode(comic.getLeftText().trim()));
                    }
                    if (comic.getRightText() != null && !comic.getRightText().trim().equals("")) {
                        Element rightText = document.createElement("rightText");
                        text.appendChild(rightText);
                        rightText.appendChild(document.createTextNode(comic.getRightText().trim()));
                    }
                }
            }
            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }
}
