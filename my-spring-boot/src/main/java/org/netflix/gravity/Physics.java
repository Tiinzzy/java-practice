package org.netflix.gravity;

public class Physics {

    private static final float G = 6.67430e-11f * (float) 10e12;

    static float getForce(Particle p1, Particle p2) {
        float dx = p1.x - p2.x;
        float dy = p1.y - p2.y;
        float distanceSquared = dx * dx + dy * dy;
        return G * (p1.m * p2.m) / distanceSquared;
    }

    static Particle getMergedParticle(Particle p1, Particle p2) {
        float m = p1.m + p2.m;
        float x = (p1.x * p1.m + p2.x * p2.m) / m;
        float y = (p1.y * p1.m + p2.y * p2.m) / m;
        return new Particle(x, y, m);
    }

    static void vanishParticles(Particle... particles) {
        for (Particle p : particles) {
            if (p != null) {
                p.x = -1;
                p.y = -1;
                p.m = -1;
                p.nearest = null;
            }
        }
    }

    static Particle copy(Particle p) {
        return new Particle(p.x, p.y, p.m);
    }
}
