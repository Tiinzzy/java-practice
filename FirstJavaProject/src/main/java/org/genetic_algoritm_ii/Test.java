package org.genetic_algoritm_ii;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {

    static List<Couple> getCouples() {
        return getCouples(0);
    }

    static List<Couple> getCouples(int coupleCount) {
        List<Couple> couples = new ArrayList<>();
        for (int i = 0; i < coupleCount; i++) {
            couples.add(new Couple(new Person(), new Person()));
        }
        return couples;
    }

    static List<Couple> getCouples(List<Person> generation) {
        Collections.shuffle(generation);
        int halfSize = generation.size() / 2;
        List<Couple> couples = new ArrayList<>();

        for (int i = 0; i < halfSize; i++) {
            couples.add(new Couple(generation.get(i), generation.get(halfSize + i)));
        }
        return couples;
    }

    static List<Person> getNextGeneration(List<Couple> couples) {
        List<Person> nextGeneration = new ArrayList<>();
        for (Couple c : couples) {
            nextGeneration.addAll(c.getChildren());
        }
        return nextGeneration;
    }

    public static void main(String[] args) {
        List<Couple> couples = getCouples(Config.INITIAL_COUPLE_COUNT);
        JSONObject jStat = Statistics.getStat(couples);

        for (int g = 0; g < Config.GENERATION_COUNT; g++) {
            System.out.print("G" + g + " >> ");
            System.out.println(Statistics.getStat(couples));
            List<Person> nextGeneration = getNextGeneration(couples);
            couples = getCouples(nextGeneration);
        }
    }
}
