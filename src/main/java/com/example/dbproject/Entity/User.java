package com.example.dbproject.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_num;

    @Column(name = "user_email", unique = true, nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String user_pw;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String user_name;

    @Column(nullable = false)
    private String user_phone;

    // Getters and Setters
    public Long getUser_num() {
        return user_num;
    }

    public void setUser_num(Long user_num) {
        this.user_num = user_num;
    }

    public String getUser_email() {
        return userEmail;
    }

    public void setUser_email(String user_email) {
        this.userEmail = user_email;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }
    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
}


