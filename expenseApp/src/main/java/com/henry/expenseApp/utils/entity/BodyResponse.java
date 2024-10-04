package com.henry.expenseApp.utils.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class BodyResponse<T> {
    private T data;

    private String message;

}
