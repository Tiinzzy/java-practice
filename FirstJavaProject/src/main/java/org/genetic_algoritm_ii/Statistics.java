package org.genetic_algoritm_ii;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    static JSONObject getStat(List<Couple> couples) {
        Map<String, Integer> distribution = new HashMap<>();
        for (Couple c : couples) {
            distribution.putIfAbsent(c.p1.getRace(), 0);
            distribution.put(c.p1.getRace(), distribution.get(c.p1.getRace()) + 1);

            distribution.putIfAbsent(c.p2.getRace(), 0);
            distribution.put(c.p2.getRace(), distribution.get(c.p2.getRace()) + 1);
        }

        JSONObject jDistribution = new JSONObject();
        for (String key : distribution.keySet()) {
            jDistribution.put(key, distribution.get(key));
        }

        return jDistribution;
    }
}
