package com.henry.expenseApp.service;

import com.henry.expenseApp.dto.UserDto;
import com.henry.expenseApp.entity.User;

import java.util.List;


public interface UserService {
    List<User> getAllUsers();

    List<User> getAllUsersByEmail(String email);

    List<User> getAllUsersByName(String name);

    List<User> getAllUsersByEmailAndName(String email, String name);


    User getUserById(Long id);

    User createUser(UserDto user);

    User updateUser(UserDto user, Long id);

    void deleteUser(Long id);
}
