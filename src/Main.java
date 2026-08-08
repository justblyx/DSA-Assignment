import javafx.application.Application;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static String seperator = "=----------------------------------------------------------------------=";

    enum MenuState {
        MAIN_MENU,
        ADD_GRAPH,
        TRAVERSE_GRAPH,
        DISPLAY_GRAPH,
        EXIT
    }

    public static String padding(int count) {
        return " ".repeat(count);
    }

    public static int getInt() {
        try {
            System.out.print(padding(20) + "Your choice: ");
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(padding(20) + "Invalid input, press any key to try again...");
            scanner.nextLine();
            scanner.nextLine();
            return -1;
        }
        
    }

    public static void main(String[] args) {
        Thread fxThread = new Thread(() -> {
            Application.launch(NavigationController.class);
        });

        fxThread.start();

        MenuState currentState = MenuState.MAIN_MENU;
        int input = 0;

        while (currentState != MenuState.EXIT) {
            switch (currentState) {
                case MAIN_MENU:
                    System.out.println(seperator);
                    System.out.println("|" + padding(19) + "Interplanetary Navigation System" + padding(19) + "|");
                    System.out.println(seperator);

                    System.out.println(padding(20) + "1. Create Graph");
                    System.out.println(padding(20) + "2. Search for Traversal Path");
                    System.out.println(padding(20) + "3. View the Graph");
                    System.out.println(padding(20) + "4. Exit\n");

                    input = getInt();
                    if (input == -1) continue;

                    switch(input) {
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
                            break;
                        default:
                            System.out.println(padding(20) + "Invalid input, press any key to try again...");
                            scanner.nextLine();
                            scanner.nextLine();
                            break;
                    }
                    break;

                case ADD_GRAPH:
                    System.out.println(seperator);
                    System.out.println("|" + padding(12) + "Create Graph: Enter 1-4 for updating the graph" + padding(12) + "|") ;
                    System.out.println(seperator);
                    System.out.println(padding(20) + "1. Add vertex");
                    System.out.println(padding(20) + "2. Remove vertex");
                    System.out.println(padding(20) + "3. Add edge");
                    System.out.println(padding(20) + "4. Remove edge");
                    System.out.println(padding(20) + "5. Return to Main Menu\n");

                    input = getInt();
                    if (input == -1) continue;
                    
                    switch(input) {
                        case 1:
                            // add vertex
                            break;
                        case 2:
                            // remove vertex
                            break;
                        case 3:
                            // add edge
                            break;
                        case 4:
                            // remove edge
                            break;
                        case 5:
                            currentState = MenuState.MAIN_MENU;
                            break;
                        default:
                            System.out.println(padding(20) + "Invalid input, press any key to try again...");
                            scanner.nextLine();
                            scanner.nextLine();
                            break;
                    }

                    break;
                case TRAVERSE_GRAPH:
                    break;
                case DISPLAY_GRAPH:
                    NavigationController.showGraph();
                    System.out.println("\n" + padding(20) + "Press any key to continue...");
                    scanner.nextLine();
                    scanner.nextLine();
                    currentState = MenuState.MAIN_MENU;
                    
                    break;
                case EXIT:
                    break;
                default:
                    currentState = MenuState.MAIN_MENU;
                    break;
            }
        }

        scanner.close();
    }
}