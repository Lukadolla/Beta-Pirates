package app.controllers;

import app.Character;
import app.Comic;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class LoadComicController {

    Controller controller;

    public LoadComicController(Controller controller) {
        this.controller = controller;
    }

    void loadXML() throws CloneNotSupportedException {  //Method called when Load XML menu item is pressed which prompts user to select file to load

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle("Load Comic as XML");
        FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
                "xml files (*.xml)", "xml");
        chooser.setFileFilter(xmlfilter);
        chooser.showOpenDialog(null);
        String filePath;

        try {
            filePath = chooser.getSelectedFile().toString();
        }
        catch(Exception e){
            return;
        }

        if (!(filePath.endsWith(".xml"))) {
            errorMessage("File could not be opened");
            return;
        }

        File file = new File(filePath);

        Comic currentComic = (Comic) controller.comic.clone();

        loadFromXML(file);

        controller.comic = (Comic) currentComic.clone();

        controller.getComicController().drawComic(currentComic);
    }

    public void loadFromXML(File file) { //Method that takes in an XML comic file and saves the data into the lower panel

        controller.getComicController().clearComic();

        controller.getLowerPanelController().comicPanelList.clear();
        controller.getLowerPanelController().loadBottomPanel();

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();

            if (controller.getMidScrollPaneController().characterImages == null) {
                controller.getMidScrollPaneController().loadCharacterImages();
            }

            if (controller.getMidScrollPaneController().backgroundImages == null) {
                controller.getMidScrollPaneController().loadBackgroundImages();
            }

            ArrayList<String> backgrounds = controller.getMidScrollPaneController().imageLists.getBackgroundImageNames();
            NodeList list = doc.getElementsByTagName("panel");
            if (list.getLength() == 0) {
                errorMessage("Couldn't load file");
            }

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);

                Element element = (Element) node;
                Element Left = (Element) element.getElementsByTagName("characterLeft").item(0);
                Element Right = (Element) element.getElementsByTagName("characterRight").item(0);
                Element Background = (Element) element.getElementsByTagName("background").item(0);
                Element Text = (Element) element.getElementsByTagName("text").item(0);

                if (Left != null) {
                    try {
                        controller.getComicController().insertLeftCharacter(loadCharacter(Left));
                    } catch (IllegalArgumentException | NullPointerException ex) {
                        controller.getComicController().clearComic();
                        errorMessage("XML file data corrupted - couldn't load file");
                        return;
                    }
                }

                if (Right != null) {
                    try {
                        controller.getComicController().insertRightCharacter(loadCharacter(Right));
                    } catch (IllegalArgumentException | NullPointerException ex) {
                        controller.getComicController().clearComic();
                        errorMessage("XML file data corrupted - couldn't load file");
                        return;
                    }
                }

                if (Background != null) {
                    try {
                        controller.getComicController().insertBackground(controller.getMidScrollPaneController().backgroundImages.get(backgrounds.indexOf(Background.getTextContent())));
                        controller.comic.setChosenBackground(Background.getTextContent());
                    } catch (Exception ex) {
                        errorMessage("Couldn't Find Background");
                        return;
                    }
                }

                if (Text != null) {
                    if (Text.getElementsByTagName("leftText").item(0) != null) {
                        controller.comic.setLeftText(Text.getElementsByTagName("leftText").item(0).getTextContent());
                        controller.leftTextField.setText(controller.comic.getLeftText());
                        controller.getComicController().insertLeftTextGraphic(controller.comic.getLeftText());
                    }
                    if (Text.getElementsByTagName("rightText").item(0) != null) {
                        controller.comic.setRightText(Text.getElementsByTagName("rightText").item(0).getTextContent());
                        controller.rightTextField.setText(controller.comic.getRightText());
                        controller.getComicController().insertRightTextGraphic(controller.comic.getRightText());
                    }
                    if (Text.getElementsByTagName("topText").item(0) != null) {
                        controller.comic.setTopText(Text.getElementsByTagName("topText").item(0).getTextContent());
                        controller.topText.setText(controller.comic.getTopText());
                    }
                    if (Text.getElementsByTagName("bottomText").item(0) != null) {
                        controller.comic.setBottomText(Text.getElementsByTagName("bottomText").item(0).getTextContent());
                        controller.bottomText.setText(controller.comic.getBottomText());
                    }
                }

                controller.getLowerPanelController().addToPanelList();
            }

        } catch (ParserConfigurationException | SAXException | IOException | CloneNotSupportedException | URISyntaxException e) {
            errorMessage("File corrupted - couldn't load comic");
        }
    }

    Character loadCharacter(Element node){
        String chosenImage = node.getElementsByTagName("chosenImage").item(0).getTextContent();
        Node bubble = node.getElementsByTagName("bubble").item(0);
        Character character = new Character(controller.getMidScrollPaneController().imageLists.getCharacterImages().get(controller.getMidScrollPaneController().imageLists.getCharacterImageNames().indexOf(chosenImage)), chosenImage);
        character.setGender(node.getElementsByTagName("gender").item(0).getTextContent());
        character.setFacing(Integer.parseInt(node.getElementsByTagName("direction").item(0).getTextContent()));
        character.setSkinColour(Color.web(node.getElementsByTagName("skinColour").item(0).getTextContent()));
        character.setMaleHairColour(Color.web(node.getElementsByTagName("maleHairColour").item(0).getTextContent()));
        character.setFemaleHairColour(Color.web(node.getElementsByTagName("femaleHairColour").item(0).getTextContent()));


        controller.getComicController().insertRightCharacter(character);
        if (bubble != null) {
            if (bubble.getTextContent().equals("speech")) {
                controller.getButtonController().addSpeechBubble();
            } else if (bubble.getTextContent().equals("thought")) {
                controller.getButtonController().addThoughtBubble();
            } else {
                throw new IllegalArgumentException();
            }
        }
        controller.getColourController().loadSkinColour(character.getSkinColour());
        return character;
    }

    void errorMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(message);
        alert.setContentText("");
        alert.showAndWait();
    }

}
