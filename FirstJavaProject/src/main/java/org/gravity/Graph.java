package org.gravity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

import java.util.Random;
import org.json.JSONObject;

public class Graph {
    private final int[][] matrix;
    private static final int SIZE = 10;

    public Graph(int count) {
        matrix = new int[SIZE][SIZE];
        fillRandomCells(count);
    }

    private void fillRandomCells(int count) {
        Random random = new Random();
        while (count > 0) {
            int x = random.nextInt(SIZE);
            int y = random.nextInt(SIZE);

            if (matrix[x][y] == 0) {
                matrix[x][y] = 1;
                count--;
            }
        }
    }

    public JSONArray getMatrixAsJson() {
        JSONArray jsonMatrix = new JSONArray();

        for (int i = 0; i < SIZE; i++) {
            JSONArray row = new JSONArray();
            for (int j = 0; j < SIZE; j++) {
                row.put(matrix[i][j]);
            }
            jsonMatrix.put(row);
        }
        return jsonMatrix;
    }

    public void printMatrix() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void merge() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                mergeIfPossible(i, j);
            }
        }
    }

    private void mergeIfPossible(int x, int y) {
        int currentValue = matrix[x][y];
        if (currentValue == 0) return;

        if (isInBounds(x - 1, y) && matrix[x - 1][y] == currentValue) {
            matrix[x][y] += matrix[x - 1][y];
            matrix[x - 1][y] = 0;
        } else if (isInBounds(x + 1, y) && matrix[x + 1][y] == currentValue) {
            matrix[x][y] += matrix[x + 1][y];
            matrix[x + 1][y] = 0;
        } else if (isInBounds(x, y - 1) && matrix[x][y - 1] == currentValue) {
            matrix[x][y] += matrix[x][y - 1];
            matrix[x][y - 1] = 0;
        } else if (isInBounds(x, y + 1) && matrix[x][y + 1] == currentValue) {
            matrix[x][y] += matrix[x][y + 1];
            matrix[x][y + 1] = 0;
        }
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

}