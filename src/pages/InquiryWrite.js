import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './InquiryWrite.css';

const InquiryWrite = () => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
      alert('로그인이 필요합니다.');
      return;
    }

    try {
        await axios.post('http://localhost:8080/api/inquiry', {
          title,
          content,
          userNum: user.user_num, // API 요구사항에 맞는 데이터 전달
        });
        navigate('/inquiry'); // 성공 시 문의 게시판으로 이동
      } catch (error) {
        console.error('문의글 작성 실패:', error.response?.data || error.message);
        alert('문의글 작성 중 오류가 발생했습니다.');
      }
  };

  return (
    <div className="inquiry-write-container">
      <h2>문의하기</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>제목</label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>내용</label>
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
            rows="10"
          />
        </div>
        <div className="button-group">
          <button type="submit">등록</button>
          <button type="button" onClick={() => navigate('/inquiry')}>취소</button>
        </div>
      </form>
    </div>
  );
};

export default InquiryWrite;