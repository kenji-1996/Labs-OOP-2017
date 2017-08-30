package Q1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Button displayBtn = (Button) root.lookup("#displayBtn");
        Label welcomeFX = (Label) root.lookup("#welcomeFX");
        ComboBox cnbBox = (ComboBox) root.lookup("#fontSizeBox");
        RadioButton sansserif = (RadioButton) root.lookup("#sansserif");
        RadioButton serif = (RadioButton) root.lookup("#serif");
        RadioButton mono = (RadioButton) root.lookup("#mono");
        CheckBox checkBold = (CheckBox) root.lookup("#checkBold");
        CheckBox checkItalic = (CheckBox) root.lookup("#checkItalic");
        ToggleGroup tg = new ToggleGroup();
        sansserif.setToggleGroup(tg);
        sansserif.setUserData("Sans Serif");
        serif.setToggleGroup(tg);
        serif.setUserData("Serif");
        mono.setToggleGroup(tg);
        mono.setUserData("Monospaced");

        if (displayBtn != null) displayBtn.setText("Display");
        displayBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (displayBtn!=null && welcomeFX!=null && cnbBox!=null && sansserif!=null && serif!=null && mono!=null && checkBold!=null && checkItalic!=null) {
                    if(checkItalic.isSelected() && checkBold.isSelected()) {
                        welcomeFX.setFont(Font.font(tg.getSelectedToggle().getUserData().toString(),(checkItalic.isSelected()? FontPosture.ITALIC : FontPosture.REGULAR), Double.parseDouble(cnbBox.getValue().toString())));
                    }else if(checkBold.isSelected()){
                        welcomeFX.setFont(Font.font(tg.getSelectedToggle().getUserData().toString(),(checkBold.isSelected()? FontWeight.BOLD : FontWeight.NORMAL), Double.parseDouble(cnbBox.getValue().toString())));
                    }else{
                        welcomeFX.setFont(Font.font(tg.getSelectedToggle().getUserData().toString(),(checkBold.isSelected()? FontWeight.BOLD : FontWeight.NORMAL), Double.parseDouble(cnbBox.getValue().toString())));
                    }
                }
            }
        });

        for(int i = 5; i < 55; i+=5) {
            if (cnbBox!=null) cnbBox.getItems().add("" + i);
        }


        primaryStage.setTitle("FontSelectorV1");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
