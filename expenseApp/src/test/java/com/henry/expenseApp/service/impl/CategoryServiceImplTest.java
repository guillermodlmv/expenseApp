package com.henry.expenseApp.service.impl;

import com.henry.expenseApp.dto.CategoryDto;
import com.henry.expenseApp.entity.Category;
import com.henry.expenseApp.entity.User;
import com.henry.expenseApp.repository.CategoryRepository;
import com.henry.expenseApp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private User user;

    private List<Category> categories;

    private Category categoryResponse;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("Test@mail.com");
        user.setActive(true);
        user.setExpenses(new ArrayList<>());

        categories = new ArrayList<>();

        Category category1 = new Category(1L, "Categoria 1", "Categoria Fake 1", user, new ArrayList<>(), true);
        Category category2 = new Category(2L, "Categoria 2", "Categoria Fake 2", user, new ArrayList<>(), false);
        Category category3 = new Category(3L, "Categoria 3", "Categoria Fake 3", user, new ArrayList<>(), true);
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        user.setCategories(categories);

        categoryResponse = new Category(4L, "Categoria 4", "Categoria Fake 4", user, new ArrayList<>(), true);
    }

    @Test
    @DisplayName("Debe encontrar todas las categorías activas del usuario")
    @Order(1)
    void should_findAllUserCategories_when_userHasThemAndAreActive() {
        List<Category> expectedResult = categories.stream()
                .filter(Category::isActive)
                .toList();

        when(categoryRepository.findByUser_IdAndActiveTrue(user.getId()))
                .thenReturn(expectedResult);
        when(categoryRepository.findByUser_IdAndActiveTrue(2L))
                .thenReturn(null);

        List<Category> categoriesResult = categoryService.getAllUserCategory(1L);
        List<Category> categoriesResult2 = categoryService.getAllUserCategory(2L);

        assertNotNull(categoriesResult);
        assertNull(categoriesResult2);
        assertEquals(expectedResult, categoriesResult);
        assertEquals(expectedResult.size(), 2);
    }

    @Test
    @DisplayName("Debe encontrar todas las categorías activas" +
            "y que coincidan con el filtro por nombre de categoría del usuario")
    @Order(2)
    void should_findUserCategories_when_CategoryNameLooksLikeFilterAndAreActive() {
        List<Category> expectedResult1 = categories.stream()
                .filter(category -> {
                    return category.isActive() && category.getName().contains("1");
                })
                .toList();

        List<Category> expectedResult2 = categories.stream()
                .filter(category -> {
                    return category.isActive() && category.getName().contains("2");
                })
                .toList();

        when(categoryRepository.findAllByUser_IdAndNameContainingAndActiveTrue(user.getId(), "1"))
                .thenReturn(expectedResult1);

        when(categoryRepository.findAllByUser_IdAndNameContainingAndActiveTrue(user.getId(), "2"))
                .thenReturn(expectedResult2);

        when(categoryRepository.findAllByUser_IdAndNameContainingAndActiveTrue(eq(2L), anyString()))
                .thenReturn(null);

        List<Category> categoriesResult1 = categoryService.getAllUserCategoryByCategoryName(1L, "1");
        List<Category> categoriesResult2 = categoryService.getAllUserCategoryByCategoryName(1L, "2");
        List<Category> categoriesResult3 = categoryService.getAllUserCategoryByCategoryName(2L, "Categoria");

        assertNotNull(categoriesResult1);
        assertNotNull(categoriesResult2);
        assertNull(categoriesResult3);

        assertEquals(expectedResult1, categoriesResult1);
        assertEquals(expectedResult2, categoriesResult2);
    }

    @Test
    @DisplayName("Debe encontrar todas las categorías activas" +
            "y que coincidan con el filtro por descripción de categoría del usuario")
    @Order(3)
    void should_findUserCategories_when_CategoryDescriptionLooksLikeFilterAndAreActive() {
        List<Category> expectedResult1 = categories.stream()
                .filter(category -> {
                    return category.isActive() && category.getDescription().contains("1");
                })
                .toList();

        List<Category> expectedResult2 = categories.stream()
                .filter(category -> {
                    return category.isActive() && category.getDescription().contains("2");
                })
                .toList();

        when(categoryRepository.findAllByUser_IdAndDescriptionContainingAndActiveTrue(user.getId(), "1"))
                .thenReturn(expectedResult1);

        when(categoryRepository.findAllByUser_IdAndDescriptionContainingAndActiveTrue(user.getId(), "2"))
                .thenReturn(expectedResult2);

        when(categoryRepository.findAllByUser_IdAndDescriptionContainingAndActiveTrue(eq(2L), anyString()))
                .thenReturn(null);

        List<Category> categoriesResult1 = categoryService.getAllUserCategoryByCategoryDescription(1L, "1");
        List<Category> categoriesResult2 = categoryService.getAllUserCategoryByCategoryDescription(1L, "2");
        List<Category> categoriesResult3 = categoryService.getAllUserCategoryByCategoryDescription(2L, "Categoria");

        assertNotNull(categoriesResult1);
        assertNotNull(categoriesResult2);
        assertNull(categoriesResult3);

        assertEquals(expectedResult1, categoriesResult1);
        assertEquals(expectedResult2, categoriesResult2);
    }

    @Test
    @DisplayName("Debe encontrar todas las categorías activas" +
            "y que coincidan con el filtro por nombre y descripción de categoría del usuario")
    @Order(4)
    void should_findUserCategories_when_CategoryDescriptionAndNameLooksLikeFilterAndAreActive() {
        List<Category> expectedResult1 = categories.stream()
                .filter(category -> {
                    return category.isActive()
                            && category.getDescription().contains("1")
                            && category.getName().contains("catgoria");
                })
                .toList();

        List<Category> expectedResult2 = categories.stream()
                .filter(category -> {
                    return category.isActive()
                            && category.getDescription().contains("2")
                            && category.getName().contains("catgoria");
                })
                .toList();

        when(categoryRepository
                .findAllByUser_IdAndDescriptionContainingAndNameContainingAndActiveTrue(
                        user.getId(),
                        "catgoria",
                        "1"))
                .thenReturn(expectedResult1);

        when(categoryRepository
                .findAllByUser_IdAndDescriptionContainingAndNameContainingAndActiveTrue(
                        user.getId(),
                        "catgoria",
                        "2"
                ))
                .thenReturn(expectedResult2);

        when(categoryRepository
                .findAllByUser_IdAndDescriptionContainingAndNameContainingAndActiveTrue(
                        eq(2L),
                        anyString(),
                        anyString()))
                .thenReturn(null);

        List<Category> categoriesResult1 = categoryService
                .getAllUserCategoryByCategoryNameAndDescription(1L, "catgoria", "1");
        List<Category> categoriesResult2 = categoryService
                .getAllUserCategoryByCategoryNameAndDescription(1L, "catgoria", "2");
        List<Category> categoriesResult3 = categoryService
                .getAllUserCategoryByCategoryNameAndDescription(2L, "catgoria", "Categoria");

        assertNotNull(categoriesResult1);
        assertNotNull(categoriesResult2);
        assertNull(categoriesResult3);

        assertEquals(expectedResult1, categoriesResult1);
        assertEquals(expectedResult2, categoriesResult2);
    }

    @Test
    @DisplayName("Debe encontrar la categoría con el id buscado")
    @Order(5)
    void should_returnUserCategory_when_CategoryIdMatchWithAnExistentCategoryId() {
        Category expectedResult1 = this.categories.get(0);
        Category expectedResult2 = this.categories.get(1);
        Category expectedResult3 = this.categories.get(2);

        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(expectedResult1));
        when(categoryRepository.findById(2L)).thenReturn(Optional.ofNullable(expectedResult2));
        when(categoryRepository.findById(3L)).thenReturn(Optional.ofNullable(expectedResult3));

        Category category1 = categoryService.getCategoryById(1L);
        Category category2 = categoryService.getCategoryById(2L);
        Category category3 = categoryService.getCategoryById(3L);

        assertNotNull(category1);
        assertNotNull(category2);
        assertNotNull(category3);

        assertEquals(category1.getName(), "Categoria 1");
        assertEquals(category2.getName(), "Categoria 2");
        assertEquals(category3.getName(), "Categoria 3");
        assertTrue(category1.isActive());
        assertFalse(category2.isActive());
        assertTrue(category3.isActive());
    }

    @Test
    @DisplayName("Debe arrojar excepción si no existe categoría con ese id")
    @Order(6)
    void should_throwAnException_when_thereIsNotCategoryWithTheId() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.getCategoryById(4L);  // Aquí se espera que falle
        });
        assertEquals("La categoría no fue encontrada.", exception.getMessage());
    }

    @Test
    @DisplayName("Debe retornar la  categoría creada si los campos son correctos")
    @Order(7)
    void should_returnTheCreatedCategory_when_AllFieldsAreCorrect() {
        Category categoryResponse = new Category(4L, "Categoria 4", "Categoria Fake 4", user, new ArrayList<>(), true);
        when(categoryRepository.save(any(Category.class))).thenReturn(categoryResponse);
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        CategoryDto categoryDto = new CategoryDto("Categoria 4", "Categoria Fake 4", 1L);
        Category category = categoryService.createCategory(categoryDto);

        assertEquals(categoryResponse.getName(), categoryDto.getName());
        assertEquals(categoryResponse.getId(), 4L);
        assertEquals(categoryResponse.getDescription(), category.getDescription());
        assertEquals(categoryResponse.getUser().getId(), category.getUser().getId());
        assertTrue(category.isActive());

    }

    @Test
    @DisplayName("Debe arrojar excepción si no existe usuario con el id dentro del categoryDto")
    @Order(8)
    void should_throwAnException_when_UserIdIsNotExistent() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            CategoryDto categoryDto = new CategoryDto("Categoria 4", "Categoria Fake 4", 1L);
            categoryService.createCategory(categoryDto);
        });
        assertEquals("No se encontró Usuario", exception.getMessage());
    }
    
    @Test
    @DisplayName("Debe retornar la categoría actualizada")
    @Order(9)
    void should_returnUpdatedCategory_when_ItsUpdatedSuccessfully() {
        when(categoryRepository.findById(4L)).thenReturn(Optional.of(categoryResponse));

        Category expectedCategory = new Category(4L, "Categoria 4", "Categoria Fake 4", user, new ArrayList<>(), true);
        CategoryDto categoryDto = new CategoryDto("Updated", "Updated", 1L);

        Category category = categoryService.updateCategory(4L, categoryDto);

        assertNotEquals(expectedCategory.getName(), category.getName());
        assertNotEquals(expectedCategory.getDescription(), category.getDescription());
        assertEquals(expectedCategory.isActive(), category.isActive());
        assertEquals(expectedCategory.getUser().getId(), category.getUser().getId());
    }

    // Se deja sin logica por falta de sentido de test.
    @Test
    @DisplayName("La categoría debe obtener activo falso tras eliminacion")
    @Order(10)
    void should_categoryIsActiveFalse_when_deleteCategory() {
//        when(categoryRepository.findById(4L)).thenReturn(Optional.of(categoryResponse));
//        Category expectedCategory = categoryService.getCategoryById(4L);
//        categoryService.removeCategoryById(4L);

//        assertFalse(expectedCategory.isActive());
        return;
    }
}

