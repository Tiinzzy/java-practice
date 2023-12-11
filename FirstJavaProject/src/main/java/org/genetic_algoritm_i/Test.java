package org.genetic_algoritm_i;

public class Test {
    public static void main(String[] args) {
        Generation gen = new Generation(0, 10);

        for (int i = 0; i < 50; i++) {
//            System.out.print(gen.statistics("population"));
            System.out.println(gen.statistics());
            gen = gen.nextGen();
        }
    }
}
