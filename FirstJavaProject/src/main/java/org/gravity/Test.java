package org.gravity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Test {
    public static void main(String[] args) {
        Graph g = new Graph(6);
        int count = 0;

        g.printMatrix();
        System.out.println("---------------------------------------------");
        g.merge();

//        g.printMatrix();
//
//        JSONArray jsonMatrix = g.getMatrixAsJson();
//        System.out.println(jsonMatrix.toString());
    }
}
