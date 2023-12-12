package org.genetic_algoritm_ii;

import java.util.List;

public interface Environment {
    boolean canSurvive(Couple couple);

    List<Person> generateChildren(Couple couple);

}
