package org.netflix.ant_simulation;

public class Ant {
    private int x;
    private int y;
    private Direction currentDirection;

    public Ant(int x, int y, Direction startDirection) {
        this.x = x;
        this.y = y;
        this.currentDirection = startDirection;
    }

    public void moveForward(Torus grid) {
        y = grid.wrapCoordinate(currentDirection.getY(y));
        x = grid.wrapCoordinate(currentDirection.getX(x));
    }

    public void flipDirection(Torus grid) {
        grid.flipColor(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
