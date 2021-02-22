package com.heedi.modernjavainaction.stream;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;

public class CollectMinMax {

    public static void main(String[] args) {
        List<Dish> menu = Dish.MENU_SAMPLE;

        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);

        Optional<Dish> mostCalorieDish = menu.stream()
                .collect(maxBy(dishCaloriesComparator));

        Optional<Dish> mostCalorieDish2 = menu.stream()
                .max(dishCaloriesComparator);

        Optional<Dish> leastCalorieDish = menu.stream()
                .collect(minBy(dishCaloriesComparator));

        Optional<Dish> leastCalorieDish2 = menu.stream()
                .min(dishCaloriesComparator);

        System.out.println(mostCalorieDish);
        System.out.println(mostCalorieDish2);
        System.out.println(leastCalorieDish);
        System.out.println(leastCalorieDish2);
    }
}
