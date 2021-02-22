package com.heedi.modernjavainaction.stream;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.reducing;

public class CollectJoining {

    public static void main(String[] args) {
        List<Dish> menu = Dish.MENU_SAMPLE;

        String shortMenu1 = menu.stream()
                .map(Dish::getName)
                .collect(reducing((s1, s2) -> s1 + s2)).get();

        String shortMenu2 = menu.stream()
                .collect(reducing("", Dish::getName, (s1, s2) -> s1 + s2));

        String shortMenu3 = menu.stream()
                .map(Dish::getName)
                .reduce((s1, s2) -> s1 + s2).get();

        String shortMenu4 = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.joining());

        System.out.println(shortMenu1);
        System.out.println(shortMenu2);
        System.out.println(shortMenu3);
        System.out.println(shortMenu4);
    }
}
