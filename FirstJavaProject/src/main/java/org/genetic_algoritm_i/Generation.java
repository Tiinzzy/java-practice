package org.genetic_algoritm_i;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Generation {
    final int MIN_HEIGHT = 100;
    final int MAX_HEIGHT = 230;
    final int MIN_CHILD = 0;
    final int MAX_CHILD = 5;

    int generation = -1;

    float p1InheritanceRate = 0.9F;

    int genAverageHeight = 0;

    Random random = new Random();

    List<Couple> couples = new ArrayList<>();


    public Generation(int generation, int count) {
        this.generation = generation;
        for (int i = 0; i < count; i++) {
            Person p1 = new Person(getRandomHeight());
            Person p2 = new Person(getRandomHeight());
            Couple c = new Couple(p1, p2);
            couples.add(c);
        }
        this.genAverageHeight = getGenAverageHeight(couples);
    }

    public Generation(int generation, List<Couple> p1p2) {
        this.genAverageHeight = getGenAverageHeight(p1p2);
        this.generation = generation;
        List<Person> newGenPeople = new ArrayList<>();
        for (Couple c : p1p2) {
            int noOfChildren = getRandomChildCount(c);
            for (int cc = 0; cc < noOfChildren; cc++) {
                int childHeight = getRandomHeight(c);
                newGenPeople.add(new Person(childHeight));
            }
        }
        Collections.shuffle(newGenPeople);
        int coupleCount = newGenPeople.size() / 2;

        for (int c = 0; c < coupleCount; c++) {
            couples.add(new Couple(newGenPeople.get(c), newGenPeople.get(coupleCount + c)));
        }
    }

    int getRandomHeight() {
        return random.nextInt(MIN_HEIGHT, MAX_HEIGHT);
    }

    int getRandomHeight(Couple c) {
//        return (int) (c.p1.height * p1InheritanceRate + c.p2.height * (1 - p1InheritanceRate));
        return (int) ((c.p1.height * p1InheritanceRate + c.p2.height * (1 - p1InheritanceRate)));
    }

    int getRandomChildCount() {
        return random.nextInt(MIN_CHILD, MAX_CHILD);
    }

    int getGenAverageHeight(List<Couple> couples) {
        long sum = 0;
        for (Couple c : couples) {
            sum += c.p1.height + c.p2.height;
        }
        if (couples.isEmpty()) {
            return (MIN_HEIGHT + MAX_HEIGHT) / 2;
        } else {
            return (int) (sum / (couples.size() * 2L));
        }
    }

    int getRandomChildCount(Couple c) {
        int parentAverage = (c.p1.height + c.p2.height) / 2;
        if (parentAverage < genAverageHeight) {
            return (int) (random.nextInt(MIN_CHILD, MAX_CHILD) * 0.99999);
        } else {
            return (int) (random.nextInt((MIN_CHILD + MAX_CHILD) / 2, MAX_CHILD));
        }
    }

    JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jCouples = new JSONArray();
        couples.forEach(c -> {
            jCouples.put(c.toJSON());
        });
        jsonObject.put("couples", jCouples);
        return jsonObject;
    }

    String statistics() {
        return statistics(null);
    }

    String statistics(String key) {
        int minHeight = Integer.MAX_VALUE;
        int maxHeight = Integer.MIN_VALUE;
        long avgHeight = 0;

        for (Couple c : couples) {
            if (minHeight > c.p1.height) {
                minHeight = c.p1.height;
            }
            if (minHeight > c.p2.height) {
                minHeight = c.p2.height;
            }
            if (maxHeight < c.p1.height) {
                maxHeight = c.p1.height;
            }
            if (maxHeight < c.p2.height) {
                maxHeight = c.p2.height;
            }
            avgHeight += c.p1.height;
            avgHeight += c.p2.height;
        }

        if (couples.isEmpty()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERROR", "LIFE IS OVER!");
            return jsonObject.toString();
        } else {
            avgHeight = avgHeight / (couples.size() * 2L);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("minHeight", minHeight);
            jsonObject.put("maxHeight", maxHeight);
            jsonObject.put("avgHeight", avgHeight);
            jsonObject.put("population", couples.size() * 2);
            jsonObject.put("generation", generation + 1);
            if (key != null) {
                return String.valueOf(jsonObject.get(key));
            } else {
                return jsonObject.toString();
            }
        }
    }

    public String toString() {
        return toJSON().toString();
    }


    Generation nextGen() {
        return new Generation(generation + 1, couples);
    }

}
