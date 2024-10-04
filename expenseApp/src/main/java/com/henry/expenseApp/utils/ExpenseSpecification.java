package com.henry.expenseApp.utils;

import com.henry.expenseApp.entity.Expense;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ExpenseSpecification {
    public static Specification<Expense> filterByCriteria(
            Long userId, Double minAmount, Double maxAmount,
            LocalDate initDate, LocalDate endDate,
            String description) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtrado por monto mínimo y máximo
            if (minAmount != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), minAmount));
            }
            if (maxAmount != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("amount"), maxAmount));
            }

            // Filtrado por rango de fechas
            if (initDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("date"), initDate));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("date"), endDate));
            }

            // Filtrado por descripción
            if (description != null && !description.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
            }

            // Filtrado por userId
            if (userId != null) {
                predicates.add(cb.equal(root.get("user").get("id"), userId)); // Asegúrate de que el campo se llama "sessionId"
            }

            // Filtrado por active
            predicates.add(cb.isTrue(root.get("active")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
