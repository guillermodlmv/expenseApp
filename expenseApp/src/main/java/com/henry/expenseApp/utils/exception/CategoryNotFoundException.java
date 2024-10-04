package com.henry.expenseApp.utils.exception;

@Deprecated
public class CategoryNotFoundException extends  Exception {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
