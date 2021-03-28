# Beta-Pirates
Repository for Software Engineering Project 3

## Beta-Pirates Members
- Dáire Fagan 14749125
- Lukasz Filanowski 18414616
- Cal Nolan 18355103
- Conor Fowler 18458476

## Old Files added in previous Sprints
- Main.java- runs the application
- Controller.java - will be used later for functionality implementation
- main.fxml - contains fxml for the application main window 
- test.fxml - used as a test file to experiment with new layout consepts without interfering/breaking any finished implementations.
- resource folder- contains character poses and button images

## New Files added/updated in Sprint 2
- Controller.java - contains functionality for adding the characters into the pannels as well as rotating these characters once inserted.
- testController.java - used for testing new implementations and ideas without affecting old working ones. 
- main.fxml - contains fxml for the application main window.
- help.txt - a file containing useful information for the user on how to use the application.
- help.fxml - simple stage where the help.txt will be displayed to the user.
- resource folder- added icon for the application in the top left conrner once launched.

## Instructions on setting up and running the project
Java version: 8

The following link outlines ways to set up the JavaFX project if versions other than Java 8 are used. You will need to add a library which is pointing to the lib file of the JavaFX SDK as well as edit the VM options (The VM options only need to be edited if a version other than Java 8 is used). The link walks through this process for a variety of IDEs and Java versions.
https://openjfx.io/openjfx-docs/

For IntelliJ users: File >> Project Structure >> Libraries >> '+' icon and navigate to the lib file of your Java SDK

To edit VM options: Edit >> Edit Configurations >> Enter relevant command from the article into the VM options box.

We have created a .jar file in the root directory of the project which runs when ran from inside an IDE like Intellij as well as command line. Navigate to the root project folder and use the command: java -jar Beta_Pirates.jar. However, the .jar file must remain in this directory in order to run properly. It still runs from within the IDE which can be done by right clicking on the .jar file and selecting "Run Beta_Pirates.jar".

## General Info about implementation of Sprint 2

The user can insert the characters into the left or right pannels of the comic by pressing the corresponding character insertion buttons found at the top 
of the rightmost section. Once the button is pressed, a separate window of the file explorer appears which opens a directory containig all character poses. The user can then select whichever character they wish and press open to insert them into the pannel. The character inserted into the right hand side is automatically rotated to face the
character on the left. When a character is inserted, they can be selected(indicated by a highlight around the border of the image) and rotated using the rotation button. The rotate button is greyed out when the application is launched as it only becomes active once a character is selected. The top bar has a help section which displays a set of instructions for the user on how to use the application. The application window now also launches with an icon in the top left corner of the screen.


