package com.henry.expenseApp.service.impl;

import com.henry.expenseApp.dto.CategoryDto;
import com.henry.expenseApp.entity.Category;
import com.henry.expenseApp.entity.User;
import com.henry.expenseApp.utils.exception.utils.Messages;
import com.henry.expenseApp.repository.CategoryRepository;
import com.henry.expenseApp.repository.UserRepository;
import com.henry.expenseApp.service.CategoryService;
import com.henry.expenseApp.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Category> getAllUserCategory(Long userId) {
        return categoryRepository.findByUser_IdAndActiveTrue(userId);
    }

    @Override
    public List<Category> getAllUserCategoryByCategoryName(Long userId, String name) {
        return categoryRepository.findAllByUser_IdAndNameContainingAndActiveTrue(userId, name);
    }

    @Override
    public List<Category> getAllUserCategoryByCategoryDescription(Long userId, String description) {
        return categoryRepository.findAllByUser_IdAndDescriptionContainingAndActiveTrue(userId, description);
    }

    @Override
    public List<Category> getAllUserCategoryByCategoryNameAndDescription(Long userId, String name, String description) {
        return categoryRepository
                .findAllByUser_IdAndDescriptionContainingAndNameContainingAndActiveTrue(userId, name, description);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Messages.CATEGORY_NOT_FOUND));
    }

    @Override
    public Category createCategory(CategoryDto category) {
        category.validateParamsDto();
        User user = userRepository.findById(category.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException(Messages.USER_NOT_FOUND));

        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setDescription(category.getDescription());
        newCategory.setUser(user);
        newCategory.setExpenses(new ArrayList<>());
        newCategory.setActive(true);
        return categoryRepository.save(newCategory);
    }

    @Transactional
    @Override
    public Category updateCategory(Long categoryId, CategoryDto category) {
        Category categoryDb = getCategoryById(categoryId);
        String name = Utils.returnValueOrAlternativeIfNull(category.getName(), categoryDb.getName());
        String description = Utils.returnValueOrAlternativeIfNull(category.getDescription(), categoryDb.getDescription());

        categoryDb.setName(name);
        categoryDb.setDescription(description);
        categoryRepository.update(categoryId, name, description);
        return categoryDb;
    }

    @Transactional
    @Override
    public void removeCategoryById(Long categoryId) {
        categoryRepository.deleteCategory(categoryId);
    }
}
