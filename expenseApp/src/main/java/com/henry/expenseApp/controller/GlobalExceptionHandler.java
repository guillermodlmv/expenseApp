package com.henry.expenseApp.controller;

import com.henry.expenseApp.utils.exception.NotFoundException;
import com.henry.expenseApp.utils.exception.utils.Messages;
import com.henry.expenseApp.utils.entity.ApiResponseEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {

        return new ApiResponseEntity<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponseEntity<String> handleNotFoundException(NotFoundException ex) {

        return new ApiResponseEntity<>(
                null,
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ApiResponseEntity<String> handleEntityNotFoundExceptionException(EntityNotFoundException ex) {
        return new ApiResponseEntity<>(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ApiResponseEntity<String> handleDateTimeParseException(Exception ex) {
        return new ApiResponseEntity<>(
                Messages.BAD_FORMAT_DATE,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ApiResponseEntity<String> handleException(Exception ex) {
        return new ApiResponseEntity<>(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
