package com.henry.expenseApp.facade.impl;

import com.henry.expenseApp.entity.Category;
import com.henry.expenseApp.facade.CategoryFacade;
import com.henry.expenseApp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryFacadeImpl implements CategoryFacade {
    @Autowired
    CategoryService categoryService;

    @Override
    public List<Category> getAllUserCategory(Long userId, String name, String description) {
        List<Category> categories;
        if (name == null && description == null) {
            categories = categoryService.getAllUserCategory(userId);
        } else if (name == null) {
            categories = categoryService
                    .getAllUserCategoryByCategoryDescription(
                            userId,
                            description
                    );
        } else if (description == null) {
            categories = categoryService
                    .getAllUserCategoryByCategoryName(
                            userId,
                            name
                    );
        } else {
            categories = categoryService
                    .getAllUserCategoryByCategoryNameAndDescription(
                            userId,
                            description,
                            name
                    );
        }
        return categories;
    }
}
