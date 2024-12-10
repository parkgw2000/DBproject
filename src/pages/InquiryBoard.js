import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './InquiryBoard.css';

const InquiryBoard = () => {
  const [inquiries, setInquiries] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchInquiries();
  }, []);

  const fetchInquiries = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/inquiry/list');
      console.log(response.data);
      setInquiries(response.data);
    } catch (error) {
      console.error('문의글 목록 조회 실패:', error);
    }
  };

  const handleCreateInquiry = () => {
    const user = localStorage.getItem('user');
    if (!user) {
      alert('로그인이 필요합니다.');
      return;
    }
    navigate('/inquiry/write');
  };

  return (
    <div className="inquiry-board-container">
      <h2>문의게시판</h2>
      
      <div className="button-container">
        <button 
          className="create-inquiry-button"
          onClick={handleCreateInquiry}
        >
          문의하기
        </button>
      </div>

      <div className="inquiry-list">
        <table>
          <thead>
            <tr>
              <th>번호</th>
              <th>제목</th>
              <th>작성자</th>
              <th>작성일</th>
              <th>답변상태</th>
            </tr>
          </thead>
          <tbody>
                {inquiries.map((inquiry, index) => (
            <tr 
            key={inquiry.inquirynum || index} // inquiryNum이 없을 경우 인덱스 사용
            onClick={() => navigate(`/inquiry/${inquiry.inquirynum}`)}
            >
            <td>{inquiry.inquirynum || '-'}</td>
            <td>{inquiry.title || '제목 없음'}</td>
            <td>{inquiry.user?.nickname || '알 수 없음'}</td>
            <td>
                {inquiry.createdate
                ? new Intl.DateTimeFormat('ko-KR').format(new Date(inquiry.createdate))
                : '날짜 없음'}
            </td>
            <td>{inquiry.reply ? '답변완료' : '대기중'}</td>
            </tr>
        ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default InquiryBoard;