package com.example.dbproject.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "recipe_name", nullable = false, length = 200)
    private String recipeName;

    @Lob
    @Column(name = "ingredients", nullable = false)
    private String ingredients;

    @Lob
    @Column(name = "instructions", nullable = false)
    private String instructions;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // Getters and Setters
}