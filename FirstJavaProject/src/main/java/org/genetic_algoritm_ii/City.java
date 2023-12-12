package org.genetic_algoritm_ii;

import java.util.List;

public class City implements Environment {
    @Override
    public boolean canSurvive(Person person) {
        return false;
    }

    @Override
    public List<Person> generateChildren(Couple couple) {
        return null;
    }
}
