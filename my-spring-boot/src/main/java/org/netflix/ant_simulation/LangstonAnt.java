package org.netflix.ant_simulation;

// TODO:
//  3- what if ant hits boarders, handle as torus/donat!?
//  5- num of ants (last change)  <= you need a better design

import org.json.JSONArray;
import org.json.JSONObject;

public class LangstonAnt {
    private final int GRID_SIZE;
    private final Torus grid;
    private int x1;
    private int y1;
    private int steps = 0;

//    private final List<Direction> directions = new LinkedList<>();
//    private int dirIndex = 0;

    private Direction current;

    public LangstonAnt(int gridSize) {
        this.GRID_SIZE = gridSize;
        this.grid = new Torus(gridSize);
        this.x1 = gridSize / 2;
        this.y1 = gridSize / 2;

//        this.directions.add(new Direction((x) -> (x), (y) -> (y - 1)));     // North
//        this.directions.add(new Direction((x) -> (x + 1), (y) -> (y)));     // East
//        this.directions.add(new Direction((x) -> (x), (y) -> (y + 1)));     // South
//        this.directions.add(new Direction((x) -> (x - 1), (y) -> (y)));     // West

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
//            turnRight();
            current = current.right;
        } else {
//            turnLeft();
            current = current.left;
        }
        flipColor();
        moveForward();
        return toJSON();
    }

    private void turnRight() {
//        dirIndex = (dirIndex + 1) % directions.size();
    }

    private void turnLeft() {
//        dirIndex = (dirIndex - 1 + directions.size()) % directions.size();
    }

    private void moveForward() {
//        y = grid.wrapCoordinate(directions.get(dirIndex).getY(y));
//        x = grid.wrapCoordinate(directions.get(dirIndex).getX(x));

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