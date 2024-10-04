package com.henry.expenseApp.utils.exception;

@Deprecated
public class UserNotFoundException  extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
