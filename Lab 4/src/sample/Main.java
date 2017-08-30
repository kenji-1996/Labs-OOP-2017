package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {

    public static ArrayList<MusicAlbum> albums = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("MusicCatalogFXVersion2");
        primaryStage.setScene(new Scene(root, 465, 326));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
