package com.example.dbproject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cook")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cook_num")  // recipe_num이 아닌 cook_num을 ID로 사용
    private Long cookNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_num")  // recipe_num은 외래키로 사용
    private Recipe recipe;

    @Lob
    @Column(name = "filename", nullable = false)
    private byte[] filename;

    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "step_order", nullable = false)
    private int index;
}
