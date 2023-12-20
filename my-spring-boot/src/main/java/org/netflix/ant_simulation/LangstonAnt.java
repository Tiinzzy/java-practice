package org.netflix.ant_simulation;

// TODO:
//  5- num of ants (last change)  <= you need a better design

import org.json.JSONArray;
import org.json.JSONObject;

public class LangstonAnt {
    private final int GRID_SIZE;
    private final Torus grid;
    private int x1;
    private int y1;
    private int steps = 0;

    private Direction current;

    public LangstonAnt(int gridSize) {
        this.GRID_SIZE = gridSize;
        this.grid = new Torus(gridSize);
        this.x1 = gridSize / 2;
        this.y1 = gridSize / 2;

        Direction north = new Direction((x) -> (x), (y) -> (y - 1));
        Direction east = new Direction((x) -> (x + 1), (y) -> (y));
        Direction south = new Direction((x) -> (x), (y) -> (y + 1));
        Direction west = new Direction((x) -> (x - 1), (y) -> (y));

        north.setNeighbours(west, east);
        east.setNeighbours(north, south);
        south.setNeighbours(east, west);
        west.setNeighbours(south, north);

        current = north;
    }

    public JSONObject nextMove() {
        if (grid.getColor(x1, y1)) {
            current = current.right;
        } else {
            current = current.left;
        }
        flipColor();
        moveForward();
        return toJSON();
    }

    private void moveForward() {
        y1 = grid.wrapCoordinate(current.getY(y1));
        x1 = grid.wrapCoordinate(current.getX(x1));
    }

    private void flipColor() {
        grid.flipColor(x1, y1);
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