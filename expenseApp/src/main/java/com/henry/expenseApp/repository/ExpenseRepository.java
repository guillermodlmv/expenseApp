package com.henry.expenseApp.repository;

import com.henry.expenseApp.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> , JpaSpecificationExecutor<Expense> {
    Optional<Expense> findByIdAndActiveTrue(Long id);

    @Modifying
    @Query("UPDATE Expense e SET e.active = false WHERE e.id = :expenseId")
    void deleteExpense(@Param("expenseId") Long expenseId);

}
