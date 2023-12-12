package org.genetic_algoritm_ii;

import java.util.HashMap;
import java.util.Map;

public class Config {
    static final int MIN_CHILDREN = 1;
    static final int MAX_CHILDREN = 3;

    static final int INITIAL_COUPLE_COUNT = 1000;

    static final int GENERATION_COUNT = 50;

    static final int JUNGLE_MIN_STRENGTH_THRESHOLD = 2;

    static final int JUNGLE_MAX_INTELLIGENCE_THRESHOLD = 2;

    static final int CITY_MAX_STRENGTH_THRESHOLD = 1;

    static final int CITY_MIN_INTELLIGENCE_THRESHOLD = 2;
    final static Map<Integer, String> LEVEL_TO_STR = new HashMap<>();

    static {
        LEVEL_TO_STR.put(1, "L");
        LEVEL_TO_STR.put(2, "M");
        LEVEL_TO_STR.put(3, "H");
    }
}
