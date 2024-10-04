package com.henry.expenseApp.utils.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExpenseFilter {
    private Double minAmount;

    private Double maxAmount;

    private String initDate;

    private String endDate;

    private String description;
}
