package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Recipe Database");
        primaryStage.setScene(new Scene(root, 751, 482));
        primaryStage.show();

        /*Parent root2 = FXMLLoader.load(getClass().getResource("viewRecipe.fxml"));
        primaryStage.setTitle("Recipe Update/View");
        primaryStage.setScene(new Scene(root2, 328, 400));
        primaryStage.show();*/

    }


    public static void main(String[] args) {
        launch(args);
    }
}
