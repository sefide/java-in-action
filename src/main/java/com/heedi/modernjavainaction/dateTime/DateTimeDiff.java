package com.heedi.modernjavainaction.dateTime;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

public class DateTimeDiff {

    public static void main(String[] args) {
        diffDate();
        diffTime();
        checkDiff();
    }

    private static void diffDate() {
        LocalDate ld1 = LocalDate.of(2021, 1, 1);
        LocalDate ld2 = LocalDate.of(2021, 2, 1);

        Period period = Period.between(ld1, ld2);
        System.out.println(period); // P1M

        LocalDate ld3 = LocalDate.of(2021, 1, 1);
        LocalDate ld4 = LocalDate.of(2021, 2, 13);

        Period period2 = Period.between(ld3, ld4);
        System.out.println(period2); // P1M12D
    }

    private static void diffTime() {
        LocalTime lt1 = LocalTime.of(9, 30, 0);
        LocalTime lt2 = LocalTime.of(18, 30, 0);

        Duration duration = Duration.between(lt1, lt2);
        System.out.println(duration); // PT9H

        LocalTime lt3 = LocalTime.of(3, 33, 33);
        LocalTime lt4 = LocalTime.of(4, 55, 59);

        Duration duration2 = Duration.between(lt3, lt4);
        System.out.println(duration2); // PT1H22M26S
    }

    private static void checkDiff() {
        LocalDate ld1 = LocalDate.of(2019, 12, 31);
        LocalDate ld2 = LocalDate.of(2021, 1, 3);

        Period period = Period.between(ld1, ld2);
        System.out.println(period.getDays() == 3); // true
    }
}
