package com.henry.expenseApp.controller;

import com.henry.expenseApp.dto.CategoryDto;
import com.henry.expenseApp.entity.Category;
import com.henry.expenseApp.entity.User;
import com.henry.expenseApp.service.CategoryService;
import com.henry.expenseApp.utils.exception.utils.Messages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.henry.expenseApp.facade.CategoryFacade;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryFacade categoryFacade;

    @MockBean
    private CategoryService categoryService;

    @Test
    void findAllCategories_Success() throws Exception {

        // Crea algunas categorías asociadas al usuario
        Category category1 = new Category(1L, "Groceries", "Monthly groceries", new User(), new ArrayList<>(), true);
        Category category2 = new Category(2L, "Entertainment", "Movies and games", new User(), new ArrayList<>(), true);

        // Añade las categorías al usuario
        List<Category> categories = Arrays.asList(category1, category2);

        // Configura el mock para devolver estas categorías cuando se llame al método del facade
        Mockito.when(categoryFacade.getAllUserCategory(eq(1L), isNull(), isNull()))
                .thenReturn(categories);

        // Ejecuta la solicitud GET y verifica el resultado
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/category/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("Groceries")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].name", is("Entertainment")))
                .andExpect(jsonPath("$.message", is(Messages.SUCCESS)));
    }

    @Test
    void findAllCategories_EmptyList() throws Exception {
        when(categoryFacade.getAllUserCategory(anyLong(), anyString(), anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/category/user/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(Messages.USER_WITHOUT_CATEGORIES)));
    }

    @Test
    void getCategoryById_Success() throws Exception {
        // Crear el objeto de categoría
        Category category = new Category(1L,
                "Groceries",
                "Monthly groceries",
                new User(1L, "guille", "test@test.com", true, new ArrayList<>(), new ArrayList<>()),
                new ArrayList<>(),
                true);

        // Simular el comportamiento del servicio
        when(categoryService.getCategoryById(anyLong())).thenReturn(category);

        // Agregar depuración para ver si el servicio se llama correctamente
        System.out.println("Category returned by service: " + categoryService.getCategoryById(1L));

        // Ejecutar la solicitud y verificar los resultados
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("Groceries")))
                .andExpect(jsonPath("$.message", is(Messages.SUCCESS)));
    }

    @Test
    void getCategoryById_NotFound() throws Exception {
        when(categoryService.getCategoryById(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/category/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(Messages.CATEGORY_NOT_FOUND)));
    }

    @Test
    void createCategory_Success() throws Exception {
        CategoryDto categoryDto = new CategoryDto("Groceries", "Monthly groceries", 1L);
        Category category = new Category(1L, "Groceries", "Monthly groceries", new User(), new ArrayList<>(), true);

        when(categoryService.createCategory(Mockito.any(CategoryDto.class))).thenReturn(category);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Groceries\", \"description\": \"Monthly groceries\", \"userId\": 1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("Groceries")))
                .andExpect(jsonPath("$.message", is(Messages.SUCCESS)));
    }

    @Test
    void updateCategory_Success() throws Exception {
        CategoryDto categoryDto = new CategoryDto("Groceries", "Updated description", 1L);
        Category updatedCategory = new Category(1L, "Groceries", "Updated description", new User(), new ArrayList<>(), true);

        when(categoryService.updateCategory(Mockito.anyLong(), Mockito.any(CategoryDto.class))).thenReturn(updatedCategory);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Groceries\", \"description\": \"Updated description\", \"userId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.description", is("Updated description")))
                .andExpect(jsonPath("$.message", is(Messages.SUCCESS)));
    }

    @Test
    void deleteCategory_Success() throws Exception {
        Mockito.doNothing().when(categoryService).removeCategoryById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(Messages.SUCCESS)));
    }
}
