package org.genetic_algoritm_ii;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class City implements Environment {

    private final Random random = new Random();

    @Override
    public boolean canSurvive(Couple couple) {
        return (random.nextInt(100) > Config.CITY_DEATH_PERCENTAGE);
    }

    @Override
    public List<Person> generateChildren(Couple couple) {
        List<Person> children = new ArrayList<>();
        if (canSurvive(couple)) {
            int childCount = couple.p1.intelligenceGene + couple.p2.intelligenceGene;
            for (int i = 0; i < childCount; i++) {
                children.add(couple.getChild());
            }
        }
        return children;
    }
}
