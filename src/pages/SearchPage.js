import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import RecipeCard from '../components/RecipeCard'; 
import SearchBar from '../components/SearchBar'; 
import ViewedRecipes from '../components/ViewedRecipes';
import './SearchPage.css'; 

const SearchPage = () => {
  const [searchResults, setSearchResults] = useState([]); // 검색 결과
  const location = useLocation(); // 현재 URL 정보를 가져옴
  const queryParams = new URLSearchParams(location.search); // URL에서 쿼리 파라미터 추출
  const searchQuery = queryParams.get('query'); // 쿼리 파라미터에서 'query'를 가져옴
  const searchClassification = queryParams.get('classification'); // 'classification' 가져옴

  // 한글 매핑
  const getClassificationLabel = (classification) => {
    switch (classification) {
      case 'name':
        return '이름';
      case 'ingredient':
        return '재료';
      case 'content':
        return '내용';
      default:
        return classification; 
    }
  };

  useEffect(() => {
    if (searchQuery) {
      // URL에서 가져온 searchQuery와 searchClassification으로 검색 API 호출
      handleSearchChange();
    }
  }, [searchQuery, searchClassification]);

  const handleSearchChange = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/recipes/search`, {
        params: {
          query: searchQuery,
          classification: searchClassification,
        },
      });
      setSearchResults(response.data); // 응답 데이터 상태에 저장
    } catch (error) {
      console.error('Error fetching search results:', error);
      setSearchResults([]); // 에러 발생 시 빈 배열로 처리
    }
  };

  return (
    <div className="search-page-container">
      {/* 서치바 */}
      <div className="search-bar-container">
        <SearchBar setSearchResults={setSearchResults} />
      </div>

      <div className="main-content-container">
        {/* 사이드바 (오늘 조회한 레시피) */}
        <div className="sidebar">
          <ViewedRecipes />
        </div>

        {/* 검색 결과 */} 
        <div className="search-results-container">
        <h2>{getClassificationLabel(searchClassification)} 검색결과 (검색어: {searchQuery})</h2>
          <div className="search-results">
            {searchResults.length > 0 ? (
              searchResults.map((post) => (
                <RecipeCard key={post.recipeNum} userNickname={post.userName} recipe={post} />
              ))
            ) : (
            /* 검색 안될때 */
              <p>해당하는 레시피가 없습니다</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default SearchPage;
