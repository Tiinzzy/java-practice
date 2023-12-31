package org.genetic_algoritm_ii;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jungle implements Environment {
    private final Random random = new Random();

    @Override
    public boolean canSurvive(Couple couple) {
        return canSurvive(couple.p1) && canSurvive(couple.p2);
    }

    public boolean canSurvive(Person person) {
        return (random.nextInt(100) > Config.JUNGLE_DEATH_PERCENTAGE);
    }

    @Override
    public List<Person> generateChildren(Couple couple) {
        List<Person> children = new ArrayList<>();
        int childCount = couple.p1.strengthGene + couple.p2.strengthGene;
        for (int i = 0; i < childCount; i++) {
            children.add(couple.getChild());
        }
        return children;
    }
}
