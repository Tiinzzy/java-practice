package org.genetic_algoritm_ii;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Couple {
    final Person p1;
    final Person p2;

    private final static Random random = new Random();

    public Couple(Person p1, Person p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("p1", p1.toJSON());
        jsonObject.put("p2", p2.toJSON());
        return jsonObject;
    }

    Person getChild() {
        float ig = (float) (p1.intelligenceGene + p2.intelligenceGene) / 2;
        float sg = (float) (p1.strengthGene + p2.strengthGene) / 2;

        int intelligenceGene;
        int strengthGene;
        if (random.nextBoolean()) {
            intelligenceGene = (int) Math.floor(ig);
            strengthGene = (int) Math.floor(sg);
            if (ig - intelligenceGene > 0) {
                intelligenceGene += 1;
            }
            if (sg - strengthGene > 0) {
                strengthGene += 1;
            }
        } else {
            intelligenceGene = (int) Math.floor(ig);
            strengthGene = (int) Math.floor(sg);
        }

        return new Person(intelligenceGene, strengthGene);
    }

    List<Person> getChildren() {
        int count = Config.MIN_CHILDREN + random.nextInt(Config.MAX_CHILDREN - Config.MIN_CHILDREN + 1);
        List<Person> children = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            children.add(getChild());
        }
        return children;
    }

}
