package mineSweeper.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import mineSweeper.Main;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Parameters dialog controller.
 */
public class ParametersDialogController implements Initializable {

    @FXML
    private Button playButton;

    private int width = 7;
    private int height = 5;
    private int numberBombs = 4;


    @FXML
    private Spinner<Integer> heightSpinner;

    @FXML
    private Spinner<Integer> widthSpinner;

    @FXML
    private Spinner<Integer> bombsSpinner;

    /**
     * The Initial value.
     */
    final int initialValue = 1;

    /**
     * The Width value factory.
     */
    SpinnerValueFactory<Integer> widthValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, initialValue);
    /**
     * The Bombs value factory.
     */
    SpinnerValueFactory<Integer> bombsValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, initialValue);
    /**
     * The Height value factory.
     */
    SpinnerValueFactory<Integer> heightValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, initialValue);


    private static ParametersDialogController instance;

    /**
     * Instantiates a new Parameters dialog controller.
     */
    public ParametersDialogController() {
        instance = this;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ParametersDialogController getInstance() {
        return instance;
    }


    /**
     * Play button pressed.
     *
     * @param event the event
     */
    @FXML
    void playButtonPressed(ActionEvent event) {
        setHeight(heightSpinner.getValue());
        setWidth(widthSpinner.getValue());
        setNumberBombs(bombsSpinner.getValue());
        Main.showView("views/game");
        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.close();

    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets number bombs.
     *
     * @return the number bombs
     */
    public int getNumberBombs() {
        return numberBombs;
    }

    /**
     * Sets number bombs.
     *
     * @param numberBombs the number bombs
     */
    public void setNumberBombs(int numberBombs) {
        this.numberBombs = numberBombs;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        heightSpinner.setValueFactory(heightValueFactory);
        widthSpinner.setValueFactory(widthValueFactory);
        bombsSpinner.setValueFactory(bombsValueFactory);
    }
}
