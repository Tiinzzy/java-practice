package org.netflix.ant_simulation;

// TODO:
//  1- grid/board size
//  2- steps/tick
//  3- what if ant hits boarders, handle as torus/donat!?
//  4- N => E => S => W =>
//  5- num of ants (last change)  <= you need a better design

import org.json.JSONArray;
import org.json.JSONObject;

public class LangstonAnt {
    private final int GRID_SIZE;
    private final boolean[][] grid;
    private int x;
    private int y;
    private Direction direction = Direction.NORTH;

    private int steps = 0;

    public LangstonAnt(int GRID_SIZE) {
        this.GRID_SIZE = GRID_SIZE;
        this.grid = new boolean[GRID_SIZE][GRID_SIZE];
        this.x = GRID_SIZE / 2;
        this.y = GRID_SIZE / 2;
    }

    public JSONObject nextMove() {
        if (grid[x][y]) {
            turnRight();
        } else {
            turnLeft();
        }
        flipColor();
        moveForward();
        return toJSON();
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

    public JSONObject toJSON() {
        JSONObject resultObject = new JSONObject();
        this.steps = this.steps + 1;
        JSONArray gridArray = new JSONArray();
        for (boolean[] row : grid) {
            JSONArray rowArray = new JSONArray();
            for (boolean cell : row) {
                rowArray.put(cell ? 1 : 0);
            }
            gridArray.put(rowArray);
        }

        resultObject.put("steps",this.steps);
        resultObject.put("data",gridArray);
        return resultObject;
    }
}
