package com.example.dbproject.Controller;

import com.example.dbproject.Entity.User;
import com.example.dbproject.Service.HistoryService;
import com.example.dbproject.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;
    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        log.info("회원가입 요청 데이터: 이메일={}, 닉네임={}, 이름={}, 전화번호={}",
                user.getUser_email(), user.getNickname(), user.getUsername(), user.getUser_phone());

        // 이메일 중복 체크
        if (userService.findByUserEmail(user.getUser_email()) != null) {
            log.warn("회원가입 실패: 이미 존재하는 이메일 - {}", user.getUser_email());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다.");
        }
        // 사용자 저장
        userService.saveUser(user);
        log.info("회원가입 성공: 이메일={}, 닉네임={}", user.getUser_email(), user.getNickname());
        return ResponseEntity.ok("회원가입 성공");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest, HttpSession session, HttpServletResponse response) {
        log.info("로그인 요청 데이터: 이메일={}", loginRequest.getUser_email());

        // 이메일로 사용자 조회
        User user = userService.findByUserEmail(loginRequest.getUser_email());
        if (user != null && loginRequest.getUser_pw().equals(user.getUser_pw())) {
            // 세션에 사용자 정보 저장
            session.setAttribute("email", user.getUser_email());
            session.setAttribute("nickname", user.getNickname());
            session.setAttribute("usernum", user.getUser_num());
            session.setMaxInactiveInterval(60 * 60);  // 세션에 1시간 저장

            log.info("로그인 성공: 세션 데이터 - 이메일={}, 닉네임={}, 유저번호={}",
                    session.getAttribute("email"), session.getAttribute("nickname"), session.getAttribute("usernum"));

            // 성공 응답
            return ResponseEntity.ok("로그인 성공");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 틀렸습니다.");
    }


    @PostMapping("/user/getUserInfo")
    public ResponseEntity<?> getUserInfo(@RequestBody Map<String, String> user) {
        String email = user.get("user_email");

        // 이메일로 유저 조회
        User foundUser = userService.findByUserEmail(email);

        if (foundUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("nickname", foundUser.getNickname());
            response.put("user_num", foundUser.getUser_num());
            log.info("유저 확인: 세션 데이터 - 이메일={}, 닉네임={}, 유저번호={}", email, foundUser.getNickname(), foundUser.getUser_num());
            return ResponseEntity.ok(response); // 유저 정보 반환
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저를 찾을 수 없습니다.");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // 세션 무효화
        session.invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }
}
