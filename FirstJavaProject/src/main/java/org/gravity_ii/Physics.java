package org.gravity_ii;

public class Physics {


    static float getForce(Particle p1, Particle p2) {
        // return (m1 * m2) / ( (x1-x2)^2 + (y1-y2)^2 )
        return 0F;
    }

    static Particle getMergedParticle(Particle p1, Particle p2) {
        float m = p1.m + p2.m;
        float x = (p1.x + p2.x) / 2;
        float y = (p1.y + p2.y) / 2;
        return new Particle(x, y, m);
    }
}
