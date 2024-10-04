package com.henry.expenseApp.service;

import com.henry.expenseApp.dto.CategoryDto;
import com.henry.expenseApp.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    List<Category> getAllUserCategory(Long userId);

    List<Category> getAllUserCategoryByCategoryName(Long userId, String name);

    List<Category> getAllUserCategoryByCategoryDescription(Long userId, String description);

    List<Category> getAllUserCategoryByCategoryNameAndDescription(Long userId, String name ,String description);

    Category getCategoryById(Long id);

    Category createCategory(CategoryDto category);

    Category updateCategory(Long categoryId, CategoryDto category);

    void removeCategoryById(Long categoryId);
}
