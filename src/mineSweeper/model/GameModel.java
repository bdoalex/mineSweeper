package mineSweeper.model;


import javafx.util.Pair;
import mineSweeper.variable.GameVariable;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


public class GameModel {

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
     * @param posX position in board
     * @param posY position in board
     * @return if the position is a mine return true
     */


    public int play(int posX, int posY) {

        if (posX < this.board.length && posX >= 0 && posY < this.board[0].length && posY >= 0) {

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
                                        if (this.board[indexX][indexY] != GameVariable.MINE_CODE &&  this.board[indexX][indexY] >0) {
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
     * @param width       number cells
     * @param height      number cells
     * @param numberBombs number bombs
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
