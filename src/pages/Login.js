import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./Login.css";

function Login() {
  const [user_email, setUserEmail] = useState("");
  const [user_pw, setUserPw] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/api/login", {
        user_email, 
        user_pw,   
      });

      if (response.status === 200) {
        // 로그인 성공
        console.log("로그인 성공", response.data);
        localStorage.setItem("user", user_email);
        navigate("/Allboard"); // 메인 페이지로 이동
        window.location.reload();
      }
    } catch (err) {
      if (err.response && err.response.status === 401) {
        setError("이메일 또는 비밀번호가 틀렸습니다.");
      } else {
        setError("로그인 중 오류가 발생했습니다.");
      }
    }
  };

  return (
    <div className="login-container">
      <form className="login-box" onSubmit={handleLogin}>
        <h1 className="login-title">로그인</h1>
        {error && <p className="error-message">{error}</p>}
        <input
          type="email"
          name="user_email" 
          placeholder="이메일"
          value={user_email}
          onChange={(e) => setUserEmail(e.target.value)}
          className="login-input"
          required
        />
        <input
          type="password"
          name="user_pw"
          placeholder="비밀번호"
          value={user_pw}
          onChange={(e) => setUserPw(e.target.value)}
          className="login-input"
          required
        />
        <button type="submit" className="login-button">로그인</button>
      </form>
    </div>
  );
}

export default Login;
