package com.henry.expenseApp.facade;

import com.henry.expenseApp.entity.User;

import java.util.List;

public interface UserFacade {
    List<User> getAllUsers(String email, String name);
}
