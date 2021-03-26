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
Java version: 13

The imlementation of our project in Java 13 will require to alter the VM Options of the project as well as set up a lib file containing the path to your JavaFX SDK. 

The following link contains instructions on how to set up the project using any Operating System and any popular IDE:
https://openjfx.io/openjfx-docs/

## General Info about implementation of Sprint 2

The user can insert the characters into the left or right pannels of the comic by pressing the corresponding character insertion buttons found at the top 
of the rightmost section. Once the button is pressed, a separate window of the file explorer appears which opens a directory containig all character poses. The user can then select whichever character they wish and press open to insert them into the pannel. The character inserted into the right hand side is automatically rotated to face the
character on the left. When a character is inserted, they can be selected(indicated by a highlight around the border of the image) and rotated using the rotation button. The rotate button is greyed out when the application is launched as it only becomes active once a character is selected. The top bar has a help section which displays a set of instructions for the user on how to use the application. The application window now also launches with an icon in the top left corner of the screen.


