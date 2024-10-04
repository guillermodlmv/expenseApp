package com.henry.expenseApp.utils.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Getter
@Setter
public class ApiResponseEntity<T> extends ResponseEntity<BodyResponse<T>>{
    private String message;
    private HttpStatus status;
    private T data;

    // Constructor para éxito
    public ApiResponseEntity(T data, String message, HttpStatus status) {
        super(new BodyResponse<>(data, message), status);
        this.message = message;
        this.status = status;
        this.data = data;
    }

    // Constructor para error
    public ApiResponseEntity(String message, HttpStatus status) {
        super(new BodyResponse<>(null, message), status);
        this.message = message;
        this.status = status;
    }


}
