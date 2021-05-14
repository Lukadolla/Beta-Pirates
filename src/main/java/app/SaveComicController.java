package app;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class SaveComicController {

    Controller controller;

    public SaveComicController(Controller controller){
        this.controller = controller;
    }

    @FXML
    void createXML() {  //Method called when Save as XML menu item is pressed which prompts user to input file name and directory

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("XML Details");
        dialog.setHeaderText("");

        // Set the button types.
        ButtonType submitButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        // Create the name labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField comicName = new TextField();
        comicName.setPromptText("Comic Name");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(comicName, 1, 0);

        // Enable/Disable save button depending on whether a name was entered.
        Node submitButton = dialog.getDialogPane().lookupButton(submitButtonType);
        submitButton.setDisable(true);

        // Do some validation.
        comicName.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the name field by default.
        Platform.runLater(comicName::requestFocus);

        // Convert the result to a String when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                return comicName.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(details -> {

            if(!details.equals("")){
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setDialogTitle("Save Comic");
                    chooser.showSaveDialog(null);
                    String filePath = chooser.getSelectedFile().toString();

                    File file = new File(filePath + "/" + details + ".xml");
                    saveAsXML(file);

                } catch(Exception ignored){
                }
            }
        });
    }

    @FXML
    void createHTML() {  //Method called when Save as HTML menu item is pressed which prompts user to input file name, description and directory

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Details");
        dialog.setHeaderText("");

        // Set the button types.
        ButtonType submitButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        // Create the description and name labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField comicName = new TextField();
        comicName.setPromptText("Comic Name");
        TextField comicDescription = new TextField();
        comicDescription.setPromptText("Comic Description");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(comicName, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(comicDescription, 1, 1);

        // Enable/Disable save button depending on whether a name was entered.
        Node submitButton = dialog.getDialogPane().lookupButton(submitButtonType);
        submitButton.setDisable(true);

        // Do some validation.
        comicName.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the name field by default.
        Platform.runLater(comicName::requestFocus);

        // Convert the result to a pair when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                return new Pair<>(comicName.getText(), comicDescription.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(details -> {

            if(!details.getKey().equals("")){
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setDialogTitle("Save Comic");
                    chooser.showSaveDialog(null);
                    String filePath = chooser.getSelectedFile().toString();

                    File directory = new File(filePath + "/" + details.getKey());
                    directory.mkdir();

                    File file = new File(directory + "/" + details.getKey() + ".html");

                    saveAsHTML(file, details.getKey(), details.getValue());
                    saveComicAsImages(directory.toString(), details.getKey());
                    //controller.getLowerPanelController().GIFTest(directory.toString(), details.getKey());
                }
                catch(Exception ignored){
                }
            }
        });
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
                    chosenImage.appendChild(document.createTextNode(comic.getLeftCharacter().getChosenImage()));
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
                    chosenImage.appendChild(document.createTextNode(comic.getRightCharacter().getChosenImage()));
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

                Element background = document.createElement("background");
                panel.appendChild(background);

                if (comic.getBackground() != null) {
                    background.appendChild(document.createTextNode(comic.getChosenBackground()));
                }
                else{
                    background.appendChild(document.createTextNode("default.png"));
                }

                if (!(comic.getTopText().equals("") && comic.getBottomText().equals("") && comic.getLeftText().equals("") && comic.getRightText().equals(""))) {
                    Element text = document.createElement("text");
                    panel.appendChild(text);
                    if (!comic.getTopText().trim().equals("")) {
                        Element topText = document.createElement("topText");
                        text.appendChild(topText);
                        topText.appendChild(document.createTextNode(comic.getTopText().trim()));
                    }
                    if (!comic.getBottomText().trim().equals("")) {
                        Element bottomText = document.createElement("bottomText");
                        text.appendChild(bottomText);
                        bottomText.appendChild(document.createTextNode(comic.getBottomText().trim()));
                    }
                    if (!comic.getLeftText().trim().equals("")) {
                        Element leftText = document.createElement("leftText");
                        text.appendChild(leftText);
                        leftText.appendChild(document.createTextNode(comic.getLeftText().trim()));
                    }
                    if (!comic.getRightText().trim().equals("")) {
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

        } catch (NullPointerException | ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    private void saveComicAsImages(String filePath, String fileName) throws IOException { //Method to save the comic panels as images in a separate directory to the HTML

        File imageDirectory = new File(filePath + "/" + fileName + "images");
        imageDirectory.mkdir();

        for(int image = 0; image < controller.getLowerPanelController().comicPanelList.size(); image++){

            File imageFile = new File(imageDirectory + "/" + fileName + image + ".png");
            ImageIO.write(SwingFXUtils.fromFXImage(controller.getLowerPanelController().comicPanelList.get(image).getComicImage(), null), "png", imageFile);
        }
    }

    public void saveAsHTML(File file, String fileName, String description) throws IOException {  //Method that takes in the panels as images and creates a HTML file

        file.createNewFile();


        FileWriter writer = new FileWriter(file);

        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>"+ fileName +"</title>\n" +
                "\n" +
                "</head>\n" +
                "<body style=\"background-color: #bbc4c4\">\n" +
                "\n" +
                "<h1 style=\"text-align: center\">" + description + "</h1>" +
                "\n" +
                "<table style=\"margin-left: auto; margin-right: auto; font-family: georgia, garamond, serif; border-spacing: 20px\">\n");

        for(int image = 0; image < controller.getLowerPanelController().comicPanelList.size(); image++){

            String imagePath = fileName + "images" + "/" + fileName + image + ".png";

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

    public void saveAsGIF(File file) throws IOException {
        if(controller.getLowerPanelController().comicPanelList.size() <= 0){
            return;
        }

        ImageOutputStream output = new FileImageOutputStream(file);

        BufferedImage first = SwingFXUtils.fromFXImage(controller.getLowerPanelController().comicPanelList.get(0).getComicImage(), null);

        GifSequenceWriter writer = new GifSequenceWriter(output, first.getType(), 6000, true);
        writer.writeToSequence(first);

        for(int i = 1; i < controller.getLowerPanelController().comicPanelList.size(); i++){
            BufferedImage current = SwingFXUtils.fromFXImage(controller.getLowerPanelController().comicPanelList.get(i).getComicImage(), null);
            writer.writeToSequence(current);
        }

        writer.close();
        output.close();
    }

    public void createGIF(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("GIF Details");
        dialog.setHeaderText("");

        // Set the button types.
        ButtonType submitButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        // Create the name labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField gifName = new TextField();
        gifName.setPromptText("GIF Name");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(gifName, 1, 0);

        // Enable/Disable save button depending on whether a name was entered.
        Node submitButton = dialog.getDialogPane().lookupButton(submitButtonType);
        submitButton.setDisable(true);

        // Do some validation.
        gifName.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the name field by default.
        Platform.runLater(gifName::requestFocus);

        // Convert the result to a String when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                return gifName.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(details -> {

            if(!details.equals("")){
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setDialogTitle("Save GIF");
                    chooser.showSaveDialog(null);
                    String filePath = chooser.getSelectedFile().toString();

                    File file = new File(filePath + "/" + details + ".gif");
                    file.createNewFile();
                    saveAsGIF(file);

                } catch(Exception ignored){
                }
            }
        });
    }
}
