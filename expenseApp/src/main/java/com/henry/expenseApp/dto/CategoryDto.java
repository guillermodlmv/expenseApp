package com.henry.expenseApp.dto;

import com.henry.expenseApp.utils.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    private String name;

    private String description;

    private Long user_id;

    public void validateParamsDto() {
        ValidationUtil.requireNonNullNotEmpty(name, "name");
        ValidationUtil.requireNonNullNotEmpty(description, "description");
        ValidationUtil.requireNonNullNotEmpty(user_id, "user_id");
    }
}
