# Beta-Pirates
Repository for Software Engineering Project 3

## Beta-Pirates Members
- Dáire Fagan 14749125
- Lukasz Filanowski 18414616
- Cal Nolan 18355103
- Conor Fowler 18458476

## Old Files added in previous Sprints
- testController.java - used for testing new implementations and ideas without affecting old working ones. 
- main.fxml - contains fxml for the application main window.
- help.txt - a file containing useful information for the user on how to use the application.
- help.fxml - simple stage where the help.txt will be displayed to the user.
-test.fxml - used to test new implementations in the layout of the application without breaking/interfering with already working features.
- resource folder- added icon for the application in the top left conrner once launched.
- Controller.java - contains functionality for changing the character's gender as well as their hair/body colour using colour picker as well as new way to insert
  characters via a selection scroll pane in middle pannel. 
- testController.java - used for testing new implementations and ideas without affecting old working ones. 
- Character.java - a class containing data of each new character object such as their hair colour, gender etc.
- ImageLists.java - a class that contains the list of all character images and background images as well as a method to access them from the controller which has been 
edited. 
- Comic.java - a class that contains data of the current state of the comic such as which pannel currently has which character etc.  
- TextGraphic.java - experimental class which will be used in future to convert text to images. 
- directoryList.txt - a text file which contains the paths of images in the resources/images/characters folder. This is used in order to allow the jar to reference 
 the character images correctly without crashing
 - backgroundList.txt - a text file which contains the paths of images in the resources/images/backgrounds folder. This is used in order to allow the jar to reference 
 the background images correctly without crashing
 - TestImageLists - a method that tests the ImageLists class (work in progress).


## New Files added/updated in Sprint 6
- Controller.java - contains functionality for saving/loading, editing and deleting a panel from the bottom section of the application.
- testController.java - used for testing new implementations and ideas without affecting old working ones. 
- ButtonController.java - Contains functionality relating to the buttons in the application
- CharacterController.java - Contains functionality relating to changing the state of the character image (excluding colours).
- ColourController.java - Contains functionality relating to changing colours and colour alteration/checking
- ComicController.java - Contains functionality for inserting images into the comic panel.
- LowerPanelController.java - Contains functionality for the bottom section of the application responsible for saving/loading/deleting and displaying the saved 
  panels.
- MidScrollPaneController.java - Contains functionality for the middle panel of the application which displays all available characters and backgrounds. 
- main.fxml - contains fxml for the application main window.
-test.fxml - used to test new implementations in the layout of the application without breaking/interfering with already working features.
- help.txt - a file containing useful information for the user on how to use the application.
- help.fxml - simple stage where the help.txt will be displayed to the user.

 
 
## Instructions on setting up and running the project
Java version: 8
JUnit version: 4

The following link outlines ways to set up the JavaFX project if versions other than Java 8 are used. You will need to add a library which is pointing to the lib file of the JavaFX SDK as well as edit the VM options (The VM options only need to be edited if a version other than Java 8 is used). The link walks through this process for a variety of IDEs and Java versions.
https://openjfx.io/openjfx-docs/

For IntelliJ users: File >> Project Structure >> Libraries >> '+' icon and navigate to the lib file of your Java SDK

To edit VM options: Run >> Edit Configurations >> Enter relevant command from the article into the VM options box.

We have created a .jar file in the root directory of the project which runs when ran from inside an IDE like Intellij as well as command line. Navigate to the root 
project folder and use the command: java -jar Beta_Pirates.jar. It still runs from within the IDE which can be done by right clicking on the .jar file and selecting 
"Run Beta_Pirates.jar".

In order to make the JUnit test class work, add junit/junit/4.12 to the class path. IDES like IntelliJ should prompt to do this automatically when you hover over
the Test annotations or import statements.

## General Info about implementation of Sprint 6

In this week's sprint we worked on the main implementation as well as dividing the controller into separate controllers, as per the feedback on previous sprints.
The program now contains 6 "sub controllers" and one main Controller which runs the application.

Once the user has created the comic, they can save the current panel by pressing Ctrl + S or by going to File >> Save Panel in the top menu bar. Onve saved, the panel
will display at the bottom of the application and the working panel will be cleared to enable the creation of a new panel. The user can select the saved panel from 
the bottom section and either load it back into the working space to edit (L) or delete it from the saved list (Delete). The currently loaded panel is indicated by an 
orange border in the bottom section.

For more information on how the program works, go to the help menu in the application. 
