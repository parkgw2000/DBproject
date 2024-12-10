import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./Signup.css";

function Signup() {
  const [formData, setFormData] = useState({
    user_email: "", 
    user_pw: "",
    nickname: "",
    username: "",
    userphone: "",
  });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSignup = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/api/signup", formData, {
        headers: {
          "Content-Type": "application/json",
        },
      });
      if (response.status === 200) {
        setSuccess(true);
        setTimeout(() => navigate("/login"), 2000); // 2초 후 로그인 페이지로 이동
      }
    } catch (error) {
      if (error.response && error.response.status === 409) {
        setError("이미 존재하는 이메일입니다.");
      } else {
        setError("회원가입에 실패했습니다. 다시 시도해주세요.");
      }
    }
  };

  return (
    <div className="signup-container">
      <form className="signup-box" onSubmit={handleSignup}>
        <h1 className="signup-title">회원가입</h1>
        {success && <p className="success-message">회원가입이 완료되었습니다!</p>}
        {error && <p className="error-message">{error}</p>}
        <input
          type="email"
          name="user_email" 
          placeholder="이메일"
          value={formData.user_email}
          onChange={handleInputChange}
          className="signup-input"
          required
        />
        <input
          type="password"
          name="user_pw" 
          placeholder="비밀번호"
          value={formData.user_pw}
          onChange={handleInputChange}
          className="signup-input"
          required
        />
        <input
          type="text"
          name="nickname" 
          placeholder="닉네임"
          value={formData.nickname}
          onChange={handleInputChange}
          className="signup-input"
          required
        />
        <input
          type="text"
          name="username" 
          placeholder="이름"
          value={formData.username}
          onChange={handleInputChange}
          className="signup-input"
          required
        />
        <input
          type="text"
          name="userphone" 
          placeholder="전화번호"
          value={formData.userphone}
          onChange={handleInputChange}
          className="signup-input"
          required
        />
        <button type="submit" className="signup-button">
          회원가입
        </button>
      </form>
    </div>
  );
}

export default Signup;
