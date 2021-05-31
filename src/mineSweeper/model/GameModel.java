package mineSweeper.model;


import javafx.util.Pair;
import mineSweeper.variable.GameVariable;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Game model.
 */
public class GameModel {

    /**
     * Get board int [ ] [ ].
     *
     * @return the int [ ] [ ]
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * This is the board of the game
     * Negative value represent cell seen
     * <p>
     * 1 => Empty mine around
     * 2 => One mine around
     * 3 => Two mine around
     * .....
     * 8 => Seven mine around
     * 9 => Eight mine around
     * 10 => Mine
     */
    private int[][] board;


    /**
     * Play int.
     *
     * @param posX position in board
     * @param posY position in board
     * @return if the position is a mine return true
     */
    public int play(int posX, int posY) {

        if (posX < this.board.length && posX >= 0 && posY < this.board[0].length && posY >= 0) {

            if (this.board[posX][posY] > 30) {
                this.board[posX][posY] -= 30;
            }

            if (this.board[posX][posY] == GameVariable.MINE_CODE) {
                this.board[posX][posY] *= -1;
                return 1; //is a mine
            } else if (this.board[posX][posY] < 0) {
                return 2; //alreadyPlayed
            } else {
                List<Pair<Integer, Integer>> allPositions = new ArrayList<>();
                List<Pair<Integer, Integer>> allPositionsChecked = new ArrayList<>();


                allPositions.add(new Pair<>(posX, posY));
                allPositionsChecked.add(new Pair<>(posX, posY));

                while (!allPositions.isEmpty()) {

                    Pair<Integer, Integer> currentPosition = allPositions.get(0);
                    allPositions.remove(0);
                    if (this.board[currentPosition.getKey()][currentPosition.getValue()] == 1) {
                        for (int k = -1; k < 2; k++) {
                            for (int l = -1; l < 2; l++) {
                                if (k != 0 || l != 0) {
                                    int indexX = currentPosition.getKey() + k;
                                    int indexY = currentPosition.getValue() + l;
                                    Pair<Integer, Integer> pos = new Pair<>(indexX, indexY);


                                    if (!allPositionsChecked.contains(pos) && indexX >= 0 && indexX < this.board.length && indexY >= 0 && indexY < this.board[0].length) {
                                        if (this.board[indexX][indexY] > 30) {
                                            this.board[indexX][indexY] -= 30;
                                        }
                                        if (this.board[indexX][indexY] != GameVariable.MINE_CODE && this.board[indexX][indexY] > 0) {
                                            allPositions.add(pos);
                                            allPositionsChecked.add(pos);
                                        }
                                    }
                                }
                            }
                        }

                    }
                    this.board[currentPosition.getKey()][currentPosition.getValue()] *= -1;
                }


                return 3; //can play
            }
        } else {
            throw new Error("Out of bound");
        }
    }

    /**
     * Flag.
     *
     * @param posX the pos x
     * @param posY the pos y
     * @return the int
     */
    public int isFlag(int posX, int posY) {
        if (posX < this.board.length && posX >= 0 && posY < this.board[0].length && posY >= 0) {
            if (this.board[posX][posY] > 0) {
                if (Math.abs(this.board[posX][posY]) > 30) {
                    this.board[posX][posY] -= 30; //si valeur >30 alors il y a un flag
                    return 1; //flag enlevé
                } else {
                    this.board[posX][posY] += 30; //on ajoute un flag
                    return 2; // flag remis
                }
            }

        } else {
            throw new Error("Out of bound");
        }
        return 3;
    }

    /**
     * check if Game is win .
     *
     * @return the result (win game or not)
     */
    public boolean gameIsWin(){
        boolean isWin = true;

        for (int[] tempTab : this.board) {
            for (int elem : tempTab) {
                if (elem>30){
                    elem = elem - 30;
                }

                if (elem > 0 && elem !=10) {
                    isWin = false;
                    break;
                }
            }
        }
        return isWin;
    }

    /**
     * Generate random board.
     *
     * @param width       number cells
     * @param height      number cells
     * @param numberBombs number bombs
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public void generateRandomBoard(int width, int height, int numberBombs) throws NoSuchAlgorithmException {

        if (numberBombs > width * height) {
            throw new Error("The number of mines is greater than the size of the game");
        }


        this.board = new int[width][height];

        List<Pair<Integer, Integer>> allPositionsForMine = new ArrayList<>();

        for (var i = 0; i < width; ++i) {
            for (var j = 0; j < height; ++j) {
                allPositionsForMine.add(new Pair<>(i, j));
                this.board[i][j] = 1;
            }
        }

        var ran = SecureRandom.getInstanceStrong();

        for (var i = 0; i < numberBombs; i++) {
            var position = ran.nextInt(allPositionsForMine.size());
            this.board[allPositionsForMine.get(position).getKey()][allPositionsForMine.get(position).getValue()] = GameVariable.MINE_CODE;
            allPositionsForMine.remove(position);
        }


        for (var i = 0; i < width; ++i) {
            for (var j = 0; j < height; ++j) {
                if (this.board[i][j] != GameVariable.MINE_CODE) {
                    var numberMine = 0;
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            if (k != 0 || l != 0) {
                                int indexX = i + k;
                                int indexY = j + l;

                                if (indexX >= 0 && indexX < this.board.length && indexY >= 0 && indexY < this.board[0].length) {
                                    if (this.board[indexX][indexY] == GameVariable.MINE_CODE) {
                                        numberMine++;
                                    }
                                }
                            }
                        }
                    }
                    this.board[i][j] += numberMine;
                }
            }
        }


    }


}
