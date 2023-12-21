package org.netflix.ant_simulation;

public class Ant {
    int x;
    int y;
    private Direction currentDirection;
    private final Torus grid;
    private final int id;

    public Ant(int x, int y, Direction startDirection, Torus grid, int id) {
        this.x = x;
        this.y = y;
        this.currentDirection = startDirection;
        this.grid = grid;
        this.id = id;
    }

    public void flipDirection() {
        grid.flipColor(x, y, id);
    }

    public void move() {
        if (grid.getColor(x, y) != 0) {
            currentDirection = currentDirection.right;
        } else {
            currentDirection = currentDirection.left;
        }
        grid.flipColor(x, y, id);
        y = grid.wrapCoordinate(currentDirection.transformY(y));
        x = grid.wrapCoordinate(currentDirection.transformX(x));
    }

}
