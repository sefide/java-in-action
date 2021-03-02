package com.heedi.modernjavainaction.collection;

import java.util.*;

import static java.util.Map.entry;

public class MapFactory {

    public static void main(String[] args) {
        forEach();
        entryComparingBy();
        getOrDefault();
        computingOnMaps();
        remove();
        replace();
        merge();
    }

    private static void forEach() {
        Map<String, Integer> ageOfFriends = Map.of(
                "momo", 4,
                "dd", 6,
                "toto", 1
        );

        for (Map.Entry<String, Integer> entry : ageOfFriends.entrySet()) {
            String friend = entry.getKey();
            Integer age = entry.getValue();
            System.out.println(friend + " is " + age + " years old");
        }

        ageOfFriends.forEach((friend, age) -> System.out.println(friend + " is " + age + " years old"));
    }

    private static void entryComparingBy() {
        Map<String, String> favouriteMovies = Map.ofEntries(
                Map.entry("coco", "Star Wars"),
                Map.entry("chuchu", "Matrix"),
                Map.entry("lala", "James Bond")
        );

        favouriteMovies.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(System.out::println);
    }

    private static void getOrDefault() {
        Map<String, String> favouriteMovies = Map.ofEntries(
                Map.entry("dodo", "Pirates of the Caribbean"),
                Map.entry("tt", "notebook")
        );

        System.out.println(favouriteMovies.getOrDefault("tt", "sweet candy"));
        System.out.println(favouriteMovies.getOrDefault("momo", "Harry Potter"));
    }

    private static void computingOnMaps() {
        Map<String, List<String>> friendsToMovies = new HashMap<>();

        String friend = "pingping";
        List<String> movies = friendsToMovies.get(friend);

        // if문으로 값이 null이면 리스트 생성하여 추가
        if (movies == null) {
            movies = new ArrayList<>();
            friendsToMovies.put(friend, movies);
        }
        movies.add("KokSung");
        System.out.println(friendsToMovies);

        // computeIfAbsent
        friendsToMovies.clear();
        friendsToMovies.computeIfAbsent("pingping", name -> new ArrayList<>())
                .add("Parasite"); // 새로 추가한 value 반환하여 바로 add 실행
        System.out.println(friendsToMovies);
    }

    private static void remove() {
        Map<String, String> favouriteMovies = new HashMap<>();
        favouriteMovies.put("toto", "Jack Reacher 2");
        favouriteMovies.put("lulu", "Matrix");
        favouriteMovies.put("lala", "James Bond");

        String key = "toto";
        String value = "Jack Reacher 2";
        favouriteMovies.remove(key, value);
        System.out.println(favouriteMovies);
    }

    private static void replace() {
        Map<String, String> favouriteMovies = new HashMap<>();
        favouriteMovies.put("dd", "Star Wars");
        favouriteMovies.put("lala", "james bond");

        favouriteMovies.replaceAll((friend, movie) -> movie.toUpperCase());
        System.out.println(favouriteMovies);
    }

    private static void merge() {
        Map<String, String> family = Map.ofEntries(
                entry("dd", "Star Wars"),
                entry("lala", "James Bond"));
        Map<String, String> friends = Map.ofEntries(entry("momo", "Star Wars"));

        Map<String, String> everyone = new HashMap<>(family);
        everyone.putAll(friends);
        System.out.println(everyone);

        Map<String, String> friends2 = Map.ofEntries(
                entry("dd", "Star Wars2"),
                entry("tt", "Matrix"));

        Map<String, String> everyone2 = new HashMap<>(family);
        friends2.forEach((k, v) -> everyone2.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
        System.out.println(everyone2);
    }
}
