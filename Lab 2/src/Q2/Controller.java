package Q2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class Controller {
    @FXML
    ToggleGroup fonts;
    public void myGroupAction(ActionEvent action)
    {
        System.out.println("Toggled: " + fonts.getSelectedToggle().getUserData().toString());
    }
}
