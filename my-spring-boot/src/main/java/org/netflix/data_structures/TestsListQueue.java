package org.netflix.data_structures;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class TestsListQueue {
    private final Random random = new Random();

    Node getRandomNode() {
        return new Node(UUID.randomUUID().toString(), random.nextInt(100));
    }

    void initialTests() {
        int[] a = new int[100];
        a[0] = 1;
        a[99] = 1;
        System.out.println(Arrays.toString(a));

        Edge edge = new Edge();
        edge.setFrom(new Node("Head", 10));
        edge.setTo(new Node("Tail", 10));
        System.out.println(edge.getFrom());
        System.out.println(edge.getTo().label() + ", " + edge.getTo().weight());

        TestsListQueue tests = new TestsListQueue();
        for (var i = 0; i < 10; i++) {
            System.out.println(tests.getRandomNode());
        }
    }

    void testList() {
        String path = "/home/tina/IdeaProjects/my-spring-boot";

//        List<Node> myArrayList = new ArrayList<>();
//        List<Node> myList = new LinkedList<>();
        List<Node> myList = new Vector<>();

        try (Stream<Path> paths = Files.list(Paths.get(path))) {
            paths.filter(p -> !Files.isDirectory(p)).forEach(p -> {
                try {
                    myList.add(new Node(p.toString(), Files.size(p)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        myList.forEach(System.out::println);

        for (var e : myList) {
            System.out.println(e.weight());
        }
    }

    void testQueue1() {
        LocalDate today = LocalDate.now();
//        Queue<LocalDate> queue = new ArrayDeque<>();
        Queue<LocalDate> queue = new PriorityQueue<>();

        for (int i = 0; i < 10; i++) {
            queue.add(today.minusDays(i));
        }

        queue.forEach(System.out::println);
        System.out.println(queue.size());

        System.out.println();

        System.out.print("queue.peek() ");
        System.out.println(queue.peek());
        System.out.println(queue.size());

        System.out.println();

        System.out.print("queue.poll() ");
        System.out.println(queue.poll());
        System.out.println(queue.size());
    }

    void testQueue2() {
        Queue<Integer> intQueue = new PriorityQueue<>();
        for (int i = 0; i < 10; i++) {
            intQueue.add(random.nextInt(100));
        }
//        intQueue.forEach(System.out::println);
        for (int i = 0; i < 10; i++) {
            Integer e = intQueue.poll();
            System.out.println(e);
        }

        System.out.println();

        Queue<Salary> salaryQueue = new PriorityQueue<>();
        for (int i = 0; i < 10; i++) {
            salaryQueue.add(new Salary(random.nextInt(100)));
        }

//        salaryQueue.forEach(System.out::println);
        for (int i = 0; i < 10; i++) {
            Salary s = salaryQueue.poll();
            System.out.println(s);
        }

    }

    public static void main(String[] args) {
        TestsListQueue tests = new TestsListQueue();
        tests.testQueue2();
    }
}


