# Beta-Pirates
Repository for Software Engineering Project 3

## Beta-Pirates Members
- Dáire Fagan 14749125
- Lukasz Filanowski 18414616
- Cal Nolan 18355103
- Conor Fowler 18458476

## Old Files added in previous Sprints
- Controller.java - contains functionality for adding the characters into the pannels as well as rotating these characters once inserted.
- testController.java - used for testing new implementations and ideas without affecting old working ones. 
- main.fxml - contains fxml for the application main window.
- help.txt - a file containing useful information for the user on how to use the application.
- help.fxml - simple stage where the help.txt will be displayed to the user.
- resource folder- added icon for the application in the top left conrner once launched.


## New Files added/updated in Sprint 3
- Controller.java - contains functionality for changing the character's gender as well as their hair/body colour using colour picker as well as new way to insert
  characters via a selection scroll pane in middle pannel. 
- testController.java - used for testing new implementations and ideas without affecting old working ones. 
- main.fxml - contains fxml for the application main window.
- help.txt - a file containing useful information for the user on how to use the application.
- help.fxml - simple stage where the help.txt will be displayed to the user.
- Character.java - a class containing data of each new character object such as their hair colour, gender etc.
- CharacterList.java - a class that contains the list of all character images as well as a method to access them from the controller.
- Comic.java - a class that contains data of the current state of the comic such as which pannel currently has which character etc.  

## Instructions on setting up and running the project
Java version: 8

The following link outlines ways to set up the JavaFX project if versions other than Java 8 are used. You will need to add a library which is pointing to the lib file of the JavaFX SDK as well as edit the VM options (The VM options only need to be edited if a version other than Java 8 is used). The link walks through this process for a variety of IDEs and Java versions.
https://openjfx.io/openjfx-docs/

For IntelliJ users: File >> Project Structure >> Libraries >> '+' icon and navigate to the lib file of your Java SDK

To edit VM options: Run >> Edit Configurations >> Enter relevant command from the article into the VM options box.

We have created a .jar file in the root directory of the project which runs when ran from inside an IDE like Intellij as well as command line. Navigate to the root project folder and use the command: java -jar Beta_Pirates.jar. However, the .jar file must remain in this directory in order to run properly. It still runs from within the IDE which can be done by right clicking on the .jar file and selecting "Run Beta_Pirates.jar".

## General Info about implementation of Sprint 3

The character models can now be inserted without the use of a file explorer. Instead the middle panel will load all the character images once a character insertion button
is pressed. The desired character can be clicked in the menu and will be loaded into the corresponding pannel. A blue border indicates that the character is selected. Note that 
the buttons with functionality currently will remain greyed out until you select a character and insert them into the panel. Once selected, the character's gender can be changed 
by pressing the M/F button. This might be a little slow initially. The character's hair and body colours cab be changed using the colour pickers below the buttons. You can pick 
a pre-made colour or press the "Custom Color" link to create your own colour. The anti-aliasing removal gets rid of most anti-aliasing around the hair as well as the lips. 

For more information on how the program works, go to the help menu. 
link 

