package com.henry.expenseApp.controller;

import com.henry.expenseApp.dto.CategoryDto;
import com.henry.expenseApp.utils.exception.NotFoundException;
import com.henry.expenseApp.utils.entity.ApiResponseEntity;
import com.henry.expenseApp.entity.Category;
import com.henry.expenseApp.utils.exception.utils.Messages;
import com.henry.expenseApp.facade.CategoryFacade;
import com.henry.expenseApp.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    CategoryFacade categoryFacade;

    @Autowired
    CategoryService categoryService;

    @SneakyThrows
    @Operation(
            summary = "Obtener las categorías del usuario por id",
            description="Debe enviar el id de usuario",
            tags= {"Category"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="400", description = Messages.USER_WITHOUT_CATEGORIES),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    @GetMapping("/user/{userId}")
    public ApiResponseEntity<List<Category>> findAll(
            @PathVariable Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description
    ) {
        List<Category> allCategories = categoryFacade.getAllUserCategory(userId, name, description);
        if (allCategories.isEmpty()) {
            throw new NotFoundException(Messages.USER_WITHOUT_CATEGORIES);
        }
        return new ApiResponseEntity<>(
                allCategories,
                Messages.SUCCESS,
                HttpStatus.OK
        );
    }

    @SneakyThrows
    @Operation(
            summary = "Obtener categoría por id",
            description="Debe enviar el id de categoría",
            tags= {"Category"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="400", description = Messages.CATEGORY_NOT_FOUND),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    @GetMapping("/{id}")
    public ApiResponseEntity<Category> getById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            throw new NotFoundException(Messages.CATEGORY_NOT_FOUND);
        }
        return new ApiResponseEntity<>(
                category,
                Messages.SUCCESS,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Crear categoría",
            description="Debe enviar name, description y user_id",
            tags= {"Category"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    @PostMapping
    public ApiResponseEntity<Category> create(@RequestBody CategoryDto category) {
        Category categoryCreated = categoryService.createCategory(category);
        return new ApiResponseEntity<>(
                categoryCreated,
                Messages.SUCCESS,
                HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "Actualiza categoría",
            description="Puede enviar name, description, debe enviar id de categoría",
            tags= {"Category"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="400", description = Messages.CATEGORY_NOT_FOUND),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    @PatchMapping("/{id}")
    public ApiResponseEntity<Category> update(@RequestBody CategoryDto category, @PathVariable Long id) {
        Category categoryUpdated =  categoryService.updateCategory(id, category);
        return new ApiResponseEntity<>(
                categoryUpdated,
                Messages.SUCCESS,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Actualiza categoría",
            description="Debes enviar id de categoría",
            tags= {"Category"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="400", description = Messages.CATEGORY_NOT_FOUND),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    @DeleteMapping("/{id}")
    public ApiResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.removeCategoryById(id);
        return new ApiResponseEntity<>(
                Messages.SUCCESS,
                HttpStatus.OK
        );
    }
}
