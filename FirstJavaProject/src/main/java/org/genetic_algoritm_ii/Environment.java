package org.genetic_algoritm_ii;

import java.util.List;

public interface Environment {
    boolean canSurvive(Person person);
    List<Person> generateChildren(Couple couple);
}
