package com.heedi.modernjavainaction.stream;

import java.util.List;
import java.util.LongSummaryStatistics;

import static java.util.stream.Collectors.*;

public class CollectSumming {

    public static void main(String[] args) {
        List<Dish> menu = Dish.MENU_SAMPLE;

        // 합계
        int totalCalories = menu.stream()
                .collect(summingInt(Dish::getCalories));

        int totalCalories2 = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();

        System.out.println(totalCalories);
        System.out.println(totalCalories2);

        // 평균
        double averageCalories = menu.stream()
                .collect(averagingDouble(Dish::getCalories));

        System.out.println(averageCalories);

        // 통계
        LongSummaryStatistics menuStatistics = menu.stream()
                .collect(summarizingLong(Dish::getCalories));

        System.out.println(menuStatistics);
    }
}
