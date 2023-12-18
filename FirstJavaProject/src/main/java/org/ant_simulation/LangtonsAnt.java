package org.ant_simulation;

import org.json.JSONArray;

public class LangtonsAnt {
    private static final int GRID_SIZE = 100;
    private boolean[][] grid = new boolean[GRID_SIZE][GRID_SIZE];
    private int x = GRID_SIZE / 2;
    private int y = GRID_SIZE / 2;
    private Direction direction = Direction.NORTH;

    public void nextMove() {
        if (grid[x][y]) {
            turnRight();
        } else {
            turnLeft();
        }
        flipColor();
        moveForward();
        System.out.println(toJSON());
    }

    private void turnRight() {
        switch (direction) {
            case NORTH:
                direction = Direction.EAST;
                break;
            case EAST:
                direction = Direction.SOUTH;
                break;
            case SOUTH:
                direction = Direction.WEST;
                break;
            case WEST:
                direction = Direction.NORTH;
                break;
        }
    }

    private void turnLeft() {
        switch (direction) {
            case NORTH:
                direction = Direction.WEST;
                break;
            case WEST:
                direction = Direction.SOUTH;
                break;
            case SOUTH:
                direction = Direction.EAST;
                break;
            case EAST:
                direction = Direction.NORTH;
                break;
        }
    }

    private void moveForward() {
        switch (direction) {
            case NORTH:
                y--;
                break;
            case EAST:
                x++;
                break;
            case SOUTH:
                y++;
                break;
            case WEST:
                x--;
                break;
        }
    }

    private void flipColor() {
        grid[x][y] = !grid[x][y];
    }


    public JSONArray toJSON() {
        JSONArray gridArray = new JSONArray();
        for (boolean[] row : grid) {
            JSONArray rowArray = new JSONArray();
            for (boolean cell : row) {
                rowArray.put(cell ? 1 : 0);
            }
            gridArray.put(rowArray);
        }
        return gridArray;
    }
}
