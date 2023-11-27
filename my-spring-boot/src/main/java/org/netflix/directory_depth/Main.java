package org.netflix.directory_depth;

public class Main {
    public static void main(String[] args) {
        DirectoryTree tree = new DirectoryTree("/home/tina/IdeaProjects/my-spring-boot/src/main");
        tree.buildTree(50);
        tree.displayTree();
        tree.saveJsonToFile();
    }
}
