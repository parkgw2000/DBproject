package com.example.dbproject.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_num")
    private Long recipeNum;

    @ManyToOne
    @JoinColumn(name = "user_num", nullable = false)
    private User user;


    @Column(name = "category_code", nullable = false,length = 20)
    private String category;

    @Column(name = "recipe_name", nullable = false, length = 100)
    private String recipeName;


    @Column(name = "material", nullable = false)
    private String material;

    @Column(name = "count", nullable = false)
    private String count;


    @Column(name = "intro", nullable = false)
    private String intro;

    @Column(name = "tool", nullable = false)
    private String tool;

    @Column(name = "likesd_num", columnDefinition = "NUMBER DEFAULT 0")
    private Integer likesdNum = 0;

    @Lob
    @Column(name = "main_image", nullable = false)
    private byte[] mainImage;

    @Column(name = "type")
    private String type = "recipe";

    @Column(name = "today", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime today;

    @PrePersist
    public void prePersist() {
        if (today == null) {
            today = LocalDateTime.now(); // 현재 시간 설정
        }
    }

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Cook> cookSteps = new ArrayList<>();

}
