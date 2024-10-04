package com.henry.expenseApp.service.impl;

import com.henry.expenseApp.dto.UserDto;
import com.henry.expenseApp.entity.User;
import com.henry.expenseApp.utils.exception.utils.Messages;
import com.henry.expenseApp.repository.UserRepository;
import com.henry.expenseApp.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByActiveTrue();
    }

    @Override
    public List<User> getAllUsersByEmail(String email) {
        return userRepository.findByEmailContainingAndActiveTrue(email);
    }

    @Override
    public List<User> getAllUsersByName(String name) {
        return userRepository.findByNameContainingAndActiveTrue(name);
    }

    @Override
    public List<User> getAllUsersByEmailAndName(String email, String name) {
        return userRepository.findByEmailContainingAndNameContainingAndActiveTrue(email, name);
    }

    @SneakyThrows
    @Override
    public User getUserById(Long id)  {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.USERS_NOT_FOUND));
        Hibernate.initialize(user.getCategories());
        Hibernate.initialize(user.getExpenses());
        return user;
    }

    @Override
    public User createUser(UserDto user) {
        user.validateParamsDto();
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setCategories(new ArrayList<>());
        newUser.setExpenses(new ArrayList<>());
        newUser.setActive(true);
        return userRepository.save(newUser);
    }

    @Transactional
    @Override
    public User updateUser(UserDto user, Long id) {
        User userDb = getUserById(id);
        String email = user.getEmail() != null ? user.getEmail() : userDb.getEmail();
        String name = user.getName() != null ? user.getName() : userDb.getName();
        userDb.setEmail(email);
        userDb.setName(name);
        userRepository.update(id, name, email);
        return userDb;
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }
}
