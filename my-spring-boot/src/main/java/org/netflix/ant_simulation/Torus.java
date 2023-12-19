package org.netflix.ant_simulation;

public class Torus {
    private final boolean[][] grid;
    private final int size;

    public Torus(int size) {
        this.size = size;
        this.grid = new boolean[size][size];
    }

    public void flipColor(int x, int y) {
        grid[x][y] = !grid[x][y];
    }

    public boolean getColor(int x, int y) {
        return grid[x][y];
    }

    public int wrapCoordinate(int coordinate) {
        return (coordinate + size) % size;
    }
}
