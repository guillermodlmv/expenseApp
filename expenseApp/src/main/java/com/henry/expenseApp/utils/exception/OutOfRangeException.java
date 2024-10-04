package com.henry.expenseApp.utils.exception;

public class OutOfRangeException extends Exception{
    public OutOfRangeException(String message) {
        super(message);
    }

    public static class ExpenseNotFoundException extends  Exception {
        public ExpenseNotFoundException(String message) {
            super(message);
        }
    }
}
