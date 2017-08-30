package Q3;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Main extends Application {

    @Override
    public void start(Stage stage)
    {
        build(stage);
        stage.setTitle("HelloFX");
        stage.show();
    }
    public void build(Stage stage)
    {
        VBox root = new VBox();
        Label helloFX = new Label("Hello JavaFX!");
        root.setAlignment( Pos.CENTER);
        root.getChildren().add(helloFX);
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add("css/style.css");
        stage.setScene(scene);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
