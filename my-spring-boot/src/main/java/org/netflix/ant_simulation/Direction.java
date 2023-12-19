package org.netflix.ant_simulation;

import java.util.function.Function;

public class Direction {

    Function<Integer, Integer> transformX;
    Function<Integer, Integer> transformY;

    Direction left;
    Direction right;

    public Direction() {
    }
    public Direction(Function<Integer, Integer> transformX, Function<Integer, Integer> transformY) {
        this.transformX = transformX;
        this.transformY = transformY;
    }

    Integer getX(Integer x) {
        return this.transformX.apply(x);
    }

    Integer getY(Integer y) {
        return this.transformY.apply(y);
    }

    void setNeighbours(Direction left, Direction right) {
        this.left = left;
        this.right = right;
    }
}
