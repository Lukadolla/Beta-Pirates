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

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javafx.fxml.FXML;

import javax.swing.*;

public class LoadComicController {

    Controller controller;

    public LoadComicController(Controller controller) {
        this.controller = controller;
    }

    @FXML
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

            if(controller.getMidScrollPaneController().characterImages == null){
                controller.getMidScrollPaneController().loadCharacterImages();
            }

            if(controller.getMidScrollPaneController().backgroundImages == null){
                controller.getMidScrollPaneController().loadBackgroundImages();
            }

            ArrayList<String> characters = controller.getMidScrollPaneController().imageLists.getCharacterImageNames();
            ArrayList<String> backgrounds = controller.getMidScrollPaneController().imageLists.getBackgroundImageNames();
            NodeList list = doc.getElementsByTagName("panel");

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);

                Element element = (Element) node;
                Element Left = (Element) element.getElementsByTagName("characterLeft").item(0);
                Element Right = (Element) element.getElementsByTagName("characterRight").item(0);
                Element Background = (Element) element.getElementsByTagName("background").item(0);
                Element Text = (Element) element.getElementsByTagName("text").item(0);

                if (Left != null) {
                    String chosenImage = Left.getElementsByTagName("chosenImage").item(0).getTextContent();
                    try {
                        Character character = new Character(controller.getMidScrollPaneController().imageLists.getCharacterImages().get(characters.indexOf(chosenImage)), chosenImage);
                        character.setGender(Left.getElementsByTagName("gender").item(0).getTextContent());
                        character.setFacing(Integer.parseInt(Left.getElementsByTagName("direction").item(0).getTextContent()));
                        character.setSkinColour(Color.web(Left.getElementsByTagName("skinColour").item(0).getTextContent()));
                        character.setMaleHairColour(Color.web(Left.getElementsByTagName("maleHairColour").item(0).getTextContent()));
                        character.setFemaleHairColour(Color.web(Left.getElementsByTagName("femaleHairColour").item(0).getTextContent()));

                        Node bubble = Left.getElementsByTagName("bubble").item(0);
                        controller.getComicController().insertLeftCharacter(character);
                        if (bubble != null) {
                            if (bubble.getTextContent().equals("speech")) {
                                controller.getButtonController().addSpeechBubble();
                            } else if (bubble.getTextContent().equals("thought")) {
                                controller.getButtonController().addThoughtBubble();
                            }
                            else{
                                controller.getComicController().clearComic();
                                throw new IllegalArgumentException();
                            }
                        }
                        controller.getColourController().loadSkinColour(character.getSkinColour());
                    } catch(IllegalArgumentException | NullPointerException | ArrayIndexOutOfBoundsException ex){
                        errorMessage("XML file data corrupted - couldn't load file");
                        controller.getComicController().clearComic();
                        return;
                    }
                }

                if (Right != null) {
                    String chosenImage = Right.getElementsByTagName("chosenImage").item(0).getTextContent();
                    try {
                        Node bubble = Right.getElementsByTagName("bubble").item(0);
                        Character character = new Character(controller.getMidScrollPaneController().imageLists.getCharacterImages().get(characters.indexOf(chosenImage)), chosenImage);
                        character.setGender(Right.getElementsByTagName("gender").item(0).getTextContent());
                        character.setFacing(Integer.parseInt(Right.getElementsByTagName("direction").item(0).getTextContent()));
                        character.setSkinColour(Color.web(Right.getElementsByTagName("skinColour").item(0).getTextContent()));
                        character.setMaleHairColour(Color.web(Right.getElementsByTagName("maleHairColour").item(0).getTextContent()));
                        character.setFemaleHairColour(Color.web(Right.getElementsByTagName("femaleHairColour").item(0).getTextContent()));


                        controller.getComicController().insertRightCharacter(character);
                        if (bubble != null) {
                            if (bubble.getTextContent().equals("speech")) {
                                controller.getButtonController().addSpeechBubble();
                            } else if (bubble.getTextContent().equals("thought")) {
                                controller.getButtonController().addThoughtBubble();
                            } else {
                                controller.getComicController().clearComic();
                                throw new IllegalArgumentException();
                            }
                        }
                        controller.getColourController().loadSkinColour(character.getSkinColour());
                    }catch(IllegalArgumentException | NullPointerException ex){
                        errorMessage("XML file data corrupted - couldn't load file");
                        controller.getComicController().clearComic();
                        return;
                    }
                }

                if (Background != null) {
                    try {
                        controller.getComicController().insertBackground(controller.getMidScrollPaneController().backgroundImages.get(backgrounds.indexOf(Background.getTextContent())));
                        controller.comic.setChosenBackground(Background.getTextContent());
                    }catch (Exception ex){
                        controller.getComicController().clearComic();
                        errorMessage("Couldn't Find Background");
                        return;
                    }
                }

                if(Text != null) {
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
            controller.getComicController().clearComic();
            e.printStackTrace();
        }

    }

    void errorMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(message);
        alert.setContentText("");
        alert.showAndWait();
    }

}
