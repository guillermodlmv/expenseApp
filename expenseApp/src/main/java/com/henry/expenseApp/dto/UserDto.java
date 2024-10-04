package com.henry.expenseApp.dto;

import com.henry.expenseApp.utils.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String name;

    private String email;

    public void validateParamsDto() {
        ValidationUtil.requireNonNullNotEmpty(name, "name");
        ValidationUtil.requireNonNullNotEmpty(email, "email");
    }

}
