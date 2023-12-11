package org.genetic_algoritm_ii;

import org.json.JSONObject;

import java.util.Random;

public class Person {
    final int intelligenceGene;
    final int strengthGene;

    Environment environment;

    private final static Random random = new Random();

    public Person(int intelligenceGene, int strengthGene) {
        this.intelligenceGene = intelligenceGene;
        this.strengthGene = strengthGene;
    }

    public Person() {
        intelligenceGene = 1 + random.nextInt(3);
        strengthGene = 1 + random.nextInt(3);
    }

    JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("intelligenceGene", intelligenceGene);
        jsonObject.put("strengthGene", strengthGene);
        jsonObject.put("race", getRace());
        return jsonObject;
    }

    String getRace() {
        return Config.LEVEL_TO_STR.get(intelligenceGene) + "I." + Config.LEVEL_TO_STR.get(strengthGene) + "S";
    }
}
