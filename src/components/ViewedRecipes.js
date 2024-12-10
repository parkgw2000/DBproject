import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './ViewedRecipes.css';

const ViewedRecipes = () => {
  const [viewedRecipes, setViewedRecipes] = useState([]);
  const navigate = useNavigate(); // useNavigate를 사용하여 페이지 이동

  useEffect(() => {
    // 컴포넌트가 마운트될 때 오늘 조회한 레시피 데이터 받아오기
    const getViewedRecipes = async () => {
      try {
        const user = JSON.parse(localStorage.getItem('user'));  // user 정보를 가져오기
        if (user && user.user_num) {
          const response = await axios.get('http://localhost:8080/api/gethistory', {
            params: { usernum: user.user_num } // 파라미터로 usernum 전달
          });
          setViewedRecipes(response.data);  // 조회한 레시피 목록 상태에 저장
          console.log(response.data);
        } else {
          console.log('User not logged in');
        }
      } catch (error) {
        console.error('Error fetching viewed recipes:', error);
      }
    };

    getViewedRecipes();
  }, []); // 빈 배열로 첫 렌더링 시 한 번만 호출

  // 레시피 상세 페이지로 이동하는 함수
  const handleRecipeClick = (recipeId) => {
    navigate(`/recipe/${recipeId}`);  // 해당 레시피 번호에 맞는 상세 페이지로 이동
  };

  return (
    <div className="sidebar">
      <h3>오늘 열람한 레시피</h3>
      <div className="viewed-recipes">
        {viewedRecipes.length > 0 ? (
          viewedRecipes.map((recipe, index) => (
            <div key={index} className="history-item" onClick={() => handleRecipeClick(recipe.recipeNum)}>
              <img
                src={`data:image/jpeg;base64,${recipe.mainImage}`} // 메인 이미지 출력
                alt={recipe.recipeName}
                className="history-main-image"
              />
              <p>{recipe.recipeName}</p> {/* 레시피 이름 출력 */}
            </div>
          ))
        ) : (
          <p>오늘 조회한 레시피가 없습니다.</p>
        )}
      </div>
    </div>
  );
};

export default ViewedRecipes;
