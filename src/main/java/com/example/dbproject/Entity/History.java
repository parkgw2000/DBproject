package com.example.dbproject.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_num")
    private Long historyNum; // 기본 키로 설정, 자동 증가

    @ManyToOne
    @JoinColumn(name = "recipe_num", nullable = false) //레시피 번호
    private Recipe recipenum;

    @ManyToOne
    @JoinColumn(name = "user_num", nullable = false) // 유저 번호
    private User user;

    @Column(name = "openday", nullable = false)
    private LocalDate openday; // 오픈일 (날짜)

    @Column(name = "today", nullable = false)
    private LocalDate today; // 오늘 날짜 (날짜)

    // Getter and Setter for historyNum
    public Long getHistoryNum() {
        return historyNum;
    }

    public void setHistoryNum(Long historyNum) {
        this.historyNum = historyNum;
    }

    // Getter and Setter for user
    public Recipe getRecipenum() {
        return recipenum;
    }

    public void setRecipenum(Recipe recipenum) {
        this.recipenum = recipenum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter and Setter for openday
    public LocalDate getOpenday() {
        return openday;
    }

    public void setOpenday(LocalDate openday) {
        this.openday = openday;
    }

    // Getter and Setter for today
    public LocalDate getToday() {
        return today;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }
}
