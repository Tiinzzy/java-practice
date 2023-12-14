package org.gravity_ii;

public class Test {

    public static void main(String[] args) {
        Universe u = new Universe(100);
        u.init(10);
        System.out.println(u.toJSON());
        u.tick();
        System.out.println(u.toJSON());
    }
}
