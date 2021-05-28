package mineSweeper.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mineSweeper.Main;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * The type End game controller.
 */
public class EndGameController implements Initializable {

    @FXML
    private Text endText;

    @FXML
    private Button menuButton;

    /**
     * Menu button pressed.
     *
     * @param event the event
     */
    @FXML
    void menuButtonPressed(ActionEvent event) {
        Main.showView("views/launcherLayout");
        Stage stage = (Stage) menuButton.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
