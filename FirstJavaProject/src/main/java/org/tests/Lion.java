package org.tests;

public class Lion implements Animal {
    String name;
    int weight;

    public Lion(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public void move(Location from, Location to) {
        System.out.println("Move this line in cage using truck.");
    }

    @Override
    public void takeAShower() {

    }

    public void roar() {
    }

    public void jump() {
    }
}
