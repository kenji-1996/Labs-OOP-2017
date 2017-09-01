package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class Controller {

    @FXML public TableView<MusicAlbum> albumTable;
    @FXML public TableColumn<MusicAlbum, String> idCol;
    @FXML public TableColumn<MusicAlbum, String> nameCol;
    @FXML public TableColumn<MusicAlbum, String> genreCol;
    @FXML public TableColumn<MusicAlbum, Boolean> compilationCol;
    @FXML public TableColumn<MusicAlbum, Integer> tracksCol;
    @FXML public TextField filterField;
    @FXML public BorderPane root;

    @FXML
    public void initialize() {
        //Set columns
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        compilationCol.setCellValueFactory(new PropertyValueFactory<>("isCompilation"));
        tracksCol.setCellValueFactory(new PropertyValueFactory<>("trackCount"));
        System.out.println("Information loaded");
        populateTable();
    }

    @FXML
    private void filterTable(ActionEvent event) {
        genreCol.setSortType(TableColumn.SortType.ASCENDING);
        tracksCol.setSortType(TableColumn.SortType.DESCENDING);
        albumTable.getSortOrder().add(genreCol);
        albumTable.getSortOrder().add(tracksCol);
    }

    @FXML
    private void resetFilter(ActionEvent event) {
        albumTable.getSortOrder().clear();
        albumTable.sort();
    }

    @FXML
    private void addAlbum(ActionEvent event) {
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Add");
        dialog.setHeaderText("Add new album");
        dialog.setResizable(true);

        Label idLbl = new Label("ID: ");
        Label nameLbl = new Label("Name: ");
        Label genreLbl = new Label("Genre: ");
        Label compilationLbl = new Label("Compilation: ");
        Label countLbl = new Label("Track Count: ");
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField genreField = new TextField();
        CheckBox compilationCheck = new CheckBox();
        TextField countField = new TextField();


        GridPane grid = new GridPane();

        grid.add(idLbl, 1, 1);
        grid.add(idField, 2, 1);

        grid.add(nameLbl, 1, 2);
        grid.add(nameField, 2, 2);

        grid.add(genreLbl,1,3);
        grid.add(genreField,2,3);

        grid.add(compilationLbl,1,4);
        grid.add(compilationCheck,2,4);

        grid.add(countLbl,1,5);
        grid.add(countField,2,5);

        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        Optional<ButtonType> result = dialog.showAndWait();
        MusicAlbumDSC dsc = new MusicAlbumDSC();

        if (result.isPresent() && !idField.getText().isEmpty() && !nameField.getText().isEmpty() && !genreField.getText().isEmpty() && !countField.getText().isEmpty()) {
            dsc.add(new MusicAlbum(idField.getText(),nameField.getText(),genreField.getText(),compilationCheck.isSelected(),Integer.parseInt(countField.getText())));
            //Main.albums.add(new MusicAlbum(idField.getText(),nameField.getText(),genreField.getText(),compilationCheck.isSelected(),Integer.parseInt(countField.getText())));
            System.out.println("Album " + nameField.getText() + " added!");
        }else{
            System.out.println("Not enough information/Closed with 'X'");
        }
        populateTable();
    }

    @FXML
    private void removeAlbum(ActionEvent event) {
        if(!albumTable.getSelectionModel().isEmpty()) {
            MusicAlbum album = albumTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Confirmation");
            alert.setContentText("Are you sure you want to delete");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                System.out.println(album.toString());
                MusicAlbumDSC dsc = new MusicAlbumDSC();
                dsc.remove(album.getId());
                //Main.albums.remove(album);
                populateTable();
            }
        }
    }

    private void populateTable() {
        MusicAlbumDSC dsc = new MusicAlbumDSC();
        ObservableList<MusicAlbum> data = FXCollections.<MusicAlbum>observableArrayList(dsc.getAll());
        FilteredList<MusicAlbum> filteredData = new FilteredList<>(data, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(MusicAlbum -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (MusicAlbum.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<MusicAlbum> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(albumTable.comparatorProperty());
        albumTable.setItems(sortedData);
        albumTable.refresh();
    }
}
