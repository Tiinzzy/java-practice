package org.netflix.ant_simulation;

// TODO:
//  5- num of ants (last change)  <= you need a better design

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LangstonAnt {
    private final int GRID_SIZE;
    private final Torus grid;
    private int steps = 0;
    private List<Ant> ants;

    public LangstonAnt(int gridSize, int numOfAnts) {
        this.GRID_SIZE = gridSize;
        this.grid = new Torus(gridSize);
        this.ants = new ArrayList<>();

        Direction north = new Direction((x) -> (x), (y) -> (y - 1));
        Direction east = new Direction((x) -> (x + 1), (y) -> (y));
        Direction south = new Direction((x) -> (x), (y) -> (y + 1));
        Direction west = new Direction((x) -> (x - 1), (y) -> (y));

        north.setNeighbours(west, east);
        east.setNeighbours(north, south);
        south.setNeighbours(east, west);
        west.setNeighbours(south, north);

        String[] colors = {"Red", "Green", "Blue", "Yellow", "Purple", "Orange"};
        Random random = new Random();
        for (int i = 0; i < numOfAnts; i++) {
            int x0 = random.nextInt(0, gridSize);
            int y0 = random.nextInt(0, gridSize);
            String color = colors[i % colors.length];
            ants.add(new Ant(x0, y0, north, grid, color));
        }
    }

    public JSONObject nextMove() {
        ants.forEach(Ant::move);
        return toJSON();
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