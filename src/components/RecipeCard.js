import React from 'react';
import { Heart, Clock, Tag } from 'lucide-react';
import { useNavigate } from 'react-router-dom'; 
import './RecipeCard.css';

const RecipeCard = ({ recipe, userNickname }) => {
  const navigate = useNavigate(); 

  // 레시피 디테일 페이지로 이동하는 함수
  const handleViewDetails = () => {
    navigate(`/recipe/${recipe.recipeNum}`); // 레시피 번호를 URL에 전달
  };

  // 날짜 포맷팅 함수 (yy:mm:dd 형식으로)
  const formatDate = (date) => {
    const d = new Date(date); // ISO 8601 날짜 문자열을 Date 객체로 변환
    const year = d.getFullYear().toString().slice(2, 4); // 두 자릿수 년도
    const month = (d.getMonth() + 1).toString().padStart(2, '0'); // 월 (01, 02, ...)
    const day = d.getDate().toString().padStart(2, '0'); // 일 (01, 02, ...)
    return `${year}.${month}.${day}.`;
  };

  return (
    <div className="recipe-card">
      <div className="recipe-card-image">
        {recipe.mainImage ? (
          <>
            <img 
              src={`data:image/jpeg;base64,${recipe.mainImage}`} 
              alt={recipe.recipeName}
              className="recipe-card-image-img"
            />
            <button className="recipe-card-like-button">
              <Heart 
                className="recipe-card-like-icon" 
                size={24} 
                strokeWidth={2}
              />
            </button>
          </>
        ) : (
          <div className="recipe-card-no-image">
            <span>No Image</span>
          </div>
        )}
      </div>
      
      <div className="recipe-card-body">
        <h3 className="recipe-card-title">
          {recipe.recipeName}
        </h3>
        
        <p className="recipe-card-intro">
          {recipe.intro}
        </p>
        
        <div className="recipe-card-meta">
          <div className="recipe-card-category">
            <Tag className="icon" />
            <span>{recipe.category}</span>
          </div>
          
          <div className="recipe-card-time">
            <Clock className="icon" />
            <span>{recipe.today ? formatDate(recipe.today) : 'N/A'}</span> {/* 날짜 포맷팅 */}
          </div>
        </div>
        
        <div className="recipe-card-footer">
          <div className="recipe-card-author">
            {userNickname}
          </div>
          
          {/* handleViewDetails 실행 */}
          <button 
            onClick={handleViewDetails} 
            className="recipe-card-view-button"
          >
            자세히 보기
          </button>
        </div>
      </div>
    </div>
  );
};

export default RecipeCard;
