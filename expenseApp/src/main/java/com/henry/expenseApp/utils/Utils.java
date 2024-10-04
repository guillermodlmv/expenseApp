package com.henry.expenseApp.utils;

import com.henry.expenseApp.utils.exception.utils.Messages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils{
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static LocalDate convertDate(String date) {
        if (date.isEmpty()) {
            throw new IllegalArgumentException(Messages.EMPTY_DATE);
        }
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    public static <T> T  returnValueOrAlternativeIfNull(T value, T alternativeValue) {
        return value != null ? value : alternativeValue;
    }
}
