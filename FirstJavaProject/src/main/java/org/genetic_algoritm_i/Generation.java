package org.genetic_algoritm_i;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Generation {
    private int generationNumber;
    private List<Couple> couples;
    private Random random = new Random();

    public Generation(int generationNumber, List<Couple> couples) {
        this.generationNumber = generationNumber;
        this.couples = couples;
    }

    public Generation(int generationNumber, int count) {
        this.generationNumber = generationNumber;
        this.couples = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Person p1 = new Person(random.nextInt(231) + 100); // Random height between 100 and 230
            Person p2 = new Person(random.nextInt(231) + 100);
            this.couples.add(new Couple(p1, p2));
        }
    }

    public Generation nextGeneration() {
        List<Couple> newCouples = new ArrayList<>();
        for (Couple c : this.couples) {
            int childrenCount = random.nextInt(6); // Random number of children between 0 and 5
            for (int i = 0; i < childrenCount; i++) {
                Child child = new Child(c.p1, c.p2);
                Person partner = new Person(random.nextInt(231) + 100); // Random height for partner
                newCouples.add(new Couple(child, partner));
            }
        }
        return new Generation(this.generationNumber + 1, newCouples);
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jCouples = new JSONArray();
        for (Couple couple : this.couples) {
            jCouples.put(couple.toJSON());
        }
        jsonObject.put("couples", jCouples);
        jsonObject.put("generation", this.generationNumber);
        return jsonObject;
    }

    public JSONObject statistics() {
        int minHeight = Integer.MAX_VALUE;
        int maxHeight = Integer.MIN_VALUE;
        int minSmart = Integer.MAX_VALUE;
        int maxSmart = Integer.MIN_VALUE;
        int minMuscular = Integer.MAX_VALUE;
        int maxMuscular = Integer.MIN_VALUE;
        long totalHeight = 0;
        long totalSmart = 0;
        long totalMuscular = 0;

        for (Couple couple : this.couples) {
            for (Person p : new Person[]{couple.p1, couple.p2}) {
                minHeight = Math.min(minHeight, p.getHeight());
                maxHeight = Math.max(maxHeight, p.getHeight());
                minSmart = Math.min(minSmart, p.getSmart());
                maxSmart = Math.max(maxSmart, p.getSmart());
                minMuscular = Math.min(minMuscular, p.getMuscular());
                maxMuscular = Math.max(maxMuscular, p.getMuscular());
                totalHeight += p.getHeight();
                totalSmart += p.getSmart();
                totalMuscular += p.getMuscular();
            }
        }

        int totalPeople = this.couples.size() * 2;
        JSONObject stats = new JSONObject();
        stats.put("minHeight", minHeight);
        stats.put("maxHeight", maxHeight);
        stats.put("avgHeight", totalPeople == 0 ? 0 : totalHeight / totalPeople);
        stats.put("minSmart", minSmart);
        stats.put("maxSmart", maxSmart);
        stats.put("avgSmart", totalPeople == 0 ? 0 : totalSmart / totalPeople);
        stats.put("minMuscular", minMuscular);
        stats.put("maxMuscular", maxMuscular);
        stats.put("avgMuscular", totalPeople == 0 ? 0 : totalMuscular / totalPeople);
        stats.put("generationNumber", this.generationNumber);

        return stats;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }
}