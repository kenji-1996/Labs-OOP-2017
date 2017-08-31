package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class Controller {
    @FXML
    public TableView<Recipe> recipeTable;
    @FXML public TableColumn<Recipe, Integer> idCol;
    @FXML public TableColumn<Recipe, String> nameCol;
    @FXML public TableColumn<Recipe, Integer> servesCol;
    @FXML public TableColumn<Recipe, String> ingredientsCol;
    @FXML public TableColumn<Recipe, String> remarksCol;
    @FXML public TextField searchTxt;
    @FXML public BorderPane root;
    @FXML public ToggleGroup sortType;
    @FXML public RadioButton anyRadio;
    @FXML public RadioButton ingredientRadio;
    @FXML public RadioButton recipeRadio;


    @FXML
    public void initialize() {
        //Set columns
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        servesCol.setCellValueFactory(new PropertyValueFactory<>("serves"));
        ingredientsCol.setCellValueFactory(new PropertyValueFactory<>("compiledIngredients"));
        remarksCol.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        populateTable();
    }

    public void populateTable() {
        RecipeDSC dsc = new RecipeDSC();
        ObservableList<Recipe> data = FXCollections.<Recipe>observableArrayList(dsc.findAll());
        FilteredList<Recipe> filteredData = new FilteredList<>(data, p -> true);
        searchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Recipe -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(sortType.getSelectedToggle().equals(recipeRadio)) {
                    if (Recipe.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }else if(sortType.getSelectedToggle().equals(anyRadio)) {
                    if (Recipe.getName().toLowerCase().contains(lowerCaseFilter) || Recipe.getRemarks().toLowerCase().contains(lowerCaseFilter) || Recipe.getCompiledIngredients().toLowerCase().contains(lowerCaseFilter) || (Recipe.getID() +"").toLowerCase().contains(lowerCaseFilter) || (Recipe.getServes() +"").toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }else if(sortType.getSelectedToggle().equals(ingredientRadio)) {
                    if (Recipe.getIngredients().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
        });
        SortedList<Recipe> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(recipeTable.comparatorProperty());
        recipeTable.setItems(sortedData);
        recipeTable.refresh();
    }

    @FXML
    private void refreshTable(ActionEvent event) {
        populateTable();
    }

    @FXML
    private void updateRecipe(ActionEvent event) {
        if(!recipeTable.getSelectionModel().isEmpty()) {
            Recipe recipe = recipeTable.getSelectionModel().getSelectedItem();
            try{
                int ID = recipe.getID();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewRecipe.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Recipe modifier");
                ControllerRecipe controller = fxmlLoader.<ControllerRecipe>getController();
                controller.getID(ID);
                stage.setScene(new Scene(root1, 328, 400));
                stage.addEventHandler(WindowEvent.ANY, new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        populateTable();
                    }
                });
                stage.show();
            } catch (Exception ex) { }
            populateTable();
        }
    }

    @FXML
    private void addRecipe(ActionEvent event) {
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Add");
        dialog.setHeaderText("Add new recipe");
        dialog.setResizable(true);

        Label nameLbl = new Label("Name: ");
        TextField nameField = new TextField();

        Label servesLbl = new Label("Serves: ");
        TextField servesField = new TextField();

        Label stepsLbl = new Label("Steps: ");
        TextField stepsField = new TextField();

        Label remarksLbl = new Label("Remarks: ");
        TextField remarksField = new TextField();

        GridPane grid = new GridPane();

        grid.add(nameLbl, 1, 1);
        grid.add(nameField, 2, 1);

        grid.add(servesLbl, 1, 2);
        grid.add(servesField, 2, 2);

        grid.add(stepsLbl,1,3);
        grid.add(stepsField,2,3);

        grid.add(remarksLbl,1,4);
        grid.add(remarksField,2,4);

        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && !nameField.getText().isEmpty() && !servesField.getText().isEmpty() && !stepsField.getText().isEmpty() && !remarksField.getText().isEmpty()) {
            RecipeDSC dsc = new RecipeDSC();
            int resultID = dsc.addRecipe(nameField.getText(),Integer.parseInt(servesField.getText()),stepsField.getText(),remarksField.getText());
            System.out.println("Recipe " + resultID + " added!");
            populateTable();
        }else{
            System.out.println("Not enough information/Missformatted");
        }
    }

    @FXML
    private void removeRecipe(ActionEvent event) {
        if(!recipeTable.getSelectionModel().isEmpty()) {
            Recipe recipe = recipeTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Confirmation");
            alert.setContentText("Are you sure you want to delete recipe " + recipe.getID());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                RecipeDSC dsc = new RecipeDSC();
                dsc.deleteRecipe(recipe.getID());
                populateTable();
            }
        }
    }
}
