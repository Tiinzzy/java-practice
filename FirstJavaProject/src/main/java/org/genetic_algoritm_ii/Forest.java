package org.genetic_algoritm_ii;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Forest implements Environment {
    private final Random random = new Random();

    @Override
    public boolean canSurvive(Couple couple) {
        return canSurvive(couple.p1) && canSurvive(couple.p2);
    }

    public boolean canSurvive(Person person) {
        return (person.strengthGene >= Config.JUNGLE_MIN_STRENGTH_THRESHOLD &&
                person.intelligenceGene <= Config.JUNGLE_MAX_INTELLIGENCE_THRESHOLD);
    }

    @Override
    public List<Person> generateChildren(Couple couple) {
        List<Person> children = new ArrayList<>();
        if (canSurvive(couple.p1) && canSurvive(couple.p2)) {
            int childCount = Config.MIN_CHILDREN + random.nextInt(Config.MAX_CHILDREN - Config.MIN_CHILDREN + 1);
            for (int i = 0; i < childCount; i++) {
                children.add(couple.getChild());
            }
        }
        return children;
    }
}
