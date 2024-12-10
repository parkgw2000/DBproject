package com.example.dbproject.Service;

import com.example.dbproject.Entity.User;
import com.example.dbproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 이메일 사용자 조회
    public User findByUserEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    // 유저 백엔드 삽입
    public void saveUser(User user) {
        userRepository.save(user);
    }
}