package com.heedi.modernjavainaction.dateTime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

public class LocalDateTimeMain {

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();

        System.out.println(localDate.get(ChronoField.YEAR));
        System.out.println(localDate.get(ChronoField.DAY_OF_YEAR));
        System.out.println(localDate.get(ChronoField.MONTH_OF_YEAR));
        System.out.println(localDate.get(ChronoField.DAY_OF_MONTH));
        System.out.println(localDate.get(ChronoField.DAY_OF_WEEK));

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime ldt1 = LocalDateTime.of(2039, 8, 22, 23, 0, 0, 0);
        LocalDateTime ldt2 = LocalDateTime.of(date, time);
        LocalDateTime ldt3 = date.atTime(16, 0, 0);
        LocalDateTime ldt4 = date.atTime(time);
        LocalDateTime ldt5 = time.atDate(date);

        Instant instant1 = Instant.ofEpochSecond(3);
        Instant instant2 = Instant.ofEpochSecond(3, 0);
        Instant instant3 = Instant.ofEpochSecond(3, 1);
        Instant instant4 = Instant.ofEpochSecond(3, -1);

        Instant nowInstant = Instant.now();
        System.out.println("Instant toString : " + nowInstant.toString());
        System.out.println("Instant toEpochMilli : " + nowInstant.toEpochMilli());
        System.out.println("System currentTimeMillis : " + System.currentTimeMillis());
    }
}
