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

public class Main_OLD extends Application {

    public static ArrayList<MusicAlbum> albums = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("MusicCatalogFXVersion2");
        primaryStage.setScene(new Scene(root, 465, 326));
        primaryStage.show();

        //Database
        String url = "jdbc:mysql://localhost:3306/java";
        String username = "root";
        String password = "";
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
