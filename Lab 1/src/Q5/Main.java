package Q5;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
        TextField txtField = new TextField();
        root.getChildren().add(txtField);
        Button colorButton = new Button("Click to set text");
        root.getChildren().add(colorButton);
        colorButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try{
                    PrintWriter writer = new PrintWriter("Test.txt", "UTF-8");
                    //writer.println("The first line");
                    writer.println(txtField.getText());
                    stage.setTitle(txtField.getText());//extra, sets title as input text
                    writer.close();
                } catch (IOException e) {
                    // do something
                }
            }
        });



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
