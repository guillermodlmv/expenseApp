package com.henry.expenseApp.utils.exception.utils;

import com.henry.expenseApp.utils.entity.ApiResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public final class ExceptionUtils {
    public static void creaLogError(String classType, String classPlace, String methodName, Exception e) {
        log.error("Error obtenido en " + classType + " de " + classPlace  + " en" + methodName + ": " + e);
    }

    public static ApiResponseEntity<?> getResponseEntityError(String message) {
        return new ApiResponseEntity<>(
                message,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
