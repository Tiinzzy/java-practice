package org.netflix.directory_depth;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class DirectoryTree {
    private final String output_path = "/home/tina/IdeaProjects/my-spring-boot/src/main/resources/output.json";

    private String path;
    private List<MyDirectory> level;

    public DirectoryTree(String path) {
        this.path = path;
        this.level = new ArrayList<>();
    }

    public void buildTree(int depth) {
        buildTreeHelper(path, level, depth, 0);
    }

    private void buildTreeHelper(String currentPath, List<MyDirectory> currentLevel, int maxDepth, int currentDepth) {
        if (currentDepth > maxDepth) {
            return;
        }

        File directory = new File(currentPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    MyDirectory dir = new MyDirectory(file.getName());
                    currentLevel.add(dir);
                    buildTreeHelper(file.getPath(), dir.getChildren(), maxDepth, currentDepth + 1);
                }
            }
        }
    }

    public JSONArray toJson() {
        JSONArray jTree = new JSONArray();
        for (MyDirectory myDirectory : level) {
            jTree.put(myDirectory.toJson());
        }
        return jTree;
    }

    public String toString() {
        return toJson().toString(4);
    }

    public void displayTree() {
        System.out.println(this.toString());
    }

    public void saveJsonToFile() {
        try {
            Files.write(Paths.get(output_path), this.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPath() {
        return path;
    }

    public List<MyDirectory> getLevel() {
        return level;
    }
}