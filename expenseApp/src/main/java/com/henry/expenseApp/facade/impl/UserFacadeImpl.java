package com.henry.expenseApp.facade.impl;

import com.henry.expenseApp.entity.User;
import com.henry.expenseApp.facade.UserFacade;
import com.henry.expenseApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserFacadeImpl implements UserFacade {
    @Autowired
    UserService userService;


    @Override
    public List<User> getAllUsers(String email, String name) {
        List<User> users;
        if (name == null && email == null) {
            users = userService.getAllUsers();
        } else if (name == null) {
            users = userService.getAllUsersByEmail(email);
        } else if (email == null) {
            users = userService.getAllUsersByName(name);
        } else {
            users = userService.getAllUsersByEmailAndName(email, name);
        }
        return users;
    }
}
