package org.netflix.ant_simulation;

public class Torus {
    private final int[][] grid;
    private final int size;

    public Torus(int size) {
        this.size = size;
        this.grid = new int[size][size];
    }

    public void flipColor(int x, int y, int id) {
        if (this.grid[x][y] == 0) {
            this.grid[x][y] = id;
        } else {
            this.grid[x][y] = 0;
        }
    }

    public int getColor(int x, int y) {
        return this.grid[x][y];
    }

    public int wrapCoordinate(int coordinate) {
        return (coordinate + this.size) % this.size;
    }
}
