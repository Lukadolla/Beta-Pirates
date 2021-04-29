package app;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;

import javax.swing.*;

public class LoadComicController {

    Controller controller;

    public LoadComicController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    void loadXML() {

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle("Load Comic as XML");
        chooser.showSaveDialog(null);
        String filePath = chooser.getSelectedFile().toString();

        if (!(filePath.endsWith(".xml"))) {
            return;
        }

        File file = new File(filePath);

        loadFromXML(file);
    }

    public void loadFromXML(File file) {

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

            NodeList list = doc.getElementsByTagName("panel");

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                Comic comic = new Comic();

                Element element = (Element) node;
                Element Left = (Element) element.getElementsByTagName("characterLeft").item(0);
                Element Right = (Element) element.getElementsByTagName("characterRight").item(0);
                Element Background = (Element) element.getElementsByTagName("background").item(0);
                Element Text = (Element) element.getElementsByTagName("text").item(0);

                if (Left != null) {
                    int chosenImage = Integer.parseInt(Left.getElementsByTagName("chosenImage").item(0).getTextContent());

                    Character character = new Character(controller.getMidScrollPaneController().characterImages.get(chosenImage), 0, chosenImage);
                    character.setGender(Left.getElementsByTagName("gender").item(0).getTextContent());
                    character.setFacing(Integer.parseInt(Left.getElementsByTagName("direction").item(0).getTextContent()));
                    character.setSkinColour(Color.web(Left.getElementsByTagName("skinColour").item(0).getTextContent()));
                    character.setMaleHairColour(Color.web(Left.getElementsByTagName("maleHairColour").item(0).getTextContent()));
                    character.setFemaleHairColour(Color.web(Left.getElementsByTagName("femaleHairColour").item(0).getTextContent()));

                    controller.comic.setLeftCharacter(character);
                    controller.getComicController().insertLeftCharacter(character.getImage(), chosenImage);
                }

                if (Right != null) {
                    int chosenImage = Integer.parseInt(Right.getElementsByTagName("chosenImage").item(0).getTextContent());

                    Character character = new Character(controller.getMidScrollPaneController().characterImages.get(chosenImage), 1, chosenImage);
                    character.setGender(Right.getElementsByTagName("gender").item(0).getTextContent());
                    character.setFacing(Integer.parseInt(Right.getElementsByTagName("direction").item(0).getTextContent()));
                    character.setSkinColour(Color.web(Right.getElementsByTagName("skinColour").item(0).getTextContent()));
                    character.setMaleHairColour(Color.web(Right.getElementsByTagName("maleHairColour").item(0).getTextContent()));
                    character.setFemaleHairColour(Color.web(Right.getElementsByTagName("femaleHairColour").item(0).getTextContent()));

                    controller.comic.setRightCharacter(character);
                    controller.getComicController().insertRightCharacter(character.getImage(), chosenImage);
                }

                if (Background != null) {
                    controller.comic.setBackground(new ImageView(controller.getMidScrollPaneController().backgroundImages.get(Integer.parseInt(Background.getTextContent()))));
                    controller.getComicController().insertBackground(controller.comic.getBackground().getImage());
                }

                if (Text != null) {
                    controller.comic.setLeftText(Text.getElementsByTagName("LeftText").item(0).getTextContent());
                    controller.leftTextField.setText(controller.comic.getLeftText());

                    controller.comic.setRightText(Text.getElementsByTagName("RightText").item(0).getTextContent());
                    controller.rightTextField.setText(controller.comic.getRightText());

                    controller.comic.setTopText(Text.getElementsByTagName("topText").item(0).getTextContent());
                    controller.topText.setText(controller.comic.getTopText());

                    controller.comic.setBottomText(Text.getElementsByTagName("BottomText").item(0).getTextContent());
                    controller.bottomText.setText(controller.comic.getBottomText());
                }
                controller.getLowerPanelController().addToPanelList();
            }

        } catch (ParserConfigurationException | SAXException | IOException | CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }

}
