import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SolarSystem extends Application {
    @Override
    public void start(Stage stage) {
        VBox root = new VBox(10);

        /*Button btn = new Button("click me cuz y not");

        btn.setOnAction(e -> {
            System.out.println("nice");
        });

        root.getChildren().add(btn);*/
        
        Scene scene = new Scene(root, 640, 640);

        stage.setTitle("Solar System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}