package com.heedi.modernjavainaction.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class UtilityDefaultMethod {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, "green", "america"),
                new Apple(155, "green", "singapore"),
                new Apple(120, "red", "taiwan"),
                new Apple(120, "green", "singapore"),
                new Apple(77, "red", "korea")
        );

        /* Comparator - reverse, thenComparing */
        // sort(Comparator<? super E> c)
        inventory.sort(Comparator.comparing(Apple::getWeight)
                .reversed()
                .thenComparing(Apple::getCountry));

        System.out.println(inventory);

        /* Predicate - negate, and, or */
        Predicate<Apple> redApple = apple -> "red".equals(apple.getColor());
        Predicate<Apple> notRedApple = redApple.negate();

        Predicate<Apple> heavyApple = apple -> apple.getWeight() > 150;
        Predicate<Apple> redAndHeavyApple = redApple.and(heavyApple);
        Predicate<Apple> redOrHeavyApple = redApple.or(heavyApple);

        /* Function - andThen, compose */
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g); // g(f(x))
        System.out.println(h.apply(3)); // 8

        Function<Integer, Integer> i = x -> x + 1;
        Function<Integer, Integer> k = x -> x * 2;
        Function<Integer, Integer> l = i.compose(k); // i(k(x))
        System.out.println(l.apply(3)); // 7

    }

    static class Apple {
        int weight;
        String color;
        String country;

        public Apple(int weight, String color, String country) {
            this.weight = weight;
            this.color = color;
            this.country = country;
        }

        public int getWeight() {
            return weight;
        }

        public String getColor() {
            return color;
        }

        public String getCountry() {
            return country;
        }
    }
}
