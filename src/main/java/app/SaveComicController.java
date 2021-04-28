package app;

import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class SaveComicController {

    Controller controller;

    public SaveComicController(Controller controller){
        this.controller = controller;
    }

    @FXML
    void createXML() throws IOException {

        TextInputDialog fileNameInput = new TextInputDialog();
        fileNameInput.setTitle("Name your Comic");
        fileNameInput.setHeaderText("");
        fileNameInput.setContentText("Enter a file name:");
        fileNameInput.showAndWait();
        String fileName = fileNameInput.getEditor().getText();

        if(!fileName.equals("")){
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Save Comic");
            chooser.showSaveDialog(null);
            String filePath = chooser.getSelectedFile().toString();

            File file = new File(filePath + "\\" + fileName + ".xml");
            file.createNewFile();

            saveAsXML(file);
        }
    }

    public void saveAsXML(File file) {

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("comic");
            document.appendChild(root);

            for(int counter=0;counter<controller.getLowerPanelController().comicPanelList.size();counter++) {

                Element panel = document.createElement("panel" + counter);
                root.appendChild(panel);
                Comic comic = controller.getLowerPanelController().comicPanelList.get(counter);

                if (comic.getLeftCharacter() != null) {
                    // character element
                    Element character = document.createElement("characterLeft");

                    panel.appendChild(character);

                    // position element
                    Element position = document.createElement("position");
                    position.appendChild(document.createTextNode("Left"));
                    character.appendChild(position);

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

                    //pose element
                    Element pose = document.createElement("pose");
                    pose.appendChild(document.createTextNode(String.valueOf(comic.getLeftCharacter().getChosenImage())));
                    character.appendChild(pose);

                    //facing element
                    Element direction = document.createElement("direction");
                    direction.appendChild(document.createTextNode(String.valueOf(comic.getLeftCharacter().getFacing())));
                    character.appendChild(direction);
                }
                if (comic.getRightCharacter() != null) {
                    // character element
                    Element character = document.createElement("characterRight");

                    panel.appendChild(character);

                    // position element
                    Element position = document.createElement("position");
                    position.appendChild(document.createTextNode("Right"));
                    character.appendChild(position);

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

                    //pose element
                    Element pose = document.createElement("pose");
                    pose.appendChild(document.createTextNode(String.valueOf(comic.getRightCharacter().getChosenImage())));
                    character.appendChild(pose);

                    //facing element
                    Element direction = document.createElement("direction");
                    direction.appendChild(document.createTextNode(String.valueOf(comic.getRightCharacter().getFacing())));
                    character.appendChild(direction);
                }

                if (comic.getBackground() != null) {
                    Element background = document.createElement("background");
                    panel.appendChild(background);
                    background.appendChild(document.createTextNode(String.valueOf(comic.getChosenBackground())));
                }

                if (comic.getTopText() != null || comic.getBottomText() != null || comic.getLeftText() != null ||comic.getRightText() != null) {
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
