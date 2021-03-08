package com.heedi.modernjavainaction.dateTime;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class DateTimeChange {

    public static void main(String[] args) {
        changeDate();
        plusMinusDateTime();
    }

    private static void changeDate() {
        LocalDate date1 = LocalDate.of(2021, 8, 22);
        LocalDate date2 = date1.withYear(2002);
        LocalDate date3 = date2.withMonth(6);
        LocalDate date4 = date3.withDayOfMonth(21); // 8강

        System.out.println(date2);
        System.out.println(date3);
        System.out.println(date4);

        LocalDate date5 = date4.with(ChronoField.DAY_OF_MONTH, 25); // 4강
        System.out.println(date5);

        try {
            LocalDate date6 = date5.with(ChronoField.NANO_OF_SECOND, 3);
        } catch (UnsupportedTemporalTypeException e) {
            System.out.println("UnsupportedTemporalTypeException 발생 - LocalDate에 지정되지 않는 필드 지정");
        }
    }

    private static void plusMinusDateTime() {
        LocalDate date1 = LocalDate.of(2022, 1, 1);
        LocalDate date2 = date1.plusWeeks(3);
        LocalDate date3 = date2.minusYears(1);
        LocalDate date4 = date3.plus(15, ChronoUnit.DAYS);

        System.out.println(date2);
        System.out.println(date3);
        System.out.println(date4);
    }
}
