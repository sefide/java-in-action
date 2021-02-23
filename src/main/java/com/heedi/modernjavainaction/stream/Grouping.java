package com.heedi.modernjavainaction.stream;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

public class Grouping {

    public static void main(String[] args) {
        List<Dish> menu = Dish.MENU_SAMPLE;

        Map<Dish.Type, List<Dish>> menuByType = menu.stream()
                .collect(groupingBy(Dish::getType));

        Map<CaloricLevel, List<Dish>> menuByCaloricLevel = menu.stream()
                .collect(groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));

        // 빈 리스트도 추가된 맵
        Map<Dish.Type, List<Dish>> menuByTypeWithEmpty = menu.stream()
                .collect(groupingBy(Dish::getType,
                        filtering(dish -> dish.getCalories() > 500, toList())));

        // 그룹화된 요소 조작 (getName의 List)
        Map<Dish.Type, List<String>> dishNamesByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        mapping(Dish::getName, toList())));

        Map<Dish.Type, Set<String>> dishNamesByType2 = menu.stream()
                .collect(groupingBy(Dish::getType,
                        flatMapping(dish -> Dish.dishTags.get(dish.getName()).stream(), toSet())));

        // 다수준 그룹화
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream()
                .collect(groupingBy(Dish::getType,
                        groupingBy(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        })));

        // maxBy
        Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        maxBy(Comparator.comparingInt(Dish::getCalories))));

        // 컬렉터 결과를 다른 형식에 적용하기
        Map<Dish.Type, Dish> mostCaloricDishByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)),
                                Optional::get)));

        Map<Dish.Type, Dish> mostCaloricDishByType2 = menu.stream()
                .collect(toMap(Dish::getType,
                        Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparingInt(Dish::getCalories))));

        // 그룹화 후 리듀싱 작업
        Map<Dish.Type, Integer> totalCaloriesByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        summingInt(Dish::getCalories)));

        // groupingBy & mapping
        Map<Dish.Type, Set<CaloricLevel>> s = menu.stream()
                .collect(groupingBy(Dish::getType,
                        mapping(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }, toCollection(HashSet::new))));
    }
}
