package com.henry.expenseApp.service.impl;

import com.henry.expenseApp.dto.ExpenseDto;
import com.henry.expenseApp.entity.Category;
import com.henry.expenseApp.entity.Expense;
import com.henry.expenseApp.entity.User;
import com.henry.expenseApp.repository.CategoryRepository;
import com.henry.expenseApp.repository.ExpenseRepository;
import com.henry.expenseApp.repository.UserRepository;
import com.henry.expenseApp.utils.entity.ExpenseFilter;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.henry.expenseApp.utils.Utils.convertDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpenseServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private User user;

    private List<Category> categories;

    private Category categoryResponse;

    private List<Expense> expenses;

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

        categoryResponse = new Category(4L, "Categoria 4", "Categoria Fake 4", user, new ArrayList<>(), true);
    }


    @Test
    @DisplayName("Debe encontrar todos los gastos activos del usuario")
    @Order(1)
    void should_findAllUserExpenses_when_userHasThemAndAreActive() {
        List<Expense> expectedResult = expenses.stream()
                .filter(expense -> {
                    return Objects.equals(expense.getUser().getId(), user.getId()) && expense.isActive();
                })
                .toList();

        when(expenseRepository.findAll(any(Specification.class))).thenReturn(expectedResult);

        List<Expense> expensesResult = expenseService.getAllExpensesByUserId(1L, new ExpenseFilter(null, null, null, null, null));

        assertNotNull(expensesResult);
        assertEquals(expectedResult, expensesResult);
        assertEquals(expectedResult.size(), 3);
    }

    @Test
    @DisplayName("Debe encontrar todos los gastos activos con filtros del usuario")
    @Order(2)
    void should_findAllUserExpenses_when_userHasThemWithFiltersMatchAndAreActive() {
        ExpenseFilter expenseFilter = new ExpenseFilter(1000.0, 1500.0, stringDate, null,"gas");
        LocalDate initDate = convertDate(expenseFilter.getInitDate());
        List<Expense> expectedResult = expenses.stream()
                .filter(expense -> {
                    return Objects.equals(expense.getUser().getId(), user.getId())
                            && expense.isActive()
                            && expense.getAmount() > expenseFilter.getMinAmount()
                            && expense.getAmount() < expenseFilter.getMaxAmount()
                            && (expense.getDate().isAfter(initDate) || expense.getDate().isEqual(initDate))
                            && expense.getDescription().toLowerCase().contains(expenseFilter.getDescription().toLowerCase());
                })
                .toList();

        when(expenseRepository.findAll(any(Specification.class))).thenReturn(expectedResult);

        List<Expense> expensesResult = expenseService.getAllExpensesByUserId(1L, expenseFilter);

        assertNotNull(expensesResult);
        assertEquals(expectedResult, expensesResult);
        assertEquals(expectedResult.size(), 1);
    }

    @Test
    @DisplayName("Debe retornar DateTimeParseException si la fecha no cumple formato \"yyyy/MM/dd\"")
    @Order(3)
    void should_throwException_when_anyDateOnFilterHasBadFormat() {
        ExpenseFilter expenseWrongDateFilter = new ExpenseFilter(null, null, "24/01/01", null,null);

        DateTimeParseException exception = assertThrows(DateTimeParseException.class, () -> {
            expenseService.getAllExpensesByUserId(user.getId(), expenseWrongDateFilter);  // Aquí se espera que falle
        });

        assertEquals("Text '24/01/01' could not be parsed at index 0", exception.getMessage());
    }

    @Test
    @DisplayName("Debe encontrar el gasto con el id buscado siempre que este activo")
    @Order(4)
    void should_returnExpense_when_existsAndIsActive() {
        Expense expectedResponse = expenses.get(0);

        when(expenseRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(expectedResponse));

        Expense expenseResult = expenseService.getExpenseById(1L);

        assertNotNull(expenseResult);
        assertEquals(expectedResponse, expenseResult);
    }

    @Test
    @DisplayName("Debe arrojar EntityNotFoundException si no existe gasto con ese id")
    @Order(5)
    void should_throwException_when_expenseNotExistOrIsNotActive() {
        when(expenseRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            expenseService.getExpenseById(1L);  // Aquí se espera que falle
        });

        assertEquals("El gasto no fue encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Debe encontrar el gasto con el id buscado siempre que este activo")
    @Order(6)
    void should_returnCreatedExpense_when_AllFieldsAreCorrect() {
        Expense expectedResponse = expenses.get(0);

        when(expenseRepository.save(any(Expense.class))).thenReturn(expectedResponse);
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(categories.get(0)));

        ExpenseDto expenseDto = new ExpenseDto(1000.0, stringDate, "Gasto 1", categories.get(0).getId(), user.getId());
        Expense expenseResponse = expenseService.addExpense(expenseDto);

        assertEquals(expectedResponse.getAmount(), expenseResponse.getAmount());
        assertEquals(expectedResponse.getId(), expenseResponse.getId());
        assertEquals(expectedResponse.getDescription(), expenseResponse.getDescription());
        assertEquals(expectedResponse.getUser().getId(), expenseResponse.getUser().getId());
        assertTrue(expenseResponse.isActive());

    }

    @Test
    @DisplayName("Debe retornar el gasto actualizado")
    @Order(7)
    void should_returnUpdatedExpense_when_ItsUpdatedSuccessfully() {
        Expense expectedExpense = expenses.get(0);
        expectedExpense.setAmount(1200.0);
        expectedExpense.setDate(convertDate(stringDate));
        expectedExpense.setDescription("Gasto 1 Actualizado");
        expectedExpense.setCategory(categories.get(1));

        when(expenseRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(expenses.get(0)));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(categories.get(0)));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expectedExpense);

        ExpenseDto expenseDto = new ExpenseDto(1200.0, stringDate, "Gasto 1 Actualizado", categories.get(1).getId(), user.getId());

        Expense expenseResponse = expenseService.updateExpense(1L, expenseDto);

        assertEquals(expectedExpense, expenseResponse);
    }
}