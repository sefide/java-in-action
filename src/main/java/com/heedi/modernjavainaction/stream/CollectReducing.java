package com.heedi.modernjavainaction.stream;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.maxBy;

public class CollectReducing {

    public static void main(String[] args) {
        List<Dish> menu = Dish.MENU_SAMPLE;

        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);

        Optional<Dish> mostCalorieDish = menu.stream()
                .collect(maxBy(dishCaloriesComparator));

        Optional<Dish> mostCalorieDish2 = menu.stream()
                .max(dishCaloriesComparator);

        System.out.println(mostCalorieDish);
    }
}
