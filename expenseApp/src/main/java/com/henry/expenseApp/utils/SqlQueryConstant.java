package com.henry.expenseApp.utils;
@Deprecated
public class SqlQueryConstant {
    // Category
    public static final String GET_ALL_USER_CATEGORIES =
            "SELECT id, name, description, userId, active FROM category c WHERE (c.user_id = ?1 AND c.is_active_user = true)";
    public static final String GET_ALL_USER_CATEGORIES_FILTERED =
            "SELECT id, name, description, userId, active " +
                    "FROM category c " +
                    "WHERE (c.user_id = ?1 AND c.is_active_user = true)" +
                    "AND c.name LIKE %?1%" +
                    "AND c.description LIKE %?2%";
    public static final String GET_CATEGORY_BY_ID =
            "SELECT id, name, description, userId, active FROM category WHERE id = ?";
    public static final String CREATE_CATEGORY =
            "INSERT INTO category (name, description, userId) VALUES (?, ?, ?)";
    public static final String UPDATE_CATEGORY =
            "UPDATE category SET name = ?, description = ?, active = ? WHERE id = ?";
    public static final String DELETE_CATEGORY = "DELETE category WHERE id = ?";


    // Expense
    public static final String GET_ALL_USER_EXPENSES =
            "SELECT id, amount, date, description, categoryId, userId, active FROM expense WHERE (userId = ? AND active = true)";
    public static final String GET_EXPENSE_BY_ID =
            "SELECT id, amount, date, description, categoryId, userId, active FROM expense WHERE id = ?";
    public static final String GET_EXPENSE_BY_USER_ID =
            "SELECT id, amount, date, description, categoryId, userId, active FROM expense WHERE userId = ?";
    public static final String CREATE_EXPENSE =
            "INSERT INTO expense (amount, date, description, categoryId, userId) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_EXPENSE =
            "UPDATE expense SET amount = ?, date = ?, description = ?, categoryId = ?, userId = ?, active = ?  WHERE id = ?";
    public static final String DELETE_EXPENSE = "DELETE expense WHERE id = ?";

    // User
    public static final String GET_ALL_USERS = "SELECT id, name, email, active FROM users WHERE active = true";
    public static final String GET_USER_BY_ID = "SELECT id, name, email, active FROM users WHERE id = ?";
    public static final String CREATE_USER = "INSERT INTO users (name, email) VALUES (?, ?)";
    public static final String UPDATE_USER = "UPDATE users SET name = ?, email = ?, active = ? WHERE id = ?";
    public static final String GET_USER_BY_EMAIL = "SELECT id, name, email, active FROM users WHERE email = ?";
    public static final String GET_USER_BY_NAME = "SELECT id, name, email, active FROM users WHERE LOWER(name) = LOWER(?)";
    public static final String DELETE_USER = "DELETE users WHERE id = ?";

    // Table
    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE IF NOT EXISTS category (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(255)," +
            "description VARCHAR(255)," +
            "userId BIGINT," +
            "active boolean DEFAULT true)";

    public static final String CREATE_TABLE_EXPENSE = "CREATE TABLE IF NOT EXISTS expense (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
            "amount DOUBLE," +
            "date DATE," +
            "description VARCHAR(255)," +
            "categoryId BIGINT," +
            "userId BIGINT," +
            "active boolean DEFAULT true)";

    public static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(255)," +
            "email VARCHAR(255)," +
            "active boolean DEFAULT true)";


}
