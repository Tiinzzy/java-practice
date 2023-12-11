package org.genetic_algoritm_ii;

import java.util.HashMap;
import java.util.Map;

public class Config {
    static final int MIN_CHILDREN = 1;
    static final int MAX_CHILDREN = 3;

    static final int INITIAL_COUPLE_COUNT = 1000;

    static final int GENERATION_COUNT = 50;


    final static Map<Integer, String> LEVEL_TO_STR = new HashMap<>();

    static {
        LEVEL_TO_STR.put(1, "L");
        LEVEL_TO_STR.put(2, "M");
        LEVEL_TO_STR.put(3, "H");
    }


}
