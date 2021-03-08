package com.heedi.modernjavainaction.dateTime;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextWorkingDay implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
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
    }
}
