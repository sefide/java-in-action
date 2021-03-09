package com.heedi.modernjavainaction.dateTime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class DateTimeFormatParsing {

    public static void main(String[] args) {
        formatParsingDateTime();
        makeDateTimeFormatter();
        makeDateTimeFormatterBuilder();
    }

    private static void formatParsingDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date1 = LocalDate.of(2020, 2, 20);
        String formattedDate = date1.format(dateTimeFormatter);

        System.out.println(formattedDate); // 20/02/2020

        LocalDate date2 = LocalDate.parse(formattedDate, dateTimeFormatter);
        System.out.println(date2);
    }

    private static void makeDateTimeFormatter() {
        DateTimeFormatter franceFormatter = DateTimeFormatter.ofPattern("d, MMMM yyyy", Locale.FRANCE);
        LocalDate date1 = LocalDate.of(2020, 12, 25);
        String formattedDate = date1.format(franceFormatter);

        System.out.println(formattedDate); // 25, décembre 2020

        LocalDate date2 = LocalDate.parse(formattedDate, franceFormatter);
        System.out.println(date2);
    }

    private static void makeDateTimeFormatterBuilder() {
        DateTimeFormatter americaFormatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.ENGLISH);

        // parseCaseInsensitive, parseCaseSensitive, parseStrict, parseLenient 의 차이는 ?

        LocalDate date1 = LocalDate.now();
        String formattedDate = date1.format(americaFormatter);

        System.out.println(formattedDate); // 9. March 2021
    }
}
