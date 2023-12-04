package org.netflix.game_of_life;

public class Game {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        Board board = new Torus(30, 30);
//        board.initialize(10);

        int base = 3;
        board.setCell(base, base);
        board.setCell(base+1, base+1);
        board.setCell(base+1, base+2);

        board.setCell(base, base+2);
        board.setCell(base-1, base+2);

        for (int i = 0; i < 200; i++) {
            try {
                clearScreen();
                board.display();
                System.out.print(i);
                Thread.sleep(300);
                board.evolve();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
