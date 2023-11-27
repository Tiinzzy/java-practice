package org.netflix.directory_depth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyDirectory {
    private String name;
    private List<MyDirectory> children;

    public MyDirectory(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void addChild(MyDirectory child) {
        this.children.add(child);
    }

    public String getName() {
        return name;
    }

    public List<MyDirectory> getChildren() {
        return children;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", this.name);
        JSONArray jChildren = new JSONArray();
        for (MyDirectory child : children) {
            jChildren.put(child.toJson());
        }
        jsonObject.put("children", jChildren);
        return jsonObject;
    }

    @Override
    public String toString() {
        return toJson().toString(4);
    }
}