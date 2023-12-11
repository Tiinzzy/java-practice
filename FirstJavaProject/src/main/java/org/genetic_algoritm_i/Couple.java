package org.genetic_algoritm_i;

import org.json.JSONObject;

import org.json.JSONObject;

public class Couple {
    Person p1;
    Person p2;

    public Couple(Person p1, Person p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("p1", p1.toJSON());
        jsonObject.put("p2", p2.toJSON());
        return jsonObject;
    }
}