package org.netflix.gravity;

public class Test {

    public static void main(String[] args) {
        Universe u = new Universe(500);
        u.init(100);
        System.out.println(u.toJSON());

        int maxLoopCount = 1000;
        do {
            u.tick();
            System.out.println(u.toJSON());
        } while (u.getParticleCount() > 1 && maxLoopCount-- > 0);
    }
}
