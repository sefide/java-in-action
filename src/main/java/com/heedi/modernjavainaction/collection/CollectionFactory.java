package com.heedi.modernjavainaction.collection;

import java.util.*;

public class CollectionFactory {

    public static void main(String[] args) {
        createArraysList();
        createList();
        createSet();
        createMap();
    }

    public static void createArraysList() {
        List<String> friends = new ArrayList<>();
        friends.add("momo");
        friends.add("dd");
        friends.add("tt");

        List<String> friends2 = Arrays.asList("momo", "dd", "t");

        friends2.set(2, "tt");
        try {
            friends2.add("coco");
        } catch (UnsupportedOperationException e) {
            System.out.println("'Arrays.asList()' 요소 추가 불가능");
        }

        System.out.println(friends2);
    }

    public static void createList() {
        List<String> friends = List.of("momo", "dd", "tt");

        try {
            friends.set(0, "cat");
        } catch (UnsupportedOperationException e) {
            System.out.println("'List.of()' 요소 추가/갱신 불가능");
        }
        System.out.println(friends);
    }

    private static void createSet() {
        Set<String> friends = Set.of("dodo", "toto");
        System.out.println(friends);

        try {
            friends.add("test");
            friends.clear();
        } catch (UnsupportedOperationException e) {
            System.out.println("'Set.of()' 요소 추가/갱신 불가능");
        }

        try {
            Set<String> friends2 = Set.of("nana", "nana");
        } catch (IllegalArgumentException e) {
            System.out.println("'Set.of()' 증복 요소 생성 불가능");
        }
    }

    private static void createMap() {
        Map<String, String> friends = Map.of("CAT", "momo", "DOG", "chulsu");

        Map<String, String> friends2 = Map.ofEntries(
                Map.entry("CAT", "momo"),
                Map.entry("DOG", "chulsu"),
                Map.entry("SHIP", "yang"));
        System.out.println(friends2);
    }
}
