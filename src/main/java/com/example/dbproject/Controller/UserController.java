package com.example.dbproject.Controller;

import com.example.dbproject.Entity.User;
import com.example.dbproject.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/login")//로그인 기능
    public String login(@RequestParam(name = "userEmail") String userEmail, @RequestParam(name = "user_pw") String user_pw,
                        HttpServletRequest request, HttpSession session, RedirectAttributes rd) {
        User user = userService.findByUserEmail(userEmail);
        if (user != null) {
            if (user_pw.equals(user.getUser_pw())) {
                session.setAttribute("email", userEmail);
                session.setAttribute("name", user.getNickname());
                return "redirect:/";
            }
        }
        request.setAttribute("msg", "이메일 혹은 비밀번호가 틀렸습니다.");
        request.setAttribute("url", "/login");
        return "alert";
    }

    @PostMapping(path = "/signup") //회원가입
    public String signup(@RequestParam(name = "user_email") String user_email,
                         @RequestParam(name = "user_pw") String user_pw,
                         @RequestParam(name = "nickname") String nickname,
                         @RequestParam(name = "user_name") String user_name,
                         @RequestParam(name = "user_phone") String user_phone,
                         Model model) {
        User user = new User();
        user.setUser_email(user_email);
        user.setUser_pw(user_pw);
        user.setNickname(nickname);
        user.setUser_name(user_name);
        user.setUser_phone(user_phone);

        userService.saveUser(user);
        model.addAttribute("name", user.getNickname());
        return "signup_done";
    }

    @GetMapping(path = "/logout")//로그아웃
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        return "redirect:/";
    }
}