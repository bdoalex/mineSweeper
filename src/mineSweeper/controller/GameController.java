package mineSweeper.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mineSweeper.model.GameModel;
import mineSweeper.variable.GameVariable;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    GameModel model = new GameModel();

    Boolean gameDone = false;

    @FXML
    private AnchorPane game;

    @FXML
    private AnchorPane root;


    public void drawBoard() {
        game.getChildren().clear();
        int[][] tempBoard = model.getBoard();

        double widthBoard = tempBoard.length * GameVariable.SIZE_CELL;
        double heightBoard = tempBoard[0].length * GameVariable.SIZE_CELL;

        double sizeRootHeight = root.getHeight();
        double sizeRootWidth = root.getWidth();

        double startX = sizeRootWidth / 2 - widthBoard / 2;
        double startY = sizeRootHeight / 2 - heightBoard / 2;

        AnchorPane.setTopAnchor(game, startY);
        AnchorPane.setLeftAnchor(game, startX);

        for (var i = 0; i < tempBoard[0].length; ++i) {
            HBox line = new HBox();
            AnchorPane.setTopAnchor(line, (double) GameVariable.SIZE_CELL * i);
            line.prefHeight(GameVariable.SIZE_CELL);


            for (var j = 0; j < tempBoard.length; ++j) {
                int valueOfCell = tempBoard[j][i];
                Pane cell = new Pane();
                cell.setPrefWidth(GameVariable.SIZE_CELL);
                cell.setPrefHeight(GameVariable.SIZE_CELL);
                cell.setStyle("-fx-background-color: grey;" + "-fx-border-color: black");

                Text text = new Text();
                text.setText(Integer.toString(Math.abs(valueOfCell) - 1));
                text.setLayoutX(14);
                text.setLayoutY(30);
                text.setStrokeWidth(0);

                if (valueOfCell < 0 && valueOfCell != -1) {

                    cell.setStyle("-fx-background-color: none;" + "-fx-border-color: black");
                    cell.getChildren().add(text);
                } else if (valueOfCell == -1) {
                    cell.setStyle("-fx-background-color: none;" + "-fx-border-color: black");

                }

                if (valueOfCell == -10) cell.setStyle("-fx-background-color: red;" + "-fx-border-color: black");

                if (gameDone && valueOfCell == 10) {
                    cell.setStyle("-fx-background-color: orange;" + "-fx-border-color: black");
                }
                line.getChildren().add(cell);
            }
            game.getChildren().add(line);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            model.generateRandomBoard(7, 5, 4);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        root.boundsInLocalProperty().addListener((obs, oldVal, newVal) -> {
            drawBoard();
        });


    }


    public void onClickRoot(MouseEvent mouseEvent) {

        int mouseClickedX = (int) Math.floor(mouseEvent.getX() / GameVariable.SIZE_CELL);
        int mouseClickedY = (int) Math.floor(mouseEvent.getY() / GameVariable.SIZE_CELL);


        int explodedBomb = model.play(mouseClickedX, mouseClickedY);

        if (explodedBomb == 1) {
            gameDone = true;
            drawBoard();
            showEndGame();
        } else if (explodedBomb == 3) drawBoard();
    }

    public void showEndGame() {
        System.out.println("Mouru");

    }


}
