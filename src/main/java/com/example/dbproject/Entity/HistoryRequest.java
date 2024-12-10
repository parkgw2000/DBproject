package com.example.dbproject.Entity;

import java.time.LocalDate;

public class HistoryRequest {

    private Long usernum;
    private Long recipenum;
    private LocalDate openday;  // 오픈일
    private LocalDate today;    // 오늘 날짜

    // Getters and Setters
    public Long getUsernum() {
        return usernum;
    }

    public void setUsernum(Long usernum) {
        this.usernum = usernum;
    }

    public Long getRecipenum() {
        return recipenum;
    }

    public void setRecipenum(Long recipenum) {
        this.recipenum = recipenum;
    }

    public LocalDate getOpenday() {
        return openday;
    }

    public void setOpenday(LocalDate openday) {
        this.openday = openday;
    }

    public LocalDate getToday() {
        return today;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }
}
