package mineSweeper.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mineSweeper.Main;
import mineSweeper.variable.GameVariable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * The type End game controller.
 */
public class EndGameController implements Initializable {

    @FXML
    private Text endText;

    @FXML
    private ImageView bombImageView;

    @FXML
    private Text bombText;

    @FXML
    private ImageView timeImageView;

    @FXML
    private Text timerText;

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

        File bombImageFile = new File(GameVariable.PATH_TO_RESOURCES + "images/bomb.png");
        Image bombImage = new Image(bombImageFile.toURI().toString());
        bombImageView.setImage(bombImage);
        bombText.setText("Bombes restantes : " + GameController.getInstance().getBombCount());

        File timerImageFile = new File(GameVariable.PATH_TO_RESOURCES + "images/timer.png");
        Image timerImage = new Image(timerImageFile.toURI().toString());
        timeImageView.setImage(timerImage);
        timerText.setText("Temps : " + GameController.getInstance().getTimerCount());

    }
}
