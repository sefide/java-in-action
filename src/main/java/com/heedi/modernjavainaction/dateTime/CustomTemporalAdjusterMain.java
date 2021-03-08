package com.heedi.modernjavainaction.dateTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class CustomTemporalAdjusterMain {

    public static void main(String[] args) {
        changeWithCustomTemporalAdjuster();
        changeWithLambda();
        makeLambdaCustomTemporalAdjuster();
    }

    private static void changeWithCustomTemporalAdjuster() {
        NextWorkingDay nextWorkingDay = new NextWorkingDay();

        LocalDate monday = LocalDate.of(2021, 3, 8); // 월
        LocalDate sunday = LocalDate.of(2021, 3, 7); // 일
        LocalDate saturday = LocalDate.of(2021, 3, 6); // 토
        LocalDate friday = LocalDate.of(2021, 3, 5); // 금

        LocalDate tuesday = monday.with(nextWorkingDay);
        LocalDate monday2 = sunday.with(nextWorkingDay);
        LocalDate monday3 = saturday.with(nextWorkingDay);
        LocalDate monday4 = friday.with(nextWorkingDay);

        System.out.println(tuesday);
        System.out.println(monday2);
        System.out.println(monday3);
        System.out.println(monday4);
    }

    private static void changeWithLambda() {
        LocalDate date;

        LocalDate sunday = LocalDate.of(2021, 3, 7); // 일
        LocalDate saturday = LocalDate.of(2021, 3, 6); // 토
        LocalDate friday = LocalDate.of(2021, 3, 5); // 금

        date = sunday.with(temporal -> {
            DayOfWeek week = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));

            int addDay;
            if(week == DayOfWeek.FRIDAY) {
                addDay = 3;
            } else if(week == DayOfWeek.SATURDAY) {
                addDay = 2;
            } else {
                addDay = 1;
            }

            return temporal.plus(addDay, ChronoUnit.DAYS);
        });

        System.out.println(date);
    }

    private static void makeLambdaCustomTemporalAdjuster() {
        TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(temporal -> {
            DayOfWeek week = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));

            int addDay;
            if(week == DayOfWeek.FRIDAY) {
                addDay = 3;
            } else if(week == DayOfWeek.SATURDAY) {
                addDay = 2;
            } else {
                addDay = 1;
            }

            return temporal.plus(addDay, ChronoUnit.DAYS);
        });
    }
}
