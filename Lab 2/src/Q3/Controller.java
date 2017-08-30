package Q3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Controller {

    @FXML
    private TextField result;
    @FXML
    private Button zero,one,two,three,four,five,six,seven,eight,nine;

    @FXML
    private void defaultAction(ActionEvent event) {
        result.setText(result.getText() + event.getSource().toString().split("'")[1]);
    }

    @FXML
    private void calculate(ActionEvent event) {
        try {
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Result: " + engine.eval(result.getText()), ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                result.setText("");
            }
        } catch (Exception EX) { }
    }

    @FXML
    private void clear(ActionEvent event) {
        result.setText("");
    }
}

