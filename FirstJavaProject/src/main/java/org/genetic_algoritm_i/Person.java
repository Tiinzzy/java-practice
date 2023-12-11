package org.genetic_algoritm_i;

import org.json.JSONObject;
import java.util.Random;

public class Person {
    private int height;
    private int smart;
    private int muscular;
    private Random random = new Random();

    public Person(int height) {
        this.height = height;
        this.smart = random.nextInt(11); // values from 0 to 10
        this.muscular = random.nextInt(11); // values from 0 to 10
    }

    // Getters
    public int getHeight() {
        return height;
    }

    public int getSmart() {
        return smart;
    }

    public int getMuscular() {
        return muscular;
    }

    // Setters
    public void setHeight(int height) {
        this.height = height;
    }

    public void setSmart(int smart) {
        this.smart = smart;
    }

    public void setMuscular(int muscular) {
        this.muscular = muscular;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("height", this.height);
        jsonObject.put("smart", this.smart);
        jsonObject.put("muscular", this.muscular);
        return jsonObject;
    }
}