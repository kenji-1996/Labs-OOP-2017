package Q6;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Main extends Application {

    private int i = 0;
    private GridPane gpnael = new GridPane();
    @Override
    public void start(Stage stage) throws Exception {
        // center
        VBox vbCenter = new VBox(); // use any container as center pane e.g. VBox
        TextField console = new TextField();
        vbCenter.getChildren().add(console);

        // bottom respectively "button area"
        HBox hbButtons = new HBox();
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setPadding(new Insets(10, 10, 10, 10));
        int count = 1;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                Button tempBtn = new Button("" + count);
                tempBtn.setId(""+count);
                tempBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        console.setText(console.getText() + tempBtn.getId());
                    }
                });
                grid.add(tempBtn, j, i);
                count++;
            }
        }
        Button zeroBtn = new Button("0");
        zeroBtn.setId("0");
        zeroBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                console.setText(console.getText() + zeroBtn.getId());
            }
        });
        grid.add(zeroBtn, 1, 4);
        Button addBtn = new Button("+");
        addBtn.setId("+");
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                console.setText(console.getText() + addBtn.getId());
            }
        });
        grid.add(addBtn, 0, 4);
        Button minusBtn = new Button("-");
        minusBtn.setId("-");
        minusBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                console.setText(console.getText() + minusBtn.getId());
            }
        });
        grid.add(minusBtn, 2, 4);
        Button multiBtn = new Button("*");
        multiBtn.setId("*");
        multiBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                console.setText(console.getText() + multiBtn.getId());
            }
        });
        grid.add(multiBtn, 0, 5);
        Button divideBtn = new Button("/");
        divideBtn.setId("/");
        divideBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                console.setText(console.getText() + divideBtn.getId());
            }
        });
        grid.add(divideBtn, 1, 5);
        Button calculateBtn = new Button("=");
        calculateBtn.setId("=");
        calculateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ScriptEngineManager mgr = new ScriptEngineManager();
                    ScriptEngine engine = mgr.getEngineByName("JavaScript");//engine.eval(console.getText())
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"" + engine.eval(console.getText()), ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.OK) {
                        //do stuff
                    }
                } catch (Exception EX) { }
                //console.setText(console.getText() + calculateBtn.getId());
            }
        });
        grid.add(calculateBtn, 2, 5);
        hbButtons.getChildren().add(grid);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20)); // space between elements and window border
        root.setCenter(vbCenter);
        root.setBottom(hbButtons);
        //root.setRight();

        Scene scene = new Scene(root, 300, 200);

        stage.setTitle("Wiki Scraper");
        stage.setScene(scene);
        stage.show();
    }

    public void buttonAction() {

    }

    public static void main(String[] args) {
        launch(args);
    }
}