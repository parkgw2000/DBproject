package com.example.dbproject.Service;

import com.example.dbproject.Entity.User;
import com.example.dbproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 이메일로 사용자 찾기
    public User findByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail)
                .orElse(null);  // 값이 없으면 null 반환
    }


    // 닉네임으로 사용자 조회
//    public User findUserByNickname(String nickname) {
//        Optional<User> userOptional = userRepository.findByNickname(nickname);
//        return userOptional.orElse(null);  // 값이 없으면 null 반환
//    }
    
    // 사용자 저장
    public void saveUser(User user) {
        userRepository.save(user);  // 사용자 정보 저장
    }
}