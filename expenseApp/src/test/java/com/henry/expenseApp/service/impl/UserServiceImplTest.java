package com.henry.expenseApp.service.impl;

import com.henry.expenseApp.dto.UserDto;
import com.henry.expenseApp.entity.User;
import com.henry.expenseApp.repository.UserRepository;
import com.henry.expenseApp.utils.exception.utils.Messages;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@mail.com");
        user.setActive(true);
        user.setCategories(new ArrayList<>());
        user.setExpenses(new ArrayList<>());
    }

    @Test
    @DisplayName("Debe encontrar todos los usuarios activos")
    @Order(1)
    void should_findAllActiveUsers() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user);

        when(userRepository.findAllByActiveTrue()).thenReturn(expectedUsers);

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(expectedUsers, users);
    }

    @Test
    @DisplayName("Debe encontrar usuarios por email")
    @Order(2)
    void should_findUsersByEmail() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user);

        when(userRepository.findByEmailContainingAndActiveTrue("test@mail.com")).thenReturn(expectedUsers);

        List<User> users = userService.getAllUsersByEmail("test@mail.com");

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(expectedUsers, users);
    }

    @Test
    @DisplayName("Debe encontrar usuarios por nombre")
    @Order(3)
    void should_findUsersByName() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user);

        when(userRepository.findByNameContainingAndActiveTrue("Test User")).thenReturn(expectedUsers);

        List<User> users = userService.getAllUsersByName("Test User");

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(expectedUsers, users);
    }

    @Test
    @DisplayName("Debe encontrar usuarios por email y nombre")
    @Order(4)
    void should_findUsersByEmailAndName() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user);

        when(userRepository.findByEmailContainingAndNameContainingAndActiveTrue("test@mail.com", "Test User"))
                .thenReturn(expectedUsers);

        List<User> users = userService.getAllUsersByEmailAndName("test@mail.com", "Test User");

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(expectedUsers, users);
    }

    @Test
    @DisplayName("Debe retornar usuario por id")
    @Order(5)
    void should_returnUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getName(), foundUser.getName());
    }

    @Test
    @DisplayName("Debe arrojar EntityNotFoundException si el usuario no existe")
    @Order(6)
    void should_throwException_when_userNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserById(1L);
        });

        assertEquals(Messages.USERS_NOT_FOUND, exception.getMessage());
    }

    @Test
    @DisplayName("Debe crear un nuevo usuario")
    @Order(7)
    void should_createNewUser() {
        UserDto userDto = new UserDto("New User", "newuser@mail.com");
        User newUser = new User();
        newUser.setName(userDto.getName());
        newUser.setEmail(userDto.getEmail());
        newUser.setCategories(new ArrayList<>());
        newUser.setExpenses(new ArrayList<>());
        newUser.setActive(true);

        // Cast to User to ensure type conformity
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User createdUser = userService.createUser(userDto);

        assertNotNull(createdUser);
        assertEquals(userDto.getName(), createdUser.getName());
        assertEquals(userDto.getEmail(), createdUser.getEmail());
    }

    @Test
    @DisplayName("Debe actualizar un usuario existente")
    @Order(8)
    void should_updateExistingUser() {
        UserDto userDto = new UserDto("Updated User", "updated@mail.com");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User updatedUser = userService.updateUser(userDto, 1L);

        assertNotNull(updatedUser);
        assertEquals("Updated User", updatedUser.getName());
        assertEquals("updated@mail.com", updatedUser.getEmail());
    }

}