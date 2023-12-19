package org.netflix.ant_simulation;

// TODO:
//  3- what if ant hits boarders, handle as torus/donat!?
//  5- num of ants (last change)  <= you need a better design

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class LangstonAnt {
    private final int GRID_SIZE;
    private final boolean[][] grid;
    private int x;
    private int y;
    private List<Direction> directions;
    private int dirIndex = 0;

    private int steps = 0;

    public LangstonAnt(int GRID_SIZE) {
        this.GRID_SIZE = GRID_SIZE;
        this.grid = new boolean[GRID_SIZE][GRID_SIZE];
        this.x = GRID_SIZE / 2;
        this.y = GRID_SIZE / 2;

        this.directions = new LinkedList<>();
        this.directions.add(Direction.NORTH);
        this.directions.add(Direction.EAST);
        this.directions.add(Direction.SOUTH);
        this.directions.add(Direction.WEST);
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
        dirIndex = (dirIndex + 1) % directions.size();
    }

    private void turnLeft() {
        dirIndex = (dirIndex - 1 + directions.size()) % directions.size();
    }

    private void moveForward() {
        switch (directions.get(dirIndex)) {
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

        resultObject.put("steps", this.steps);
        resultObject.put("data", gridArray);
        return resultObject;
    }
}
