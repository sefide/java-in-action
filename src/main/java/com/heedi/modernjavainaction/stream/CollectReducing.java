package com.heedi.modernjavainaction.stream;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.reducing;

public class CollectReducing {

    public static void main(String[] args) {
        List<Dish> menu = Dish.MENU_SAMPLE;

        int sum = menu.stream()
                .collect(reducing(0, Dish::getCalories, Integer::sum));

        Optional<Integer> sum2 = menu.stream()
                .map(Dish::getCalories)
                .reduce(Integer::sum);

        System.out.println(sum);
        System.out.println(sum2);
    }
}
