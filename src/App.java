import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        Button btn = new Button("click me cuz y not");

        btn.setOnAction(e -> {
            System.out.println("nice");
        });

        VBox root = new VBox(10);
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 200);

        stage.setTitle("Solar System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}