package com.example.dbproject.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inquiry")
@Data
@NoArgsConstructor
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_num")
    private Long inquirynum;

    @ManyToOne
    @JoinColumn(name = "user_num", nullable = false) // 외래 키와 매핑
    private User user;

    @Column(name = "title", nullable = false, length = 200)
    private String title;


    @Column(name = "reply")
    private String reply = ""; // 빈 문자열로 초기화


    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "dates", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdate;
}

