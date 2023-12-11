package org.genetic_algoritm_i;

public class Test {
    public static void main(String[] args) {
        Generation currentGen = new Generation(0, 10);

        int numberOfGenerations = 5;
        for (int i = 0; i < numberOfGenerations; i++) {
//            System.out.println("Generation " + currentGen.getGenerationNumber() + " Details:");
            System.out.println(currentGen.toJSON().toString(4));
            currentGen = currentGen.nextGeneration();
        }
    }
}