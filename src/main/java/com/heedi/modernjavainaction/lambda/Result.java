package com.heedi.modernjavainaction.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Result {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, "green", "america"),
                new Apple(155, "green", "singapore"),
                new Apple(120, "red", "taiwan"),
                new Apple(120, "green", "singapore"),
                new Apple(77, "red", "korea")
        );

        // sort(Comparator<? super E> c)
        inventory.sort(Comparator.comparing(Apple::getWeight));

        System.out.println(inventory);
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

        public String getCountry() {
            return country;
        }
    }
}
