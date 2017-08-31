package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;


public class ControllerRecipe {
    @FXML
    public TableView<Ingredient> ingredientsTable;
    @FXML public TableColumn<Ingredient, String> nameCol;
    @FXML public TableColumn<Ingredient, Double> quantityCol;
    @FXML public TableColumn<Ingredient, String> unitCol;
    @FXML public TextField idTxt;
    @FXML public TextField nameTxt;
    @FXML public TextField servesTxt;
    @FXML public TextField remarksTxt;
    @FXML public TextArea stepsTxt;
    public int id;
    public Recipe selectedRecipe;

    @FXML
    public void initialize() {
        //Set columns
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unitsAndStyle"));
    }

    public void populateInformation() {
        RecipeDSC dsc = new RecipeDSC();
        selectedRecipe = dsc.findRecipe(id);
        updateTable();
        idTxt.setText(id + "");
        nameTxt.setText(selectedRecipe.getName());
        servesTxt.setText(selectedRecipe.getServes() + "");
        remarksTxt.setText(selectedRecipe.getRemarks());
        stepsTxt.setText(selectedRecipe.getSteps());
    }

    public void updateTable() {
        RecipeDSC dsc = new RecipeDSC();
        ingredientsTable.getItems().setAll(dsc.findAllIngredients(id));
        ingredientsTable.refresh();
    }

    public void getID(int id){
        System.out.println(id + "");
        this.id = id;
        populateInformation();
    }

    @FXML
    private void updateRecipe(ActionEvent event) {
        RecipeDSC dsc = new RecipeDSC();
        int resultID = dsc.updateRecipe(id,nameTxt.getText(),Integer.parseInt(servesTxt.getText()),stepsTxt.getText(),remarksTxt.getText());
        Node node = (Node)event.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addIngredient(ActionEvent event) {
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Add");
        dialog.setHeaderText("Add new ingredient");
        dialog.setResizable(true);

        Label nameLbl = new Label("Name: ");
        TextField nameField = new TextField();

        Label quantityLbl = new Label("Quantity: ");
        TextField quantityField = new TextField();

        Label unitsLbl = new Label("Units: ");
        TextField unitsField = new TextField();

        GridPane grid = new GridPane();

        grid.add(nameLbl, 1, 1);
        grid.add(nameField, 2, 1);

        grid.add(quantityLbl, 1, 2);
        grid.add(quantityField, 2, 2);

        grid.add(unitsLbl,1,3);
        grid.add(unitsField,2,3);

        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && !nameField.getText().isEmpty() && !quantityField.getText().isEmpty() && !unitsField.getText().isEmpty()) {
            RecipeDSC dsc = new RecipeDSC();
            int resultID = dsc.addIngredient(id,nameField.getText(),Double.parseDouble(quantityField.getText()),unitsField.getText());
            System.out.println("Ingredient " + resultID + " added!");
            updateTable();
        }else{
            System.out.println("Not enough information/Missformatted");
        }
    }

    @FXML
    private void editIngredient(ActionEvent event) {
        if(!ingredientsTable.getSelectionModel().isEmpty()) {
            Ingredient ingredient = ingredientsTable.getSelectionModel().getSelectedItem();
            Dialog dialog = new Dialog<>();
            dialog.setTitle("Edit");
            dialog.setHeaderText("Edit current ingredient");
            dialog.setResizable(true);

            Label nameLbl = new Label("Name: ");
            TextField nameField = new TextField();
            nameField.setText(ingredient.getName());

            Label quantityLbl = new Label("Quantity: ");
            TextField quantityField = new TextField();
            quantityField.setText(ingredient.getQuantity() + "");

            Label unitsLbl = new Label("Units: ");
            TextField unitsField = new TextField();
            unitsField.setText(ingredient.getUnitsAndStyle());

            GridPane grid = new GridPane();

            grid.add(nameLbl, 1, 1);
            grid.add(nameField, 2, 1);

            grid.add(quantityLbl, 1, 2);
            grid.add(quantityField, 2, 2);

            grid.add(unitsLbl, 1, 3);
            grid.add(unitsField, 2, 3);

            dialog.getDialogPane().setContent(grid);

            ButtonType buttonTypeOk = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && !nameField.getText().isEmpty() && !quantityField.getText().isEmpty() && !unitsField.getText().isEmpty()) {
                RecipeDSC dsc = new RecipeDSC();
                dsc.updateIngredient(ingredient.getID(), nameField.getText(), Double.parseDouble(quantityField.getText()), unitsField.getText());
                updateTable();
            } else {
                System.out.println("Not enough information/Missformatted");
            }
        }
    }
    @FXML
    private void removeIngredient(ActionEvent event) {
        if(!ingredientsTable.getSelectionModel().isEmpty()) {
            Ingredient ing = ingredientsTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Confirmation");
            alert.setContentText("Are you sure you want to delete ingredient " + ing.getID());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                RecipeDSC dsc = new RecipeDSC();
                dsc.deleteIngredient(ing.getID());
                updateTable();
            }
        }
    }

}
