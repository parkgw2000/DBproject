import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import ViewedRecipes from '../components/ViewedRecipes'; 
import "./RecipeDetail.css";

const RecipeDetail = () => {
  const [recipe, setRecipe] = useState(null); // 레시피 데이터 상태
  const [likes, setLikes] = useState(0); // 추천수 상태
  const [isLiked, setIsLiked] = useState(false); // 레시피 추천수
  const [comments, setComments] = useState([]); // 댓글 데이터 상태
  const [commentText, setCommentText] = useState(""); // 댓글 입력 상태
  const { recipeId } = useParams(); // URL에서 recipeId를 받아옵니다.
  const [comLiked, setcomLiked] = useState(false); // 댓글 추천수
  const [comlikes, setlikes] = useState(0); // 댓글 추천수 상태

  // 날짜 포맷팅 함수
  const formatDate = (date) => {
    const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' };
    const formattedDate = new Date(date).toLocaleString('ko-KR', options); // 'ko-KR'은 한국 날짜 형식
    return formattedDate;
  };

  // 레시피 상세 정보를 가져오는 함수
  useEffect(() => {
    const fetchRecipeDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/recipes/${recipeId}`);
        setRecipe(response.data); // 서버에서 받은 레시피 데이터를 상태에 저장
        setLikes(response.data.likedNum); // 좋아요 수 초기화

        // 댓글 가져오기
        fetchComments(recipeId); // 댓글 가져오기 함수 호출
        pathhistory(recipeId);
        // 로그인한 유저의 좋아요 상태 확인
        const user = JSON.parse(localStorage.getItem('user'));
        if (user) {
          const likeStatusResponse = await axios.get(`http://localhost:8080/api/likes/status`, {
            params: {
                userNum: user.user_num,
                itemId: recipeId,
                itemType: 'recipe'
                    }
                  });
                  setIsLiked(likeStatusResponse.data);
                }
      } catch (error) {
        console.error("레시피 상세 정보를 가져오는 데 실패했습니다.", error);
      }
    };

    fetchRecipeDetails();
  }, [recipeId]); // recipeId가 변경될 때마다 해당 레시피 데이터를 요청

  // 댓글 목록을 가져오는 함수
  const fetchComments = async (recipeId) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/${recipeId}/getcomments`);
      setComments(response.data); // 댓글 리스트 상태 업데이트
    } catch (error) {
      console.error("댓글을 가져오는 데 실패했습니다.", error);
    }
  };

  // 추천하기 버튼 클릭 시 추천수 증가
  const handleLike = async () => {
    try {
      const user = JSON.parse(localStorage.getItem('user'));
      if (!user) {
        alert('로그인이 필요합니다.');
        return;
      }

      const params = new URLSearchParams();
      params.append('userNum', user.user_num);
      params.append('itemId', recipeId);
      params.append('itemType', 'recipe');

      const response = await axios.post('http://localhost:8080/api/likes/toggle', params);

      // 좋아요 상태 및 좋아요 수 업데이트
      if (response.data) {
        setLikes(prevLikes => prevLikes + 1);
        setIsLiked(true);
      } else {
        setLikes(prevLikes => prevLikes - 1);
        setIsLiked(false);
      }

      // 업데이트된 좋아요 수를 레시피 상태에도 반영
      setRecipe(prevRecipe => ({
        ...prevRecipe,
        likesdNum: response.data ? prevRecipe.likesdNum + 1 : prevRecipe.likesdNum - 1,
      }));
    } catch (error) {
      console.error("좋아요 처리 실패:", error);
    }
  };

  // 댓글 입력값 변경
  const handleCommentChange = (e) => {
    setCommentText(e.target.value);
  };

  // 댓글 제출 처리
  const handleCommentSubmit = async (e) => {
    e.preventDefault();

    if (!commentText) return; // 댓글이 비어있는 경우 제출하지 않음

    try {
      const user = JSON.parse(localStorage.getItem('user'));
      if (!user) {
        alert('로그인이 필요합니다.');
        return;
      }

      console.log('댓글 전송 데이터:', {
        userNum: user.user_num,
        recipeId: recipeId,
        commentText: commentText
      });

      // 댓글 전송
      const response = await axios.post(`http://localhost:8080/api/${recipeId}/comments`, {
        userNum: user.user_num,
        commentText: commentText,
      });

      // 댓글 목록 리로드
      fetchComments(recipeId); // 댓글 목록 리로드 함수 호출
      setCommentText(""); // 댓글 입력창 초기화
    } catch (error) {
      console.error("댓글 제출 실패:", error);
    }
  };

  // 댓글 추천 처리
  const handleCommentLike = async (commentId) => {
    try {
      const response = await axios.post(`http://localhost:8080/api/comments/${commentId}/like`);
      // 추천 수 업데이트
      const updatedComments = comments.map((comment) => 
        comment.commentId === commentId ? { ...comment, commentgood: response.data.commentgood } : comment
      );
      setComments(updatedComments); // 댓글 리스트 상태 업데이트
    } catch (error) {
      console.error("댓글 추천 실패:", error);
    }
  };

  // 히스토리 업데이트 함수
  const pathhistory = async (recipeId) => {
    const date = new Date().toISOString(); // 현재 날짜
    try {
      const user = JSON.parse(localStorage.getItem('user')); // 사용자 정보 가져오기
      if (user) {
        const response = await axios.post(`http://localhost:8080/api/addhistory`, {
          usernum: user.user_num, // 사용자 번호
          recipenum: recipeId, // 레시피 번호
          openday: date // 현재 날짜
        });
  
        console.log("히스토리 업데이트 :", user.user_name, recipeId, date);
      } else {
        console.log("로그인 정보가 없습니다.");
      }
    } catch (error) {
      console.log("히스토리 업데이트 오류:", error);
    }
  };

  // 로딩 상태 처리
  if (!recipe) {
    return <p>Loading...</p>; // 데이터가 로드될 때까지 로딩 메시지 표시
  }

  return (
    <div className="recipe-detail-container">
      <ViewedRecipes />

      <div className="recipe-header">
        <h2 className="recipe-title">{recipe ? recipe.recipeName : '레시피 로딩 중'}</h2>
        <img
          src={`data:image/jpeg;base64,${recipe.mainImage}`}
          alt="Main Recipe"
          className="recipe-main-image"
        />
      </div>
      <div className="like-section">
        <button className="like-button" onClick={handleLike}>
          {isLiked ? '❤️' : '🤍'} 좋아요 {recipe.likesdNum}
        </button>
      </div>

      <div className="recipe-info-container">
        <div className="recipe-info">
          <h3>요리 소개</h3>
          <p>{recipe.intro}</p>
        </div>

        <div className="recipe-info">
          <h3>재료</h3>
          <p>{recipe.material}</p>
        </div>

        <div className="recipe-info">
          <h3>분량</h3>
          <p>{recipe.count}인분</p>
        </div>

        <div className="recipe-info">
          <h3>요리 도구</h3>
          <p>{recipe.tool}</p>
        </div>

        <div className="recipe-info">
          <h3>카테고리</h3>
          <p>{recipe.category}</p>
        </div>
      </div>

      <div className="steps-container">
        <h3>조리 순서</h3>
        {recipe.cookSteps && recipe.cookSteps.map((step) => (
          <div key={step.cookNum} className="step-item">
            <span className="step-number">Step {step.index}</span>
            {step.filename && (
              <img 
                src={`data:image/jpeg;base64,${step.filename}`} 
                alt={`Step ${step.index}`} 
                className="step-image" 
              />
            )}
            <p>{step.details}</p>
          </div>
        ))}
      </div>

      {/* 댓글 입력창 */}
      <div className="comment-input">
        <textarea
          value={commentText}
          onChange={handleCommentChange}
          placeholder="내용을 입력하세요"
        />
        <button onClick={handleCommentSubmit} className="submit-comment-button">댓글 작성</button>
      </div>

      {/* 댓글 목록 */}
      <div className="comments-section">
        <h3>댓글</h3>
        {comments.length > 0 ? (
          comments.map((comment) => (
            <div key={comment.commentId} className="comment-item">
              <div className="comment-header">
                <strong>{comment.userNickname}</strong>
                <span className="comment-date">{formatDate(comment.commentDate)}</span>
              </div>
              <p>{comment.commentText}</p>
              <div className="comment-actions">
                <button onClick={() => handleCommentLike(comment.commentId)} className="like-button-comment">
                  ❤️
                </button>
                <span className="comment-like-num">{comment.commentgood}</span>
              </div>
            </div>
          ))
        ) : (
          <p>댓글이 없습니다.</p>
        )}
      </div>
    </div>
  );
};

export default RecipeDetail;
