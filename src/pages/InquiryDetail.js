import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import './InquiryDetail.css';

const InquiryDetail = () => {
  const [inquiry, setInquiry] = useState(null);
  const [reply, setReply] = useState(''); // 답변 입력 필드 상태
  const { inquiryNum } = useParams();
  const navigate = useNavigate();

  // 문의글 상세 조회
  const fetchInquiryDetail = useCallback(async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/inquiry/${inquiryNum}`);
      setInquiry(response.data);
      setReply(response.data.reply || ''); // 기존 답변이 있으면 설정
    } catch (error) {
      console.error('문의글 상세 조회 실패:', error);
      navigate('/inquiry');
    }
  }, [inquiryNum, navigate]);

  useEffect(() => {
    fetchInquiryDetail();
  }, [fetchInquiryDetail]);

  // 답변 등록/수정 요청
  const handleReplySubmit = async () => {
    try {
      await axios.put(`http://localhost:8080/api/inquiry/${inquiryNum}/reply`, null, {
        params: { reply }, // URL 파라미터로 전달
      });
      alert('답변이 등록되었습니다.');
      fetchInquiryDetail(); // 상태 갱신
    } catch (error) {
      console.error('답변 등록 실패:', error);
      alert('답변 등록 중 오류가 발생했습니다.');
    }
  };

  if (!inquiry) {
    return <div>Loading...</div>;
  }

  return (
    <div className="inquiry-detail-container">
      <div className="inquiry-header">
        <h2>{inquiry.title}</h2>
        <div className="inquiry-info">
          <span>작성자: {inquiry.user.nickname}</span>
          <span>작성일: {new Date(inquiry.createdate).toLocaleDateString()}</span>
        </div>
      </div>

      <div className="inquiry-content">
        <h3>문의내용</h3>
        <p>{inquiry.content}</p>
      </div>

      <div className="inquiry-reply">
        <h3>답변</h3>
        {inquiry.reply ? (
          <p>{inquiry.reply}</p>
        ) : (
          <div>
            <textarea
              value={reply}
              onChange={(e) => setReply(e.target.value)}
              placeholder="답변을 입력하세요."
              rows="5"
            />
            <button onClick={handleReplySubmit}>답변 등록</button>
          </div>
        )}
      </div>

      <div className="button-group">
        <button onClick={() => navigate('/inquiry')}>목록으로</button>
      </div>
    </div>
  );
};

export default InquiryDetail;
