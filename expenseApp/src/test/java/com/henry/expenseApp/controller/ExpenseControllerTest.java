package com.henry.expenseApp.controller;

import com.henry.expenseApp.dto.ExpenseDto;
import com.henry.expenseApp.entity.Category;
import com.henry.expenseApp.entity.Expense;
import com.henry.expenseApp.entity.User;
import com.henry.expenseApp.service.ExpenseService;
import com.henry.expenseApp.utils.entity.ApiResponseEntity;
import com.henry.expenseApp.utils.exception.NotFoundException;
import com.henry.expenseApp.utils.exception.utils.Messages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    @InjectMocks
    private ExpenseController expenseController;

    @Mock
    private ExpenseService expenseService;

    private Expense expense;

    private ExpenseDto expenseDto;

    private List<Expense> expenses;

    private User user;

    private List<Category> categories;

    private String stringDate = "2024/01/01";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("Test@mail.com");
        user.setActive(true);
        user.setExpenses(new ArrayList<>());
        categories = new ArrayList<>();
        expenses = new ArrayList<>();

        Category category1 = new Category(1L, "Categoria 1", "Categoria Fake 1", user, new ArrayList<>(), true);
        Category category2 = new Category(2L, "Categoria 2", "Categoria Fake 2", user, new ArrayList<>(), false);
        Category category3 = new Category(3L, "Categoria 3", "Categoria Fake 3", user, new ArrayList<>(), true);
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        user.setCategories(categories);

        Expense expense1 = new Expense(1L, 1000.0, LocalDate.now(), "Gasto 1", category1, user, true);
        Expense expense2 = new Expense(2L, 1200.0, LocalDate.of(2024, 1, 1), "Gasto 2", category1, user, true);
        Expense expense3 = new Expense(3L, 100.0, LocalDate.of(2023, 1, 1), "Gasto 3", category2, user, false);
        Expense expense4 = new Expense(4L, 10000.0, LocalDate.of(2010, 1, 1), "Gasto 4", category3, user, true);
        expenses.add(expense1);
        expenses.add(expense2);
        expenses.add(expense3);
        expenses.add(expense4);

        expense = new Expense(1L, 100.0, LocalDate.of(2024, 1, 1), "Test expense", new Category(), new User(), true);
        expenseDto = new ExpenseDto(1000.0, stringDate, "Gasto 1", categories.get(0).getId(), user.getId());
        expenses = new ArrayList<>();
        expenses.add(expense);
    }

    @Test
    @DisplayName("Debe obtener todos los gastos del usuario")
    @Order(1)
    void should_findAllExpensesByUserId() throws Exception {
        Long userId = 1L;

        when(expenseService.getAllExpensesByUserId(userId, null)).thenReturn(expenses);

        ApiResponseEntity<List<Expense>> response = expenseController.findAll(userId, null);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expenses, response.getData());
    }

    @Test
    @DisplayName("Debe retornar NotFoundException si el usuario no tiene gastos")
    @Order(2)
    void should_throwNotFoundException_when_userHasNoExpenses() throws Exception {
        Long userId = 1L;
        when(expenseService.getAllExpensesByUserId(userId, null)).thenReturn(new ArrayList<>());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            expenseController.findAll(userId, null);
        });

        assertEquals(Messages.USER_WITHOUT_EXPENSES, exception.getMessage());
    }

    @Test
    @DisplayName("Debe obtener gasto por id")
    @Order(3)
    void should_getExpenseById() throws Exception {
        Long expenseId = 1L;

        when(expenseService.getExpenseById(expenseId)).thenReturn(expense);

        ApiResponseEntity<Expense> response = expenseController.getById(expenseId);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expense, response.getData());
    }

    @Test
    @DisplayName("Debe retornar NotFoundException si el gasto no existe")
    @Order(4)
    void should_throwNotFoundException_when_expenseNotFound() throws Exception {
        Long expenseId = 1L;
        when(expenseService.getExpenseById(expenseId)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            expenseController.getById(expenseId);
        });

        assertEquals(Messages.EXPENSE_NOT_FOUND, exception.getMessage());
    }

    @Test
    @DisplayName("Debe crear un nuevo gasto")
    @Order(5)
    void should_createExpense() throws Exception {
        when(expenseService.addExpense(any(ExpenseDto.class))).thenReturn(expense);

        ApiResponseEntity<Expense> response = expenseController.create(expenseDto);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expense, response.getData());
    }

    @Test
    @DisplayName("Debe actualizar un gasto")
    @Order(6)
    void should_updateExpense() throws Exception {
        Long expenseId = 1L;
        when(expenseService.updateExpense(eq(expenseId), any(ExpenseDto.class))).thenReturn(expense);

        ApiResponseEntity<Expense> response = expenseController.update(expenseDto, expenseId);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expense, response.getData());
    }

    @Test
    @DisplayName("Debe eliminar un gasto")
    @Order(7)
    void should_deleteExpense() throws Exception {
        Long expenseId = 1L;

        ApiResponseEntity<Void> response = expenseController.delete(expenseId);

        assertEquals(HttpStatus.OK, response.getStatus());
        verify(expenseService, times(1)).removeExpenseById(expenseId);
    }
}