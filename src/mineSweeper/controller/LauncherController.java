package mineSweeper.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import mineSweeper.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class LauncherController implements Initializable {

    @FXML
    void launcherButtonOnAction(ActionEvent event) {
        Main.showView("views/game");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
