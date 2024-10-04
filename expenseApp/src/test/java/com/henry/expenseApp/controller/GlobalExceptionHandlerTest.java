package com.henry.expenseApp.controller;

import com.henry.expenseApp.utils.entity.ApiResponseEntity;
import com.henry.expenseApp.utils.exception.NotFoundException;
import com.henry.expenseApp.utils.exception.utils.Messages;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void shouldHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        ApiResponseEntity<String> response = exceptionHandler.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals("Invalid argument", response.getMessage());
    }

    @Test
    void shouldHandleNotFoundException() {
        NotFoundException exception = new NotFoundException("Resource not found");

        ApiResponseEntity<String> response = exceptionHandler.handleNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals("Resource not found", response.getMessage());
    }

    @Test
    void shouldHandleEntityNotFoundException() {
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");

        ApiResponseEntity<String> response = exceptionHandler.handleEntityNotFoundExceptionException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals("Entity not found", response.getMessage());
    }

    @Test
    void shouldHandleDateTimeParseException() {
        DateTimeParseException exception = new DateTimeParseException("Date parsing error", "2023-10-01", 0);

        ApiResponseEntity<String> response = exceptionHandler.handleDateTimeParseException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(Messages.BAD_FORMAT_DATE, response.getMessage());
    }

    @Test
    void shouldHandleGeneralException() {
        Exception exception = new Exception("General error");

        ApiResponseEntity<String> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("General error", response.getMessage());
    }
}