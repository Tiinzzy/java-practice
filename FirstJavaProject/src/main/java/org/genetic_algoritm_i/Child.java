package org.genetic_algoritm_i;

public class Child extends Person {
    public Child(Person parent1, Person parent2) {
        super((parent1.getHeight() + parent2.getHeight()) / 2);
        this.setSmart((parent1.getSmart() + parent2.getSmart()) / 2);
        this.setMuscular((parent1.getMuscular() + parent2.getMuscular()) / 2);
    }
}