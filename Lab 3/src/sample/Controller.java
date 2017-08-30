package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static javax.swing.UIManager.get;


public class Controller {

    @FXML
    private TextField idTxt,nameTxt,genreTxt,trackCountTxt;
    /*@FXML
    private Button loadDataBtn,displayAlbumBtn,searchAlbumBtn,addAlbumBtn,removeAlbumBtn,saveDataBtn;*/
    @FXML
    private TextArea albumDisplayTxt;
    @FXML
    private ComboBox isCompilationCombo;



    @FXML
    private void loadData(ActionEvent event) {
        try {
            MusicCatalogDS catDS = new MusicCatalogDS();
            final int size = 20; // Can also try larger size
            String[] id = new String[size];
            for (int i = 0; i < size; i++) {
                id[i] = catDS.generateId();
                MusicAlbum ma = new MusicAlbum(id[i], "name" + i, "genre" + i, true, i);
                catDS.add(ma);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Albums generated on list.", ButtonType.OK);
            alert.setHeaderText("Album Status");
            alert.showAndWait();
        } catch(Exception ex) { }
        displayAlbum(event);
    }

    @FXML
    private void displayAlbum(ActionEvent event) {
        albumDisplayTxt.setText("");
        for(MusicAlbum ma: MusicCatalogDS.albums) {
            albumDisplayTxt.setText(albumDisplayTxt.getText() + ma.toString() + "\n");
        }
    }

    @FXML
    private void searchAlbum(ActionEvent event) {
        //Custom text dialog snippet provided by: http://code.makery.ch/blog/javafx-dialogs-official/
        TextInputDialog dialog = new TextInputDialog("A1");
        dialog.setTitle("Album search");
        dialog.setHeaderText("Album search tool");
        dialog.setContentText("Please enter the album ID (Not case sensitive):");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String albumResult = "";
            MusicAlbum ma = null;
            for (MusicAlbum album : MusicCatalogDS.albums)
            {
                if (album.getId().equalsIgnoreCase(result.get()))
                {
                    ma = album;
                }
            }
            if(ma != null) {
                albumResult = ma.toString();
                System.out.println(albumResult);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Album found! [" + ma.toString() + "]", ButtonType.OK);
                alert.setHeaderText("Album Status");
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No album under the ID:" + result.get(), ButtonType.OK);
                alert.setHeaderText("Album Status");
                alert.showAndWait();
            }
            //"Your name: " + result.get());
        }
    }

    @FXML
    private void addAlbum(ActionEvent event) {
        if (idTxt.getText().isEmpty() || nameTxt.getText().isEmpty() || genreTxt.getText().isEmpty() || trackCountTxt.getText().isEmpty() || isCompilationCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "None of the album information can be blank", ButtonType.OK);
            alert.setHeaderText("Album Status");
            alert.showAndWait();
        } else {
            boolean isCompilation;
            if(isCompilationCombo.getValue().toString().equalsIgnoreCase("true")) {
                isCompilation = true;
            }else{
                isCompilation = false;
            }
            MusicAlbum ma = new MusicAlbum(idTxt.getText(), nameTxt.getText(), genreTxt.getText(), isCompilation, Integer.parseInt(trackCountTxt.getText().trim()));
            MusicCatalogDS.albums.add(ma);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Album " + nameTxt.getText() + " has been added.", ButtonType.OK);
            alert.setHeaderText("Album Status");
            alert.showAndWait();
        }
    }

    @FXML
    private void removeAlbum(ActionEvent event) {
        //Custom text dialog snippet provided by: http://code.makery.ch/blog/javafx-dialogs-official/
        TextInputDialog dialog = new TextInputDialog("A1");
        dialog.setTitle("Album removal");
        dialog.setHeaderText("Album removal tool");
        dialog.setContentText("Please enter the album ID (Not case sensitive):");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String albumResult = "";
            MusicAlbum ma = null;
            for (MusicAlbum album : MusicCatalogDS.albums)
            {
                if (album.getId().equalsIgnoreCase(result.get()))
                {
                    ma = album;
                }
            }
            if(ma != null) {
                albumResult = ma.toString();
                System.out.println(albumResult);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Album removed: [" + ma.toString() + "]", ButtonType.OK);
                alert.setHeaderText("Album Status");
                alert.showAndWait();
                MusicCatalogDS.albums.remove(ma);
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No album under the ID: " + result.get(), ButtonType.OK);
                alert.setHeaderText("Album Status");
                alert.showAndWait();
            }
            //"Your name: " + result.get());
        }
    }

    @FXML
    private void saveData(ActionEvent event) {
        try {
            PrintWriter out = new PrintWriter(new File("Albums.txt"));

            for (MusicAlbum album : MusicCatalogDS.albums) {
                out.println(album.toString());
            }
            out.close();
        } catch (IOException ex) { ex.printStackTrace(); }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Data has been saved to 'Albums.txt'", ButtonType.OK);
        alert.setHeaderText("Album Status");
        alert.showAndWait();
    }

}
