package com.henry.expenseApp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Double amount;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;

    @Column(name = "description")
    private String description;

    @ToString.Exclude // Evitar recursión
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonManagedReference
    private Category category;

    @ToString.Exclude // Evitar recursión
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    @JsonIgnore
    private User user;

    @Column(name = "is_active_expense", columnDefinition = "boolean default true")
    private boolean active;
}
