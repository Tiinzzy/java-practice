package org.tests;

public class Snake implements Animal {
    String name;
    int weight;
    int length;

    public Snake(String name, int weight, int length) {
        this.name = name;
        this.weight = weight;
        this.length = length;
    }

    @Override
    public void move(Location from, Location to) {
        System.out.println("Move this animal using hard glassy box in a van.");
    }

    @Override
    public void takeAShower() {

    }

    public void bite() {

    }
}
