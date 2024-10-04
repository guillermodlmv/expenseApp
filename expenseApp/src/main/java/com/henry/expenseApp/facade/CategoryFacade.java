package com.henry.expenseApp.facade;

import com.henry.expenseApp.entity.Category;

import java.util.List;

public interface CategoryFacade {
    List<Category> getAllUserCategory(Long userId, String name, String description);
}
