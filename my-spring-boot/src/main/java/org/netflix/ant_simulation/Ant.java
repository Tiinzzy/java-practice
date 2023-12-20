package org.netflix.ant_simulation;

public class Ant {
    private int x;
    private int y;
    private Direction currentDirection;
    private final Torus grid;

    public Ant(int x, int y, Direction startDirection, Torus grid) {
        this.x = x;
        this.y = y;
        this.currentDirection = startDirection;
        this.grid = grid;
    }

    public void flipDirection() {
        grid.flipColor(x, y);
    }

    public void move() {
        if (grid.getColor(x, y)) {
            currentDirection = currentDirection.right;
        } else {
            currentDirection = currentDirection.left;
        }
        grid.flipColor(x, y);
        y = grid.wrapCoordinate(currentDirection.transformY(y));
        x = grid.wrapCoordinate(currentDirection.transformX(x));
    }

}
