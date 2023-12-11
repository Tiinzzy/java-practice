package org.genetic_algoritm_i;

import org.json.JSONObject;

public class Person {
    int height;

    public Person(int height) {
        this.height = height;
    }

    JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("height", this.height);
        return jsonObject;
    }
}
