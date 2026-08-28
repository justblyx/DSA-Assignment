import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class NavigationController extends Application {
    // JavaFX 
    private static Stage stage;
    private static Pane graphPane;
    private static Font bold = Font.font("Arial", FontWeight.BOLD, 14);
    private static Font normal = Font.font("Arial", 14);

    private static NavigationGraph graph;
    private static HashMap<Planet, Circle> planetCircles;

    // BFS variables & elements
    private static VBox bfsPanel;
    private static ArrayList<Planet> bfsResult;
    private static int bfsStep;

    private static Label currentLabel;
    private static Label visitedLabel;
    private static Label stepLabel;

    private static Button nextStepButton;
    private static Button resetButton;

    public static void setGraph(NavigationGraph navigationGraph) { graph = navigationGraph; }

    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);

        stage = primaryStage;
        stage.setTitle("Interplanetary Navigation System");

        graphPane = new Pane();
        graphPane.setPrefSize(800, 550);

        currentLabel = new Label("Current: -");
        visitedLabel = new Label("Traversal: []");
        stepLabel = new Label("Step: -");

        currentLabel.setFont(bold);
        visitedLabel.setFont(normal);
        stepLabel.setFont(normal);
        
        nextStepButton = new Button("Next Step");
        resetButton = new Button("Reset");
        nextStepButton.setOnAction(event -> {
            nextBFSStep();
        });

        resetButton.setOnAction(event -> {
            resetBFS();
        });
        
        HBox buttonBox = new HBox(10, nextStepButton, resetButton);

        buttonBox.setAlignment(Pos.CENTER);

        bfsPanel = new VBox(8, currentLabel, visitedLabel, stepLabel, buttonBox);

        bfsPanel.setAlignment(Pos.CENTER);
        bfsPanel.setVisible(false);
        bfsPanel.setManaged(false);

        VBox root = new VBox(10, graphPane, bfsPanel);

        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 900, 700);

        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            stage.hide();
            event.consume();
        });
    }

    public static void showGraph() {
        if (stage == null) return;

        Platform.runLater(() -> {
            bfsPanel.setVisible(false);
            bfsPanel.setManaged(false);

            drawGraph();

            resetBFS();

            stage.setTitle("Interplanetary Navigation System - Graph");

            stage.show();
            stage.toFront();
            stage.requestFocus();
        });
    }

    public static void showBFS(String start) {
        if (stage == null) return;
        Platform.runLater(() -> {
            if (graph == null) return;

            bfsResult = graph.bfs(start);
            
            if (bfsResult.isEmpty()) {
                currentLabel.setText("Current: Planet not found");
                visitedLabel.setText("Traversal: []");
                stepLabel.setText("Step: -");

                return;
            }

            bfsStep = 0;
            bfsPanel.setVisible(true);
            bfsPanel.setManaged(true);

            drawGraph();
            resetBFS();

            currentLabel.setText("Current: " + bfsResult.get(0).getName());
            visitedLabel.setText("Traversal: []");
            stepLabel.setText("Step: 0/ " + bfsResult.size());
            stage.setTitle("Interplanetary Navigation System - BFS Traversal");

            stage.show();
            stage.toFront();
            stage.requestFocus();
        });

    }

    private static void drawGraph() {
        if (graphPane == null) return;

        graphPane.getChildren().clear();

        if (graph == null) return;

        planetCircles = new HashMap<>();

        ArrayList<Planet> planets = graph.getPlanets();

        HashMap<Planet, Point2D> positions = new HashMap<>();

        HashMap<Planet, ArrayList<Planet>> adjacencyList = graph.getAdjacencyList();

        ArrayList<String> drawnRoutes = new ArrayList<>();

        // Assign positions to each planets
        HashMap<String, Point2D> fixedPositions = new HashMap<>();

        fixedPositions.put("Mercury", new Point2D(400, 60));
        fixedPositions.put("Venus", new Point2D(550, 120));
        fixedPositions.put("Earth", new Point2D(620, 275));
        fixedPositions.put("Mars", new Point2D(550, 430));
        fixedPositions.put("Jupiter", new Point2D(400, 490));
        fixedPositions.put("Saturn", new Point2D(250, 430));
        fixedPositions.put("Uranus", new Point2D(180, 275));
        fixedPositions.put("Neptune", new Point2D(250, 120));

        for (Planet p: planets) {
            Point2D position = fixedPositions.get(p.getName());

            if (position != null) positions.put(p, position);
        }
        
        // Draw routes
        for (Planet src: adjacencyList.keySet()) {
            for (Planet dest: adjacencyList.get(src)) {
                String route1 = src.getName() + " - " + dest.getName();
                String route2 = dest.getName() + " - " + src.getName();

                if (drawnRoutes.contains(route1) || drawnRoutes.contains(route2)) {
                    continue;
                }

                Point2D start = positions.get(src);
                Point2D end = positions.get(dest);

                if (start != null && end != null) {
                    Line line  = new Line(start.getX(), start.getY(), end.getX(), end.getY());

                    line.setStroke(Color.GRAY);
                    line.setStrokeWidth(2);

                    graphPane.getChildren().add(line);
                    drawnRoutes.add(route1);
                }
            }
        }

        // Add planets as circles
        for (Planet p: planets) {
            Point2D position = positions.get(p);

            Circle circle = new Circle(position.getX(), position.getY(), 30);

            circle.setFill(Color.LIGHTBLUE);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);

            planetCircles.put(p, circle);

            Label label = new Label(p.getName());
            label.setFont(bold);
            label.setLayoutX(position.getX() - 15);

            label.setLayoutY(position.getY() + 35);

            graphPane.getChildren().addAll(circle, label);
        }
    }

    private static void nextBFSStep() {
        if (bfsResult == null) return;

        // check if it's finished
        if (bfsStep >= bfsResult.size()) {
            currentLabel.setText("Current: BFS Complete");
            stepLabel.setText("Step: " + bfsResult.size() + " / " + bfsResult.size());
            return;
        }

        Planet current = bfsResult.get(bfsStep);
        if (bfsStep > 0) {
            Planet previous = bfsResult.get(bfsStep - 1);
            Circle previousCircle = planetCircles.get(previous);

            if (previousCircle != null) {
                previousCircle.setFill(Color.LIGHTGREEN);
            }
        }
        
        Circle currentCircle = planetCircles.get(current);
        if (currentCircle != null) {
            currentCircle.setFill(Color.ORANGE);
        }

        ArrayList<Planet> visited = new ArrayList<>();

        for (int i=0; i <=bfsStep; i++) {
            visited.add(bfsResult.get(i));
        }

        currentLabel.setText("Current: " + current.getName());
        visitedLabel.setText("Traversal: " + visited);
        stepLabel.setText("Step: " + (bfsStep + 1) + " / " + bfsResult.size());

        bfsStep++;

        if (bfsStep >= bfsResult.size()) {
            currentLabel.setText("Current: " + current.getName() + " (Complete)");
        }
    }

    private static void resetBFS() {
        bfsStep = 0;

        if (planetCircles != null) {
            for (Circle c: planetCircles.values()) {
                c.setFill(Color.LIGHTBLUE);
            }
        }

        if (currentLabel != null) {
            currentLabel.setText("Current : - ");
        }

        if (visitedLabel != null) {
            visitedLabel.setText("Traversal: []");
        }

        if (stepLabel != null) {
            stepLabel.setText("Step: -");
        }
    }
}