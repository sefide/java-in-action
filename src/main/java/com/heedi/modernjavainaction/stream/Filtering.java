package com.heedi.modernjavainaction.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Filtering {

    public static void main(String[] args) {
        List<Dish> specialMenu = Arrays.asList(
                new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("pork", false, 800, Dish.Type.MEAT)
        );

        // takeWhile
        List<Dish> filteredMenu = specialMenu.stream()
                .peek(dish -> System.out.println(dish.getCalories())) // 350까지 확인
                .takeWhile(menu -> menu.getCalories() < 320)
                .collect(Collectors.toList());

        System.out.println("=================");

        List<Dish> filteredMenu2 = specialMenu.stream()
                .peek(dish -> System.out.println(dish.getCalories()))
                .filter(menu -> menu.getCalories() < 320)
                .collect(Collectors.toList());

        System.out.println("=================");

        // dropWhile
        specialMenu.stream()
                .dropWhile(menu -> menu.getCalories() < 320)
                .forEach(dish -> System.out.println(dish.getCalories()));

        // Quiz5
        List<Dish> meatMenu = specialMenu.stream()
                .filter(Dish::isMeat)
                .limit(2)
                .collect(Collectors.toList());

    }

}
