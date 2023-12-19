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
    private final Torus grid;
    private int x;
    private int y;
    private List<Direction> directions;
    private int dirIndex = 0;

    private int steps = 0;

    public LangstonAnt(int GRID_SIZE) {
        this.GRID_SIZE = GRID_SIZE;
        this.grid = new Torus(GRID_SIZE);
        this.x = GRID_SIZE / 2;
        this.y = GRID_SIZE / 2;

        this.directions = new LinkedList<>();
        this.directions.add(Direction.NORTH);
        this.directions.add(Direction.EAST);
        this.directions.add(Direction.SOUTH);
        this.directions.add(Direction.WEST);
    }

    public JSONObject nextMove() {
        if (grid.getColor(x, y)) {
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
                y = grid.wrapCoordinate(y - 1);
                break;
            case EAST:
                x = grid.wrapCoordinate(x + 1);
                break;
            case SOUTH:
                y = grid.wrapCoordinate(y + 1);
                break;
            case WEST:
                x = grid.wrapCoordinate(x - 1);
                break;
        }
    }

    private void flipColor() {
        grid.flipColor(x, y);
    }

    public JSONObject toJSON() {
        JSONObject resultObject = new JSONObject();
        this.steps = this.steps + 1;
        JSONArray gridArray = new JSONArray();
        for (int i = 0; i < GRID_SIZE; i++) {
            JSONArray rowArray = new JSONArray();
            for (int j = 0; j < GRID_SIZE; j++) {
                rowArray.put(grid.getColor(i, j) ? 1 : 0);
            }
            gridArray.put(rowArray);
        }

        resultObject.put("steps", this.steps);
        resultObject.put("data", gridArray);
        return resultObject;
    }
}