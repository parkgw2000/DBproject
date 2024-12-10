package com.example.dbproject.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_num") // 댓글 번호
    private Long commentnum;

    @ManyToOne
    @JoinColumn(name = "recipe_num", nullable = false) //레시피 번호
    private Recipe recipenum;

    @ManyToOne
    @JoinColumn(name = "user_num", nullable = false) // 유저 번호
    private User user;

    @Column(name = "commennt", nullable = false) // 댓글 내용
    private String commennt;

    @Column(nullable = false) // 추천수
    private Long good;

    @Column(name = "dates", columnDefinition = "TIMESTAMP")
    private LocalDateTime dates;

    // Getter and Setter methods

    public Long getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Long commentnum) {
        this.commentnum = commentnum;
    }

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

    public String getCommennt() {
        return commennt;
    }

    public void setCommennt(String commennt) {
        this.commennt = commennt;
    }

    public Long getGood() {
        return good;
    }

    public void setGood(Long good) {
        this.good = good;
    }

    public LocalDateTime getDates() {
        return dates;
    }

    public void setDates(LocalDateTime dates) {
        this.dates = dates;
    }
}
