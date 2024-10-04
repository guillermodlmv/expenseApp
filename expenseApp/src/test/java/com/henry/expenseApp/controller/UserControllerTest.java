package com.henry.expenseApp.controller;

import com.henry.expenseApp.dto.UserDto;
import com.henry.expenseApp.entity.User;
import com.henry.expenseApp.facade.UserFacade;
import com.henry.expenseApp.service.UserService;
import com.henry.expenseApp.utils.entity.ApiResponseEntity;
import com.henry.expenseApp.utils.exception.NotFoundException;
import com.henry.expenseApp.utils.exception.utils.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserFacade userFacade;

    private User user;

    private UserDto userDto;

    private List<User> users;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("testuser@mail.com");

        userDto = new UserDto();
        userDto.setName("Test User");
        userDto.setEmail("testuser@mail.com");

        users = new ArrayList<>();
        users.add(user);
    }

    @Test
    @DisplayName("Debe obtener todos los usuarios")
    @Order(1)
    void should_findAllUsers() throws Exception {
        // Ajustar el stubbing para aceptar cualquier String como argumentos
        when(userFacade.getAllUsers(any(), any())).thenReturn(users);
        List<User> allUsers = userFacade.getAllUsers(null, null);
        ApiResponseEntity<List<User>> response = new ApiResponseEntity<>(
                allUsers,
                Messages.SUCCESS,
                HttpStatus.OK
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(users, response.getData());
        assertEquals(Messages.SUCCESS, response.getMessage());
    }


    @Test
    @DisplayName("Debe obtener un usuario por ID")
    @Order(2)
    void should_getUserById() throws Exception {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(user);

        ApiResponseEntity<User> response = userController.getById(userId);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(user, response.getData());
        assertEquals(Messages.SUCCESS, response.getMessage());
    }

    @Test
    @DisplayName("Debe retornar NotFoundException si el usuario no existe")
    @Order(3)
    void should_throwNotFoundException_when_userNotFound() throws Exception {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userController.getById(userId);
        });

        assertEquals(Messages.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    @DisplayName("Debe crear un nuevo usuario")
    @Order(4)
    void should_createUser() throws Exception {
        when(userService.createUser(any(UserDto.class))).thenReturn(user);

        ApiResponseEntity<User> response = userController.create(userDto);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals(user, response.getData());
        assertEquals(Messages.SUCCESS, response.getMessage());
    }

    @Test
    @DisplayName("Debe actualizar un usuario")
    @Order(5)
    void should_updateUser() throws Exception {
        Long userId = 1L;
        when(userService.updateUser(any(UserDto.class), eq(userId))).thenReturn(user);

        ApiResponseEntity<User> response = userController.update(userDto, userId);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(user, response.getData());
        assertEquals(Messages.SUCCESS, response.getMessage());
    }

    @Test
    @DisplayName("Debe eliminar un usuario")
    @Order(6)
    void should_deleteUser() throws Exception {
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        ApiResponseEntity<Void> response = userController.delete(userId);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(Messages.SUCCESS, response.getMessage());
        verify(userService, times(1)).deleteUser(userId);
    }
}