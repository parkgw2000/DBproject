package com.example.dbproject.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "types")
public class Types {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "type_name", nullable = false, unique = true, length = 100)
    private String typeName;

    // Getters and Setters
}