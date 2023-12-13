package org.gravity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Test {
    public static void main(String[] args) {
        Graph g = new Graph(3);
        g.printMatrix();

        JSONArray jsonMatrix = g.getMatrixAsJson();
        System.out.println(jsonMatrix.toString());
    }
}
