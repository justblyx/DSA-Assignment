import javafx.application.Application;
import javafx.application.Platform;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static int CLI_SIZE = 90;

    enum MenuState {
        MAIN_MENU,
        ADD_GRAPH,
        TRAVERSE_GRAPH,
        DISPLAY_GRAPH,
        EXIT
    }

    // Utility methods for displaying
    public static String padded(String message) {
        int left = (CLI_SIZE - message.length()) / 2;
        int right = CLI_SIZE - message.length() - left;

        return " ".repeat(left) + message + " ".repeat(right);
    }

    public static String padded(String message, int size) { 
        int left = (size - message.length()) / 2;
        int right = size - message.length() - left;

        return " ".repeat(left) + message + " ".repeat(right);
    }

    public static String padded(String message, boolean rightPadding) {
        int left = (CLI_SIZE - message.length()) / 2;
        int right = rightPadding ? CLI_SIZE - message.length() - left : 0;

        return " ".repeat(left) + message + " ".repeat(right);
    }

    public static String seperator(int size) { return "=" + "-".repeat(size-2) + "="; }

    public static void feedback(String message) {
        System.out.println("\n" + padded(message));
        System.out.println(padded("Press enter to continue..."));
        scanner.nextLine();
    }

    public static void feedback(String message, int padding) {
        System.out.println("\n" + padded(message, padding));
        System.out.println(padded("Press enter to continue...", padding));
        scanner.nextLine();
    }

    public static int getInt() {
        int input;
        try {
            System.out.print(padded("Your choice: ", false));
            input = scanner.nextInt();
            scanner.nextLine();
            return input;
        } catch (InputMismatchException e) {
            feedback("Invalid input!");
            return -1;
        }
        
    }

    public static String getString(String prompt) {
        String input;
        while (true) {
            System.out.print(padded(prompt, false));
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                feedback("Input cannot be empty!");
                continue;
            }

            return input;
        }
    }

    public static void main(String[] args) {
        Thread fxThread = new Thread(() -> {
            Application.launch(NavigationController.class);
        });

        fxThread.start();

        NavigationGraph graph = new NavigationGraph();
        MenuState currentState = MenuState.MAIN_MENU;
        int choice = 0;
        String input, src, dest;

        while (currentState != MenuState.EXIT) {
            switch (currentState) {
                case MAIN_MENU:
                    System.out.println(seperator(CLI_SIZE));
                    System.out.println("|" + padded("Interplanetary Navigation System", CLI_SIZE-2) + "|");
                    System.out.println(seperator(CLI_SIZE));

                    System.out.println(padded("Main Menu"));
                    System.out.println(padded(seperator(20)));
                    System.out.println(padded("1. Create Graph"));
                    System.out.println(padded("2. BFS Traversal"));
                    System.out.println(padded("3. View the Graph"));
                    System.out.println(padded("4. Exit\n"));

                    choice = getInt();
                    if (choice == -1) continue;

                    switch(choice) {
                        case 1:
                            currentState = MenuState.ADD_GRAPH;
                            break;
                        case 2:
                            currentState = MenuState.TRAVERSE_GRAPH;
                            break;
                        case 3:
                            currentState = MenuState.DISPLAY_GRAPH;
                            break;
                        case 4:
                            currentState = MenuState.EXIT;
                            System.out.println(padded("Successfully exited the program."));
                            break;
                        default:
                            feedback("Invalid input");
                            break;
                    }
                    break;

                case ADD_GRAPH:
                    System.out.println(seperator(CLI_SIZE));
                    System.out.println("|" + padded("Create Graph: Enter 1-5 for updating the graph", CLI_SIZE-2) + "|") ;
                    System.out.println(seperator(CLI_SIZE));
                    System.out.println(padded("Options"));
                    System.out.println(padded(seperator(20)));
                    System.out.println(padded("1. Add vertex (planet)"));
                    System.out.println(padded("2. Remove vertex (planet)"));
                    System.out.println(padded("3. Add edge (route)"));
                    System.out.println(padded("4. Remove edge (route)"));
                    System.out.println(padded("5. Return to Main Menu\n"));

                    choice = getInt();
                    if (choice == -1) continue;
                    
                    switch(choice) {
                        case 1:
                            input = getString("Enter a planet name (or 0 to exit): ");
                            if (input.equals("0")) continue;
                            graph.addPlanet(input);
                            break;
                        case 2:
                            input = getString("Enter a planet name (or 0 to exit): ");
                            if (input.equals("0")) continue;
                            graph.removePlanet(input);
                            break;
                        case 3:
                            src = getString("Enter source planet (or 0 to exit): ");
                            if (src.equals("0")) continue;
                            dest = getString("Enter destination planet: ");
                            graph.addRoute(src, dest);
                            break;
                        case 4:
                            // remove edge
                            src = getString("Enter source planet (or 0 to exit): ");
                            if (src.equals("0")) continue;
                            dest = getString("Enter destination planet: ");
                            graph.removeRoute(src, dest);
                            break;
                        case 5:
                            currentState = MenuState.MAIN_MENU;
                            break;
                        default:
                            feedback("Invalid input");
                            scanner.nextLine();
                            break;
                    }

                    break;

                case TRAVERSE_GRAPH:
                    if (graph.getPlanets().isEmpty()) {
                        feedback("There are no planets in the graph!");
                        currentState = MenuState.MAIN_MENU;
                        break;
                    }

                    String startPlanet = getString("Enter starting planet (or 0 to exit): ");
                    if (startPlanet.equals("0")) { 
                        currentState = MenuState.MAIN_MENU;
                        break;
                    }

                    NavigationController.setGraph(graph);
                    NavigationController.showBFS(startPlanet);

                    System.out.println("\n" + padded("Please close the JavaFX window and press Enter to continue..."));
                    scanner.nextLine();

                    currentState = MenuState.MAIN_MENU;
                    break;

                case DISPLAY_GRAPH:
                    NavigationController.setGraph(graph);
                    NavigationController.showGraph();
                    System.out.println("\n" + padded("Please close the JavaFX window and press Enter to continue..."));
                    scanner.nextLine();
                    currentState = MenuState.MAIN_MENU;
                    break;

                default:
                    currentState = MenuState.MAIN_MENU;
                    break;
            }
        }

        Platform.exit();
        scanner.close();
    }
}