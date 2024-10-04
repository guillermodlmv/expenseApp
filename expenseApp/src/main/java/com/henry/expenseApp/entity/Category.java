package com.henry.expenseApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ToString.Exclude // Evitar recursión
    private User user;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    @JsonBackReference
    @ToString.Exclude // Evitar recursión
    private List<Expense> expenses;

    @Column(name = "is_active_category", columnDefinition = "boolean default true")
    private boolean active;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", expenses=" + expenses +
                ", active=" + active +
                '}';
    }
}
