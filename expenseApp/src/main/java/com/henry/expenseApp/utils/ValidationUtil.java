package com.henry.expenseApp.utils;

public class ValidationUtil {
    public static <T> void requireNonNullNotEmpty(T obj, String paramName) {
        if (obj == null || obj == "") {
            throw new IllegalArgumentException(paramName + " no debe ser vacío o nulo ");
        }
    }
}
