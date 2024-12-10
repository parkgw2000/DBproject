package com.example.dbproject.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "likes")
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_num")
    private Long likenum;

    @Column(name = "item_id", nullable = false)
    private String itemId;

    @ManyToOne
    @JoinColumn(name = "user_num", nullable = false)
    private User user;

    @Column(name = "item_type", nullable = false)
    private String itemType;

    @Column(name = "liked", nullable = false)
    private int liked;


    // Getters and Setters
}