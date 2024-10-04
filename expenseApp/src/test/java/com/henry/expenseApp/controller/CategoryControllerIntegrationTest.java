package com.henry.expenseApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henry.expenseApp.dto.CategoryDto;
import com.henry.expenseApp.entity.Category;
import com.henry.expenseApp.entity.User;
import com.henry.expenseApp.service.CategoryService;
import com.henry.expenseApp.utils.entity.ApiResponseEntity;
import com.henry.expenseApp.utils.exception.utils.Messages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;

    @Test
    void findAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void create() throws Exception {
        // GIVEN
        CategoryDto categoryDto = new CategoryDto("name", "description", 1L);
        User user = new User();
        user.setId(1L);
        Category category = new Category(1L, "name", "description", user, new ArrayList<>(), true);
        given(categoryService.createCategory(any(CategoryDto.class)))
                .willReturn(category);

        // WHEN + THEN
        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(
                        new ApiResponseEntity<>(
                        category,
                        Messages.SUCCESS,
                        HttpStatus.CREATED
                )))).andExpect(status().isCreated());


    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}