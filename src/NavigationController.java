import javafx.application.Application;
import javafx.application.Platform;
// import javafx.scene.Scene;
// import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NavigationController extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Graph Visualization");
    }

    public static void showGraph() {
        Platform.runLater(() -> {
            stage.show();
            stage.toFront();
        });
    }
}