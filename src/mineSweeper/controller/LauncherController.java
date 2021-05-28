package mineSweeper.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mineSweeper.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Launcher controller.
 */
public class LauncherController implements Initializable {

    @FXML
    private Button launcherButton;

    /**
     * Launcher button on action.
     *
     * @param event the event
     */
    @FXML
    void launcherButtonOnAction(ActionEvent event) {
        openParamDialog();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Open param dialog.
     */
    public void openParamDialog(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../views/parametersDialog.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Parameters");
            stage.setScene(scene);

            stage.initModality(Modality.APPLICATION_MODAL);//Permet de bloquer la page parents lors de l'ouverture de la fenêtre param
            stage.showAndWait();


        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
