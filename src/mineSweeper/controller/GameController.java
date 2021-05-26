package mineSweeper.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mineSweeper.model.GameModel;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    GameModel model = new GameModel();

    @FXML
    private AnchorPane root;


    public void drawBoard() {
        root.getChildren().clear();
        int[][] tempBoard = model.getBoard();
        for (var i = 0; i < tempBoard.length; ++i) {
            HBox line = new HBox();
            AnchorPane.setTopAnchor(line, (double) 50 * i);
            line.prefHeight(50);
            AnchorPane.setLeftAnchor(line, (double) 0);
            AnchorPane.setRightAnchor(line, (double) 0);
            for (var j = 0; j < tempBoard[0].length; ++j) {
                int valueOfCell = tempBoard[j][i];
                Pane cell = new Pane();
                cell.setPrefWidth(50);
                cell.setPrefHeight(50);
                cell.setStyle("-fx-background-color: grey;" + "-fx-border-color: black");

                Text text = new Text();
                text.setText(Integer.toString(Math.abs(valueOfCell)-1));
                text.setLayoutX(14);
                text.setLayoutY(30);
                text.setStrokeWidth(0);

                if (valueOfCell<0 && valueOfCell !=-1) {

                    cell.setStyle("-fx-background-color: none;" + "-fx-border-color: black");
                    cell.getChildren().add(text);
                }else if ( valueOfCell==-1){
                    cell.setStyle("-fx-background-color: none;" + "-fx-border-color: black");

                }
                line.getChildren().add(cell);
            }
            root.getChildren().add(line);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            model.generateRandomBoard(10, 10, 15);
            drawBoard();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public void onClickRoot(MouseEvent mouseEvent) {

        int mouseClickedX = (int) Math.floor(mouseEvent.getX() / 50);
        int mouseClickedY = (int) Math.floor(mouseEvent.getY() / 50);

        int explodedBomb = model.play(mouseClickedX, mouseClickedY);

        if (explodedBomb==1) {
            System.out.println("Mouru");
        } else if (explodedBomb ==3)drawBoard();
    }


}
