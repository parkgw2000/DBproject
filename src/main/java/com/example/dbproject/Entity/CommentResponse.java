package com.example.dbproject.Entity;

import java.time.LocalDateTime;

public class CommentResponse {

    private String userNickname;
    private String commentText;
    private LocalDateTime commentDate;
    private Long commentgood;

    public CommentResponse(String userNickname, String commentText, LocalDateTime commentDate, Long commentgood) {
        this.userNickname = userNickname;
        this.commentText = commentText;
        this.commentDate = commentDate;
        this.commentgood = commentgood;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public Long getCommentgood() {
        return commentgood;
    }
    public void setCommentgood(Long commentgood) {
        this.commentgood = commentgood;
    }
}
