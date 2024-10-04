package com.henry.expenseApp.facade.impl;

import com.henry.expenseApp.entity.User;
import com.henry.expenseApp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFacadeImplTest {

    @InjectMocks
    private UserFacadeImpl userFacade;

    @Mock
    private UserService userService;

    private List<User> users;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Test User 1");
        user1.setEmail("test1@mail.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Test User 2");
        user2.setEmail("test2@mail.com");

        users.add(user1);
        users.add(user2);
    }

    @Test
    @DisplayName("Debe obtener todos los usuarios sin filtros")
    @Order(1)
    void should_getAllUsers() {
        // Configuración de stubbing
        when(userService.getAllUsers()).thenReturn(users);

        List<User> result = userFacade.getAllUsers(null, null);

        assertEquals(users, result);
        verify(userService, times(1)).getAllUsers(); // Verificar que se llamó al método correcto
    }

    @Test
    @DisplayName("Debe obtener usuarios por email")
    @Order(2)
    void should_getUsersByEmail() {
        String email = "test1@mail.com";
        when(userService.getAllUsersByEmail(email)).thenReturn(users);

        List<User> result = userFacade.getAllUsers(email, null);

        assertEquals(users, result);
        verify(userService, times(1)).getAllUsersByEmail(email); // Verificar que se llamó al método correcto
    }

    @Test
    @DisplayName("Debe obtener usuarios por nombre")
    @Order(3)
    void should_getUsersByName() {
        String name = "Test User 1";
        when(userService.getAllUsersByName(name)).thenReturn(users);

        List<User> result = userFacade.getAllUsers(null, name);

        assertEquals(users, result);
        verify(userService, times(1)).getAllUsersByName(name); // Verificar que se llamó al método correcto
    }

    @Test
    @DisplayName("Debe obtener usuarios por email y nombre")
    @Order(4)
    void should_getUsersByEmailAndName() {
        String email = "test1@mail.com";
        String name = "Test User 1";
        when(userService.getAllUsersByEmailAndName(email, name)).thenReturn(users);

        List<User> result = userFacade.getAllUsers(email, name);

        assertEquals(users, result);
        verify(userService, times(1)).getAllUsersByEmailAndName(email, name); // Verificar que se llamó al método correcto
    }
}