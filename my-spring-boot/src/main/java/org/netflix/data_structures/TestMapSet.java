package org.netflix.data_structures;

import org.json.JSONArray;

import java.util.*;
import java.util.stream.IntStream;

public class TestMapSet {
    private static final Random random = new Random();

    static void testSort() {
        TestMapSet app = new TestMapSet();
        List<CRCedString> myList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            myList.add(app.getRandomCRCedString());
        }

        for (int i = 0; i < 20; i++) {
            System.out.println(myList.get(i).getS());
        }
        System.out.println();

        Collections.sort(myList);

        for (int i = 0; i < 20; i++) {
            System.out.println(myList.get(i).getS());
        }
    }

    static void testMap2() {
        Map<String, Integer> nameAge = new LinkedHashMap<>();
        nameAge.put("kamran", 50);
        nameAge.put("tina", 20);
        nameAge.put("kiana", 22);
        nameAge.put("tatania", 22);

        for (String key : nameAge.keySet()) {
            System.out.println(key + " -> " + nameAge.get(key));
        }
    }

    static void testMap3() {
        TestMapSet app = new TestMapSet();
        Map<String, CRCedString> myMap = new HashMap<>();

        String fifthName = "";
        for (int i = 0; i < 20; i++) {
            CRCedString cs = app.getRandomCRCedString();
            myMap.put(cs.getS(), cs);
            if (i == 5) {
                fifthName = cs.getS();
            }
        }

        System.out.println(myMap.get(fifthName));

        for (var key : myMap.keySet()) {
            System.out.print(key + " , ");
        }

        System.out.println();
        System.out.println();

        for (var value : myMap.values()) {
            System.out.print(value + " , ");
        }
    }

    int getCRC(String s) {
        int crc = 0;
        for (var b : s.getBytes()) {
            crc += b;
        }
        return crc;
    }

    CRCedString getRandomCRCedString() {
        String s = UUID.randomUUID().toString();
        s = s.substring(0, 10 + random.nextInt(s.length() - 10));
        int crc = getCRC(s);
        return new CRCedString(s, s.length(), crc);
    }


    void testSet() {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(random.nextInt(10));
        }
        list.forEach(System.out::println);

        System.out.println();
        System.out.println();

        Set<Integer> set = new LinkedHashSet<>();
        for (int i = 0; i < 100; i++) {
            set.add(1 + random.nextInt(9));
        }
        set.add(0);
        set.forEach(System.out::println);
    }

    void testSortList() {
        List<CRCedString> list = new ArrayList<>();
        IntStream.range(1, 20).forEach(i -> {
            list.add(getRandomCRCedString());
        });

        System.out.println("Unsorted");
        list.stream().map(CRCedString::getS).forEach(System.out::println);

        System.out.println();
        System.out.println();

        System.out.println("Sorted by .s");
        list.sort((a, b) -> a.getS().compareTo(b.getS()));
        list.stream().map(CRCedString::getS).forEach(System.out::println);

        System.out.println();
        System.out.println();

        System.out.println("Sorted by .len");
        list.sort((a, b) -> Integer.valueOf(a.getLen()).compareTo(Integer.valueOf(b.getLen())));
//        list.sort(Comparator.comparing(CRCedString::getLen));
        list.stream().map(CRCedString::getLen).forEach(System.out::println);
    }

    void testSortMap() {
        Map<Integer, CRCedString> data = new LinkedHashMap<>();
        IntStream.range(0, 20).forEach(i -> {
            var d = getRandomCRCedString();
            data.put(d.getId(), d);
        });

        data.values().forEach(System.out::println);

        List<CRCedString> values = new ArrayList<>(data.values().stream().toList());
        values.sort(Comparator.comparing(CRCedString::getCrc));

        System.out.println("\n\nSorted by .CRC");
        values.forEach(System.out::println);


        System.out.println("\n\nMAP ported by .CRC");
        Map<Integer, CRCedString> sortedData = new LinkedHashMap<>();
        values.forEach(e -> {
            sortedData.put(e.getId(), e);
        });
        sortedData.values().forEach(System.out::println);


        JSONArray jList = new JSONArray();
        sortedData.values().forEach( e -> {
            jList.put(e.toJson());
        });
        System.out.println(jList.toString());
    }

    public static void main(String[] args) {
        var app = new TestMapSet();
        app.testSortMap();
    }
}
