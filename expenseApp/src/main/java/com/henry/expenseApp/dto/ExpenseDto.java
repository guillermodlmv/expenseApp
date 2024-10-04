package com.henry.expenseApp.dto;

import com.henry.expenseApp.utils.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseDto {

    private Double amount;

    private String date;

    private String description;

    private Long categoryId;

    private Long userId;

    public void validateParamsDto() {
        ValidationUtil.requireNonNullNotEmpty(amount, "amount");
        ValidationUtil.requireNonNullNotEmpty(date, "date");
        ValidationUtil.requireNonNullNotEmpty(description, "description");
        ValidationUtil.requireNonNullNotEmpty(categoryId, "categoryId");
        ValidationUtil.requireNonNullNotEmpty(userId, "userId");
    }
}
