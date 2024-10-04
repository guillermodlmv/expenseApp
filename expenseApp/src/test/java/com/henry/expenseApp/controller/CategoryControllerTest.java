package com.henry.expenseApp.controller;

import com.henry.expenseApp.dto.CategoryDto;
import com.henry.expenseApp.entity.Category;
import com.henry.expenseApp.facade.CategoryFacade;
import com.henry.expenseApp.service.CategoryService;
import com.henry.expenseApp.utils.entity.ApiResponseEntity;
import com.henry.expenseApp.utils.exception.NotFoundException;
import com.henry.expenseApp.utils.exception.utils.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Mock
    private CategoryFacade categoryFacade;

    private Category category;

    private List<Category> categories;

    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Categoria Test", "Descripción Test", null, new ArrayList<>(), true);
        categories = new ArrayList<>();
        categories.add(category);

        categoryDto = new CategoryDto("Categoria Test", "Descripción Test", 1L);
    }

    @Test
    @DisplayName("Debe obtener todas las categorías del usuario")
    @Order(1)
    void should_findAllCategoriesByUserId() throws Exception {
        Long userId = 1L;
        when(categoryFacade.getAllUserCategory(userId, null, null)).thenReturn(categories);

        ApiResponseEntity<List<Category>> response = categoryController.findAll(userId, null, null);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(categories, response.getData());
    }


    @Test
    @DisplayName("Debe retornar NotFoundException si el usuario no tiene categorías")
    @Order(2)
    void should_throwNotFoundException_when_userHasNoCategories() throws Exception {

    }

    @Test
    @DisplayName("Debe obtener categoría por id")
    @Order(3)
    void should_getCategoryById() throws Exception {
        Long categoryId = 1L;
        when(categoryService.getCategoryById(categoryId)).thenReturn(category);

        ApiResponseEntity<Category> response = categoryController.getById(categoryId);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(category, response.getData());
    }

    @Test
    @DisplayName("Debe retornar NotFoundException si la categoría no existe")
    @Order(4)
    void should_throwNotFoundException_when_categoryNotFound() throws Exception {
        Long categoryId = 1L;
        when(categoryService.getCategoryById(categoryId)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            categoryController.getById(categoryId);
        });

        assertEquals(Messages.CATEGORY_NOT_FOUND, exception.getMessage());
    }

    @Test
    @DisplayName("Debe crear una nueva categoría")
    @Order(5)
    void should_createCategory() throws Exception {
        when(categoryService.createCategory(any(CategoryDto.class))).thenReturn(category);

        ApiResponseEntity<Category> response = categoryController.create(categoryDto);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals(category, response.getData());
    }

    @Test
    @DisplayName("Debe actualizar una categoría")
    @Order(6)
    void should_updateCategory() throws Exception {
        Long categoryId = 1L;
        when(categoryService.updateCategory(eq(categoryId), any(CategoryDto.class))).thenReturn(category);

        ApiResponseEntity<Category> response = categoryController.update(categoryDto, categoryId);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(category, response.getData());
    }

    @Test
    @DisplayName("Debe eliminar una categoría")
    @Order(7)
    void should_deleteCategory() throws Exception {
        Long categoryId = 1L;

        ApiResponseEntity<Void> response = categoryController.delete(categoryId);

        assertEquals(HttpStatus.OK, response.getStatus());
        verify(categoryService, times(1)).removeCategoryById(categoryId);
    }
}
