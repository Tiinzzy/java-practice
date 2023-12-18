package org.ant_simulation;

public class Test {
    public static void main(String[] args) {
        LangtonsAnt ant = new LangtonsAnt();
        for (int i = 0; i < 100; i++) {
            ant.nextMove();
        }
    }
}
