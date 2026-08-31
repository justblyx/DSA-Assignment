## PLEASE READ IF HAVING ISSUES WITH RUNNING THE PROGRAM
Prerequisites

Ensure the following are installed:
- Java Development Kit (JDK) 25.0.2
(Verify by opening a terminal and running `java -version` or `javac -version`)

- JavaFX SDK 26.0.2 (included within project folder)
- Visual Studio Code (Main IDE)
- Extension Pack for Java for VSCode

Then:
- Open the project in Visual Studio Code (Main IDE used to develop this system)
- Ensure JDK 25.0.2 is configured as the project's Java JDK.
(VSCode: Ctrl + Shift P -> Search for Java: Configure Java Runtime -> Under Installed JDKS, select jdk 25.0.2)
- Ensure vmArgs in launch.json is as following:
`"vmArgs": "--module-path \"${workspaceFolder}/javafx/lib\" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics"`

How to Run
1. Open Main.java
2. Right Click -> Run Java

javasdk
## Folder Structure

The workspace contains several folders, where:

- `src`: the folder to maintain source code
- `javafx`: the folder to maintain dependencies for javafx
- `bin`: compiled output files generated

Interplanetary-Navigation-System/
├── .vscode/ 
│   └── launch.json 
│   └── settings.json
│
├── javafx/
│   └── bin/
│   └── lib/
├── src/ 
    ├── AbstractGraph.java 
    ├── Graph.java 
    ├── Main.java 
    ├── NavigationController.java 
    ├── NavigationGraph.java 
    └── Planet.java 


## Program Details

Title: Interplanetary Navigation System

Features:
- Add and remove planets (vertices)
- Add and remove routes between planets (edges)
- View the adjacency list
- Perform Breadth-First Search (BFS) traversal
- Visualise the planet network using JavaFX
- Visualise BFS traversal using JavaFX
- Input validation and error handling
- Default planet network for demonstration
