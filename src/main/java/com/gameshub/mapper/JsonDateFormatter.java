package com.gameshub.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class JsonDateFormatter {

    public static LocalDate formatStringDateToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.GERMAN);

        if (date.length() == 4) {
            date += "-01-01";
        } else if (date.length() == 7) {
            date += "-01";
        }
        return LocalDate.parse(date, formatter);
    }
}
