package com.example.dbproject.Repository;

import com.example.dbproject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 사용자 찾기
    Optional<User> findByUserEmail(String userEmail);


    // 사용자 이름으로 사용자 찾기
    //List<User> findByUserName(String userName);

    // 닉네임으로 사용자 찾기
    //Optional<User> findByNickname(String nickname);

    // 이메일이 존재하는지 확인
    //boolean existsByUserEmail(String userEmail);

    // 닉네임이 존재하는지 확인
    //boolean existsByNickname(String nickname);
}
