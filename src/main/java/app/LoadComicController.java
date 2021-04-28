package app;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

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

        if(!(filePath.endsWith(".xml"))){
            return;
        }

        File file = new File(filePath);

        readXML(file);
    }

    private void readXML(File file){
        controller.getLowerPanelController().clearComic();

        /*controller.comic.setBackground(panelComic.getBackground());

        controller.comic.setLeftText(panelComic.getLeftText());
        controller.leftTextField.setText(panelComic.getLeftText());

        controller.comic.setRightText(panelComic.getRightText());
        controller.rightTextField.setText(panelComic.getRightText());

        controller.comic.setTopText(panelComic.getTopText());
        controller.topText.setText(panelComic.getTopText());

        controller.comic.setBottomText(panelComic.getBottomText());
        controller.bottomText.setText(panelComic.getBottomText());

        controller.topText.setVisible(true);
        controller.bottomText.setVisible(true);*/
    }

}
