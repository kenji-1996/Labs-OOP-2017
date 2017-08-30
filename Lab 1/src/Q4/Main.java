package Q4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

        //Items
        Button colorButton = new Button("Click me to change color");
        root.getChildren().add(colorButton);
        colorButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                colorButton.setTextFill(Color.color(Math.random(), Math.random(), Math.random()));
                //lbl.setText("Hello, World.");
            }
        });
        Label helloFX = new Label("Hello JavaFX!");
        root.getChildren().add(helloFX);

        //Scene iteself
        root.setAlignment( Pos.CENTER);
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add("css/style.css");
        stage.setScene(scene);
    }

    public void controller() {

    }


    public static void main(String[] args) {
        launch(args);
    }
}
