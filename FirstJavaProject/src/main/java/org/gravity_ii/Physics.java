package org.gravity_ii;

public class Physics {

    private static final float G = 6.67430e-11f;

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
}
