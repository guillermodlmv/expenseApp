package com.henry.expenseApp.service.impl;

import com.henry.expenseApp.dto.ExpenseDto;
import com.henry.expenseApp.entity.Category;
import com.henry.expenseApp.entity.Expense;
import com.henry.expenseApp.utils.ExpenseSpecification;
import com.henry.expenseApp.utils.Utils;
import com.henry.expenseApp.utils.entity.ExpenseFilter;
import com.henry.expenseApp.entity.User;
import com.henry.expenseApp.utils.exception.utils.Messages;
import com.henry.expenseApp.repository.CategoryRepository;
import com.henry.expenseApp.repository.ExpenseRepository;
import com.henry.expenseApp.repository.UserRepository;
import com.henry.expenseApp.service.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.henry.expenseApp.utils.Utils.convertDate;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @SneakyThrows
    @Override
    public Expense addExpense(ExpenseDto expense)  {
        expense.validateParamsDto();
        User user = userRepository.findById(expense.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(Messages.USER_NOT_FOUND));
        Category category = categoryRepository.findById(expense.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(Messages.CATEGORY_NOT_FOUND));
        if (!user.getId().equals(category.getUser().getId())) {
            throw new IllegalArgumentException(Messages.CATEGORY_NOT_BELONGS_USER);
        }
        Expense newExpense = new Expense();
        newExpense.setUser(user);
        newExpense.setCategory(category);
        newExpense.setDate(convertDate(expense.getDate()));
        newExpense.setAmount(expense.getAmount());
        newExpense.setDescription(expense.getDescription());
        newExpense.setActive(true);
        return expenseRepository.save(newExpense);
    }

    @Override
    public Expense updateExpense(Long expenseId, ExpenseDto expense) {
        Expense expenseDb = getExpenseById(expenseId);
        expenseDb.setAmount(Utils.returnValueOrAlternativeIfNull(expense.getAmount(), expenseDb.getAmount()));
        String date = (String) Utils.returnValueOrAlternativeIfNull(expense.getDate(), expenseDb.getDate());
        expenseDb.setDate(convertDate(date));
        expenseDb.setDescription(
                Utils.returnValueOrAlternativeIfNull(
                        expense.getDescription(),
                        expenseDb.getDescription()
                )
        );


        if (expense.getCategoryId() != null) {
            Category category = categoryRepository.findById(expense.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException(Messages.CATEGORY_NOT_FOUND));
            expenseDb.setCategory(category);
        }
        return expenseRepository.save(expenseDb);
    }

    @Override
    public Expense getExpenseById(Long expenseId) {
        return expenseRepository.findByIdAndActiveTrue(expenseId)
                .orElseThrow(() -> new EntityNotFoundException(Messages.EXPENSE_NOT_FOUND));
    }

    @Transactional
    @SneakyThrows
    @Override
    public List<Expense> getAllExpensesByUserId(Long userId, ExpenseFilter expenseFilter) {
        // Convertir las fechas de String a LocalDate si son provistas
        LocalDate initDate = null;
        LocalDate endDate = null;
        if (userId == null) {
            throw new IllegalArgumentException(Messages.USER_IS_NEEDED);
        }
        if (expenseFilter.getInitDate() != null) {
            initDate = convertDate(expenseFilter.getInitDate());
        }
        if (expenseFilter.getEndDate() != null) {
            endDate = convertDate(expenseFilter.getEndDate());
        }

        Specification<Expense> spec = ExpenseSpecification.filterByCriteria(
                userId, expenseFilter.getMinAmount(), expenseFilter.getMaxAmount(),
                initDate, endDate, expenseFilter.getDescription()
        );
        return expenseRepository.findAll(spec);
    }

    @Transactional
    @Override
    public void removeExpenseById(Long expenseId) {
        expenseRepository.deleteExpense(expenseId);
    }
}
