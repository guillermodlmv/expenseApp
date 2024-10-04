package com.henry.expenseApp.repository;

import com.henry.expenseApp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser_IdAndActiveTrue(Long userId);

    List<Category> findAllByUser_IdAndDescriptionContainingAndActiveTrue(Long user_id, String description);

    List<Category> findAllByUser_IdAndNameContainingAndActiveTrue(Long user_id, String name);

    List<Category> findAllByUser_IdAndDescriptionContainingAndNameContainingAndActiveTrue(
            Long user_id,
            String name,
            String description
    );

    @Modifying
    @Query("UPDATE Category c SET c.active = false WHERE c.id = :categoryId")
    void deleteCategory(@Param("categoryId") Long categoryId);

    @Modifying
    @Query("UPDATE Category c SET c.name = :name, c.description = :description  WHERE c.id = :categoryId")
    void update(@Param("categoryId") Long categoryId, @Param("name") String name, @Param("description") String description);
}
