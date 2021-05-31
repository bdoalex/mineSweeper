package mineSweeper.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mineSweeper.Main;
import mineSweeper.model.GameModel;
import mineSweeper.variable.GameVariable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The type Game controller.
 */
public class GameController implements Initializable {

    /**
     * The Model.
     */
    GameModel model = new GameModel();

    /**
     * The Game done.
     */
    Boolean gameIsDone = false;

    @FXML
    private AnchorPane game;

    @FXML
    private AnchorPane root;

    @FXML
    private Text timerText;

    @FXML
    private Text bombText;

    @FXML
    private ImageView bombImageView;

    @FXML
    private ImageView timeImageView;

    private int timerCount;


    /**
     * Gets bomb count.
     *
     * @return the bomb count
     */
    public int getBombCount() {
        return bombCount;
    }


    private int bombCount;

    private final Timer timer = new Timer();

    private static GameController instance;

    /**
     * true = win
     * false = lost
     * */
    private boolean gameIsWin;

    /**
     * Is game is win .
     *
     * @return the result
     */
    public boolean isGameIsWin() {
        return gameIsWin;
    }

    /**
     * Game controller.
     */
    public GameController() {
        instance = this;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static GameController getInstance() {
        return instance;
    }

    /**
     * Decrease bomb count.
     */
    public void decreaseBombCount() {
        this.bombCount--;
    }

    /**
     * Increase bomb count.
     */
    public void increaseBombCount() {
        this.bombCount++;
    }


    /**
     * Draw board.
     */
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

        //Générer la vue du tableau
        for (var i = 0; i < tempBoard[0].length; ++i) {
            HBox line = new HBox();
            AnchorPane.setTopAnchor(line, (double) GameVariable.SIZE_CELL * i);
            line.prefHeight(GameVariable.SIZE_CELL);


            for (int[] ints : tempBoard) {
                int valueOfCell = ints[i];
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

                if (valueOfCell == -10) {//Si la bombe vient d'exploser on affiche la bombre qui vient d'exploser

                    File bombExplodedImageFile = new File(GameVariable.PATH_TO_RESOURCES + "images/bombExploded.png");
                    Image bombExplodedImage = new Image(bombExplodedImageFile.toURI().toString());
                    ImageView bombExplodedImageView = new ImageView(bombExplodedImage);
                    bombExplodedImageView.setFitWidth(GameVariable.SIZE_CELL);
                    bombExplodedImageView.setFitHeight(GameVariable.SIZE_CELL);

                    cell.getChildren().clear();
                    cell.getChildren().add(bombExplodedImageView);
                }else if (gameIsDone && valueOfCell == 10) {//Si la partie est terminée, on affiche les bombes restantes
                    File bombImageFile = new File(GameVariable.PATH_TO_RESOURCES + "images/bomb.png");
                    Image bombImage = new Image(bombImageFile.toURI().toString());
                    ImageView bombImageView = new ImageView(bombImage);
                    bombImageView.setFitWidth(GameVariable.SIZE_CELL);
                    bombImageView.setFitHeight(GameVariable.SIZE_CELL);

                    cell.getChildren().clear();
                    cell.getChildren().add(bombImageView);
                } else if (valueOfCell > 30) {
                    File flagImageFile = new File(GameVariable.PATH_TO_RESOURCES + "images/flag.png");
                    Image flagImage = new Image(flagImageFile.toURI().toString());
                    ImageView flagImageView = new ImageView(flagImage);
                    flagImageView.setFitWidth(GameVariable.SIZE_CELL);
                    flagImageView.setFitHeight(GameVariable.SIZE_CELL);
                    cell.getChildren().clear();
                    cell.getChildren().add(flagImageView);
                }
                line.getChildren().add(cell);
            }
            game.getChildren().add(line);
        }
    }

    /**
     * On click root.
     *
     * @param mouseEvent the mouse event
     */
    public void onClickRoot(MouseEvent mouseEvent) {
        int mouseClickedX = (int) Math.floor(mouseEvent.getX() / GameVariable.SIZE_CELL);
        int mouseClickedY = (int) Math.floor(mouseEvent.getY() / GameVariable.SIZE_CELL);

        if (mouseEvent.isSecondaryButtonDown()) {
            int isFlag = model.isFlag(mouseClickedX, mouseClickedY);

            if (isFlag == 1) {
                increaseBombCount();

            } else if (isFlag == 2) {
                decreaseBombCount();

            }
            bombText.setText("Bombes : " + bombCount);
            drawBoard();


        } else {
            int explodedBomb = model.play(mouseClickedX, mouseClickedY);
            boolean resultGame = model.gameIsWin();


            //Optimisation possible
            if (resultGame) {
                drawBoard();
                gameIsWin =true;
                endGame();
            } else {
                if (explodedBomb == 1) {
                    gameIsDone = true;
                    drawBoard();
                    endGame();

                } else if (explodedBomb == 3) drawBoard();
            }


        }
    }

    /**
     * Show end game.
     */
    public void endGame() {
        try {
            timer.cancel();
            timer.purge();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../views/endGameLayout.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("End");
            stage.setScene(scene);

            stage.setOnCloseRequest(event -> {
                goToLauncher();
                stage.close();
            });

            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);//Permet de bloquer la page parents lors de l'ouverture de la fenêtre param
            stage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Go to launcher.
     */
    public void goToLauncher() {
        Main.showView("views/launcherLayout");
    }

    /**
     * Timer.
     */
    public void Timer() {
        //one-time use timer: prints stuff after 1s

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                increaseTimerCount();
                updateTimerText();
            }
        }, 0, 1000);
    }

    /**
     * Update timer text.
     */
    public void updateTimerText() {
        timerText.setText("Time : " + timerCount);
    }


    /**
     * Gets timer count.
     *
     * @return the timer count
     */
    public int getTimerCount() {
        return timerCount;
    }

    /**
     * Increase timer count.
     */
    public void increaseTimerCount() {
        this.timerCount++;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            File bombImageFile = new File(GameVariable.PATH_TO_RESOURCES + "images/bomb.png");
            Image bombImage = new Image(bombImageFile.toURI().toString());
            bombImageView.setImage(bombImage);

            File timerImageFile = new File(GameVariable.PATH_TO_RESOURCES + "images/timer.png");
            Image timerImage = new Image(timerImageFile.toURI().toString());
            timeImageView.setImage(timerImage);

            bombCount = ParametersDialogController.getInstance().getNumberBombs();
            bombText.setText("Bombes : " + bombCount);

            Timer();
            model.generateRandomBoard(ParametersDialogController.getInstance().getWidth(), ParametersDialogController.getInstance().getHeight(), ParametersDialogController.getInstance().getNumberBombs());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        root.boundsInLocalProperty().addListener((obs, oldVal, newVal) -> drawBoard());//Permet d'actualiser l'affichage à chaque modification de root

    }
}
