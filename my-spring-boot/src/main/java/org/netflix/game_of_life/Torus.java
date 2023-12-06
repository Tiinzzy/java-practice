package org.netflix.game_of_life;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class Torus implements Board {
    private final static String DEAD_CELL = " ";
    private final static String ALIVE_CELL = "▅";
    private final int rowCount;
    private final int columnCount;
    private byte[][] board;
    private final Random random = new Random();

    private int generation = 0;

    public Torus(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.board = new byte[rowCount][columnCount];
    }

    @Override
    public void initialize(int count) {
        int aliveCount = 0;
        if (count >= rowCount * columnCount) {
            count = (rowCount * columnCount) / 2;
        }
        while (aliveCount != count) {
            int r = random.nextInt(rowCount);
            int c = random.nextInt(columnCount);
            if (board[r][c] == 0) {
                board[r][c] = 1;
                aliveCount += 1;
            }
        }
    }

    @Override
    public void setCell(int row, int col) {
        board[row][col] = 1;
    }

    @Override
    public void display() {
        display(board);
    }

    public void display(byte[][] board) {
        System.out.println();
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                System.out.print((board[r][c] == 0 ? DEAD_CELL : ALIVE_CELL) + " ");
            }
            System.out.println();
        }
    }

    @Override
    public int getNumberOfAliveCellAround(int row, int col) {
        int[] offset = {-1, 0, 1};

        int aliveCount = 0;
        for (int ro : offset) {
            for (int co : offset) {
                if (ro != 0 || co != 0) {
                    int r = row + ro;
                    r = r < 0 ? r + rowCount : r;
                    r = r >= rowCount ? 0 : r;

                    int c = col + co;
                    c = c < 0 ? c + columnCount : c;
                    c = c >= columnCount ? 0 : c;

                    if (board[r][c] == 1) {
                        aliveCount += 1;
                    }
                }
            }
        }

        return aliveCount;
    }

    @Override
    public void evolve() {
        generation += 1;

        byte[][] nextGeneration = new byte[rowCount][columnCount];
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                nextGeneration[r][c] = 0;
            }
        }

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                int aliveCount = getNumberOfAliveCellAround(r, c);
                if (board[r][c] == 1) {
                    if (aliveCount == 2 || aliveCount == 3) {
                        nextGeneration[r][c] = 1;
                    } else {
                        nextGeneration[r][c] = 0;
                    }
                } else {
                    if (aliveCount == 3) {
                        nextGeneration[r][c] = 1;
                    } else {
                        nextGeneration[r][c] = 0;
                    }
                }
            }
        }
        board = nextGeneration;
    }

    @Override
    public JSONObject toJSON() {
        JSONArray jBoard = new JSONArray();
        for (int r = 0; r < rowCount; r++) {
            JSONArray jRow = new JSONArray();
            for (int c = 0; c < columnCount; c++) {
                jRow.put(board[r][c]);
            }
            jBoard.put(jRow);
        }
        JSONObject jResult = new JSONObject();
        jResult.put("board", jBoard);
        jResult.put("generation", generation);
        return jResult;
    }

    byte[][] getBoardCopy() {
        byte[][] copy = new byte[rowCount][columnCount];
        for (int r = 0; r < rowCount; r++) {
            System.arraycopy(board[r], 0, copy[r], 0, columnCount);
        }
        return copy;
    }
    @Override
    public boolean getCellState(int row, int column) {
        return board[row][column] == 1;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }
}
