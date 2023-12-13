package org.gravity_ii;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Universe {
    private final Random random = new Random();

    final int size;

    int time = 0;
    List<Particle> particles = new ArrayList<>();

    public Universe(int size) {
        this.size = size;
    }

    void init(int particleCount) {
        for (int i = 0; i < particleCount; i++) {
            particles.add(new Particle(getRandomX(), getRandomY()));
        }
    }

    float getRandomX() {
        return random.nextFloat((float) size);
    }

    float getRandomY() {
        return random.nextFloat((float) size);
    }

    void tick() {
        time += 1;

        calculateForces();

        mergeParticles();
    }

    void mergeParticles() {
        // we need to sort particles based on nearestForce

        // STEP 1 remove duplicates   A -> B is the same as B -> A, remove the second one
        // you need a function canBeMerged()

        // STEP 2 loop over result of STEP 1
        // if canBeMerged merge two particles and add them to a new list
        // if not just add the source particle to the list

    }

    void calculateForces() {
        for (Particle p : particles) {
            p.nearest = getStrongestParticleNearMe(p);
            p.nearestForce = Physics.getForce(p, p.nearest);
        }
    }

    Particle getStrongestParticleNearMe(Particle p) {
        Particle nearest = null;
        float maxForce = 0;
        for (Particle n : particles) {
            if (n != p) {
                float tempForce = Physics.getForce(p, n);
                if (maxForce < tempForce) {
                    maxForce = tempForce;
                    nearest = n;
                }
            }
        }
        return nearest;
    }

    JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("size", size);
        jsonObject.put("comment", "This universe is %d by %d".formatted(size, size));
        jsonObject.put("time", time);
        jsonObject.put("particleCount", particles.size());

        JSONArray jMass = new JSONArray();
        for (Particle m : particles) {
            jMass.put(m.toJSON());
        }
        jsonObject.put("particles", jMass);

        return jsonObject;
    }
}
