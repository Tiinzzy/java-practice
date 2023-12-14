package org.gravity_ii;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

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
        particles = removeDuplicatedAndMerge();
    }

    void mergeParticles() {
        List<Particle> newParticles = new ArrayList<>();
        Set<Particle> merged = new HashSet<>();

        for (Particle p1 : particles) {
            if (merged.contains(p1)) {
                continue;
            }

            Particle strongest = null;
            float maxForce = 0;

            for (Particle p2 : particles) {
                if (p1 != p2 && !merged.contains(p2)) {
                    float force = Physics.getForce(p1, p2);
                    if (force > maxForce) {
                        maxForce = force;
                        strongest = p2;
                    }
                }
            }

            if (strongest != null && canBeMerged(maxForce)) {
                Particle mergedParticle = Physics.getMergedParticle(p1, strongest);
                newParticles.add(mergedParticle);
                merged.add(strongest);
                merged.add(p1);
            } else if (!merged.contains(p1)) {
                newParticles.add(p1);
            }
        }

        particles = newParticles;
    }

    boolean canBeMerged(float force) {
        float mergeForceThreshold = 0.5F;
        return force >= mergeForceThreshold;
    }

    List<Particle> removeDuplicatedAndMerge() {
        Set<Particle> noDuplicate = new HashSet<>();

        for (Particle p : particles) {
            if (p.y < 0) {
                continue;
            } else if (p.y >= 0 && p.nearest.y < 0) {
                Particle np = Physics.copy(p);
                noDuplicate.add(np);
                Physics.vanishParticles(p, p.nearest);
            } else if (!noDuplicate.contains(p) && !noDuplicate.contains(p.nearest)) {
                Particle np = Physics.getMergedParticle(p, p.nearest);
                noDuplicate.add(np);
                Physics.vanishParticles(p, p.nearest);
            }
        }

        return noDuplicate.stream().toList();
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
