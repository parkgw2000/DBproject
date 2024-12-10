package com.example.dbproject.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_num")
    private Long usernum;

    @Column(name = "user_email", unique = true, nullable = false)
    private String userEmail;

    @Column(name = "user_pw", nullable = false)
    private String userpw;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "user_phone", nullable = true)
    private String userphone;

    // Getters and Setters
    public Long getUser_num() {
        return usernum;
    }

    public void setUser_num(Long usernum) {
        this.usernum = usernum;
    }

    public String getUser_email() {
        return userEmail;
    }

    public void setUser_email(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUser_pw() {
        return userpw;
    }

    public void setUser_pw(String userpw) {
        this.userpw = userpw;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUser_name(String username) {
        this.username = username;
    }

    public String getUser_phone() {
        return userphone;
    }

    public void setUser_phone(String userphone) {
        this.userphone = userphone;
    }
}
