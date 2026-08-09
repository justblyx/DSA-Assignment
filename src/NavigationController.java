import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.HashMap;

public class NavigationController extends Application {
    private static Stage stage;
    private static NavigationGraph graph;

    private static Pane graphPane;

    public static void setGraph(NavigationGraph navigationGraph) { graph = navigationGraph; }

    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);

        stage = primaryStage;
        stage.setTitle("Interplanetary Navigation System");

        graphPane = new Pane();
        Scene scene = new Scene(graphPane, 800, 600);

        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            stage.hide();
            event.consume();
        });
    }

    public static void showGraph() {
        if (stage == null) return;

        Platform.runLater(() -> {
            drawGraph();

            stage.show();
            stage.toFront();
            stage.requestFocus();
        });
    }

    private static void drawGraph() {
        if (graphPane == null) return;

        graphPane.getChildren().clear();

        if (graph == null) return;

        ArrayList<Planet> planets = graph.getPlanets();
        HashMap<Planet, Point2D> positions = new HashMap<>();
        HashMap<Planet, ArrayList<Planet>> adjacencyList = graph.getAdjacencyList();
        ArrayList<String> drawnRoutes = new ArrayList<>();

        // Assign positions to each planets
        for (int i=0; i<planets.size(); i++) {
            Planet p = planets.get(i);

            double x = 100 + (i % 4) * 180;
            double y = 100 + (i / 4) * 150;

            positions.put(p, new Point2D(x, y));
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

                    graphPane.getChildren().add(line);
                    drawnRoutes.add(route1);
                }
            }
        }

        // Add planets as circles
        for (Planet p: planets) {
            Point2D position = positions.get(p);

            Circle circle = new Circle(position.getX(), position.getY(), 30);

            Label label = new Label(p.getName());

            label.setLayoutX(position.getX() - 15);

            label.setLayoutY(position.getY() + 35);

            graphPane.getChildren().addAll(circle, label);
        }
    }
}