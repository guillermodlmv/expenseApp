package com.henry.expenseApp.service;

import com.henry.expenseApp.dto.ExpenseDto;
import com.henry.expenseApp.entity.Expense;
import com.henry.expenseApp.utils.entity.ExpenseFilter;

import java.util.List;

public interface ExpenseService {
    Expense addExpense(ExpenseDto expense);

    Expense updateExpense(Long expenseId, ExpenseDto expense);

    Expense getExpenseById(Long expenseId);

    List<Expense> getAllExpensesByUserId(Long userId, ExpenseFilter expenseFilter);

    void removeExpenseById(Long expenseId);
}
