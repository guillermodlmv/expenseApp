package com.henry.expenseApp.repository;

import com.henry.expenseApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmailContainingAndActiveTrue(String email);

    List<User> findByNameContainingAndActiveTrue(String name);

    List<User> findByEmailContainingAndNameContainingAndActiveTrue(String email, String name);

    List<User> findAllByActiveTrue();

    @Modifying
    @Query("UPDATE User u SET u.active = false WHERE u.id = :userId")
    void deleteUser(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User u SET u.name = :name, u.email = :email  WHERE u.id = :userId")
    void update(@Param("userId") Long userId, @Param("name") String name, @Param("email") String email);

}
