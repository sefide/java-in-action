package com.heedi.modernjavainaction.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListCollector {

    public static void main(String[] args) {
        Stream<Dish> menuStream1 = Dish.MENU_SAMPLE.stream();
        Stream<Dish> menuStream2 = Dish.MENU_SAMPLE.stream();
        Stream<Dish> menuStream3 = Dish.MENU_SAMPLE.stream();

        List<Dish> dishes1 = menuStream1
                .collect(Collectors.toList());

        List<Dish> dishes2 = menuStream2
                .collect(new ToListCollector<>());

        // 간결, 축약, 떨어지는 가독성
        // Characteristics 전달 불가능
        List<Dish> dishes3 = menuStream3
                .collect(ArrayList::new, List::add, List::addAll);

        System.out.println();
    }
}
