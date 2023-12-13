package org.gravity_ii;

import org.json.JSONArray;

public class Particle {
    float x = 0;
    float y = 0;
    float m = 1;

    Particle nearest = null;
    float nearestForce = 0;

    public Particle(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Particle(float x, float y, float m) {
        this.x = x;
        this.y = y;
        this.m = m;
    }

    JSONArray toJSON() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(x);
        jsonArray.put(y);
        jsonArray.put(m);
        return jsonArray;
    }
}