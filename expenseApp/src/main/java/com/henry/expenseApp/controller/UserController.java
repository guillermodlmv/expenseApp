package com.henry.expenseApp.controller;

import com.henry.expenseApp.dto.UserDto;
import com.henry.expenseApp.utils.exception.NotFoundException;
import com.henry.expenseApp.utils.entity.ApiResponseEntity;
import com.henry.expenseApp.entity.User;
import com.henry.expenseApp.utils.exception.utils.Messages;
import com.henry.expenseApp.facade.UserFacade;
import com.henry.expenseApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserFacade userFacade;

    @SneakyThrows
    @Operation(
            summary = "Obtener todos los usuarios con o sin filtro",
            description="Puedes filtrar por name y email",
            tags= {"User"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="400", description = Messages.USERS_NOT_FOUND),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    @GetMapping
    public ApiResponseEntity<List<User>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ) {
        List<User> users = userFacade.getAllUsers(email, name);
        if (users.isEmpty()) {
            throw new NotFoundException(Messages.USERS_NOT_FOUND);
        }
        return new ApiResponseEntity<>(
                users,
                Messages.SUCCESS,
                HttpStatus.OK
        );
    }

    @SneakyThrows
    @Operation(
            summary = "Obtener usuario por Id",
            description="Debes enviar userId",
            tags= {"User"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="400", description = Messages.USER_NOT_FOUND),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    @GetMapping("/{id}")
    public ApiResponseEntity<User> getById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new NotFoundException(Messages.USER_NOT_FOUND);
        }
        return new ApiResponseEntity<>(
                user,
                Messages.SUCCESS,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Crear un usuario",
            description="Debes enviar name y email",
            tags= {"User"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="400", description = Messages.USER_NOT_FOUND),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    @PostMapping
    public ApiResponseEntity<User> create(@RequestBody UserDto user) {
            User createdUser = userService.createUser(user);
            return new ApiResponseEntity<>(
                    createdUser,
                    Messages.SUCCESS,
                    HttpStatus.CREATED
            );
    }

    @Operation(
            summary = "Actualizar un usuario",
            description="Puedes enviar name y email y debes enviar userId",
            tags= {"User"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="400", description = Messages.USER_NOT_FOUND),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    @PatchMapping("/{id}")
    public ApiResponseEntity<User> update(@RequestBody UserDto user, @PathVariable Long id) {
            User createdUser = userService.updateUser(user, id);
            return new ApiResponseEntity<>(
                    createdUser,
                    Messages.SUCCESS,
                    HttpStatus.OK
            );
    }

    @Operation(
            summary = "Eliminar un usuario",
            description="Debes enviar userId",
            tags= {"User"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description = Messages.SUCCESS),
            @ApiResponse(responseCode="400", description = Messages.USER_NOT_FOUND),
            @ApiResponse(responseCode="404", description = Messages.BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_SERVER_ERROR)
    })
    @DeleteMapping("/{id}")
    public ApiResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ApiResponseEntity<>(
                Messages.SUCCESS,
                HttpStatus.OK
        );
    }
}
