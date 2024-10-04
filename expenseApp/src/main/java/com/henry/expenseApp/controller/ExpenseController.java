package com.henry.expenseApp.controller;

import com.henry.expenseApp.dto.ExpenseDto;
import com.henry.expenseApp.entity.*;
import com.henry.expenseApp.utils.exception.NotFoundException;
import com.henry.expenseApp.utils.exception.utils.Messages;
import com.henry.expenseApp.service.ExpenseService;
import com.henry.expenseApp.utils.entity.ApiResponseEntity;
import com.henry.expenseApp.utils.entity.ExpenseFilter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {
    @Autowired
    ExpenseService expenseService;

    @SneakyThrows
    @Operation(
            summary = "Obtener los gastos del usuario por id",
            description="Debe enviar el id de usuario",
            tags= {"Expense"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="400", description = Messages.USER_WITHOUT_EXPENSES),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    @GetMapping("/user/{userId}")
    public ApiResponseEntity<List<Expense>> findAll(
            @PathVariable Long userId,
            @ModelAttribute ExpenseFilter filter
    ) {
        List<Expense> allExpensesByUserId = expenseService.getAllExpensesByUserId(userId, filter);
        if (allExpensesByUserId.isEmpty()) {
            throw new NotFoundException(Messages.USER_WITHOUT_EXPENSES);
        }
        return new ApiResponseEntity<>(
                allExpensesByUserId,
                Messages.SUCCESS,
                HttpStatus.OK
        );
    }

    @SneakyThrows
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener un gasto por su id",
            description="Debe enviar el id del gasto",
            tags= {"Expense"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="400", description = Messages.EXPENSE_NOT_FOUND),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    public ApiResponseEntity<Expense> getById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        if (expense == null) {
            throw new NotFoundException(Messages.EXPENSE_NOT_FOUND);
        }
        return new ApiResponseEntity<>(
                expense,
                Messages.SUCCESS,
                HttpStatus.OK
        );
    }

    @PostMapping
    @Operation(
            summary = "Crear gasto",
            description="Debe enviar el id de usuario, amount, date, description y categoryId",
            tags= {"Expense"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    public ApiResponseEntity<Expense> create(@RequestBody ExpenseDto expense) {
        Expense newExpense = expenseService.addExpense(expense);
        return new ApiResponseEntity<>(
                newExpense,
                Messages.SUCCESS,
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Actualizar gasto",
            description="Puedes enviar el id de usuario, amount, date, description y categoryId",
            tags= {"Expense"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    public ApiResponseEntity<Expense> update(@RequestBody ExpenseDto expense, @PathVariable Long id) {
        Expense updatedExpense = expenseService.updateExpense(id, expense);
        return new ApiResponseEntity<>(
                updatedExpense,
                Messages.SUCCESS,
                HttpStatus.OK
        );

    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar gasto",
            description="Debes enviar categoryId",
            tags= {"Expense"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    public ApiResponseEntity<Void> delete(@PathVariable Long id) {
            expenseService.removeExpenseById(id);
        return new ApiResponseEntity<>(
                Messages.SUCCESS,
                HttpStatus.OK
        );
    }
}
