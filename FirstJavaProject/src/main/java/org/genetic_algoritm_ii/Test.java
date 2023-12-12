package org.genetic_algoritm_ii;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {

    static List<Couple> getCouples(int coupleCount, Environment environment) {
        List<Couple> couples = new ArrayList<>();
        for (int i = 0; i < coupleCount; i++) {
            Person p1 = new Person();
            Person p2 = new Person();

            // Assign couples to the environment based on their traits
            if (environment.canSurvive(p1) && environment.canSurvive(p2)) {
                couples.add(new Couple(p1, p2));
            }
        }
        return couples;
    }

    static List<Couple> getCouples(List<Person> generation, Environment environment) {
        Collections.shuffle(generation);
        List<Couple> couples = new ArrayList<>();

        for (int i = 0; i < generation.size(); i += 2) {
            if (i + 1 < generation.size()) {
                Person p1 = generation.get(i);
                Person p2 = generation.get(i + 1);

                if (environment.canSurvive(p1) && environment.canSurvive(p2)) {
                    couples.add(new Couple(p1, p2));
                }
            }
        }

        return couples;
    }

    static List<Person> getNextGeneration(List<Couple> couples, Environment environment) {
        List<Person> nextGeneration = new ArrayList<>();
        for (Couple c : couples) {
            nextGeneration.addAll(environment.generateChildren(c));
        }
        return nextGeneration;
    }

    public static void main(String[] args) {
        Environment city = new City();
        Environment jungle = new Jungle();

        List<Couple> cityCouples = getCouples(Config.INITIAL_COUPLE_COUNT, city);
        List<Couple> jungleCouples = getCouples(Config.INITIAL_COUPLE_COUNT, jungle);

        for (int g = 0; g < Config.GENERATION_COUNT; g++) {
            System.out.println("Generation " + g);

            List<Person> cityNextGen = getNextGeneration(cityCouples, city);
            List<Person> jungleNextGen = getNextGeneration(jungleCouples, jungle);

            cityCouples = getCouples(cityNextGen, city);
            jungleCouples = getCouples(jungleNextGen, jungle);

            // Print Statistics for each environment
            System.out.println("City Stats: " + Statistics.getStat(cityCouples));
            System.out.println("Jungle Stats: " + Statistics.getStat(jungleCouples));
        }
    }
}
