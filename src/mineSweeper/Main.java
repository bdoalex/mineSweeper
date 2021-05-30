package mineSweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mineSweeper.variable.GameVariable;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Main extends Application {



    private static AnchorPane mainLayout;



    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Démineur");

        mainLayout = (AnchorPane) loadFXML("views/mainLayout");

        mainLayout.getChildren().add(loadFXML("views/launcherLayout"));


        Scene scene = new Scene(mainLayout);


        stage.setScene(scene);
        stage.show();


    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Show view.
     *
     * @param rootElement the root element
     */
    public static void showView(String rootElement) {
        try {

            mainLayout.getChildren().clear();
            mainLayout.getChildren().add(loadFXML(rootElement));

        } catch (IOException e) {

            throw new IllegalArgumentException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }

}