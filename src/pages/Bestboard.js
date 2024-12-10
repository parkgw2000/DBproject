import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import RecipeCard from '../components/RecipeCard';
import SearchBar from '../components/SearchBar';
import ViewedRecipes from '../components/ViewedRecipes';
import './Best.css'; // 스타일링

const Bestboard = () => {
  const [posts, setPosts] = useState([]); // 전체 게시글
  const [searchResults, setSearchResults] = useState([]); // 검색 결과
  const [filteredPosts, setFilteredPosts] = useState([]); // 필터링된 게시글
  const [selectedCategory, setSelectedCategory] = useState('전체'); // 선택된 카테고리
  const navigate = useNavigate(); // Declare useNavigate

  // 카테고리 목록
  const categories = [
    '전체', '족발보쌈', '찜탕', '돈까쓰회', '피자', '치킨', '중식', '한식', 
    '양식', '일식', '카페 디저트', '패스트푸드'
  ];

  // 게시글 목록을 받아오는 함수
  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/best'); // 게시판 데이터 받아오기
        console.log("응답 데이터 타입:", typeof response.data); // 응답 데이터 타입 출력
        if (Array.isArray(response.data)) {
          setPosts(response.data); // response가 배열이면 setPosts
          setFilteredPosts(response.data); // 초기 상태에서 전체 게시글을 필터링된 목록으로 설정
          console.log("응답 데이터:", response.data);
        } else {
          console.log("빈 배열");
          setPosts([]); // 데이터가 배열이 아니면 빈 배열로 처리
          setFilteredPosts([]); // 데이터가 배열이 아니면 필터링된 배열도 빈 배열로 처리
        }
      } catch (error) {
        console.error('Error fetching posts:', error);
        setPosts([]); // 에러 발생 시 빈 배열로 처리
        setFilteredPosts([]); // 에러 발생 시 필터링된 배열도 빈 배열로 처리
      }
    };

    fetchPosts();
  }, []); // 빈 배열로 첫 렌더링 시 한 번만 호출

  useEffect(() => {
    // searchResults가 업데이트될 때마다 로그 출력
    console.log('Updated search results:', searchResults);
  }, [searchResults]); // searchResults가 변경될 때마다 실행

  const handleSearchResults = (results) => {
    if (Array.isArray(results)) {
      setSearchResults(results);
    } else {
      setSearchResults([]); // 결과가 배열이 아니면 빈 배열로 처리
    }
  };

  // 게시글 클릭 시 상세 페이지로 이동하는 함수
  const handlePostClick = (recipeNum) => {
    navigate(`/recipe/${recipeNum}`);
  };

  // 글 등록 버튼 클릭 시 글 작성 페이지로 이동하는 함수
  const handleCreatePost = () => {
    const user = localStorage.getItem('user'); // 로컬스토리지에서 user 정보 가져오기

    if (!user) {
      alert('로그인 후에 글을 작성할 수 있습니다.');
      return; // user 정보가 없다면 리턴
    }

    const userData = JSON.parse(user); // 로컬스토리지에서 받은 user 정보를 파싱

    if (!userData.user_num) {
      alert('유저 정보가 없습니다. 다시 로그인 해주세요.');
      return; // user_num이 없으면 리턴
    }

    navigate('/recipeform'); // user_num이 있으면 글 작성 페이지로 이동
  };

  // 카테고리 변경 시 필터링
  const handleCategoryChange = (category) => {
    setSelectedCategory(category);

    if (category === '전체') {
      // "전체" 선택 시 전체 게시글 보여줌
      setFilteredPosts(posts);
    } else {
      // 선택된 카테고리에 맞는 게시글만 필터링
      const filtered = posts.filter(post => post.category === category);
      setFilteredPosts(filtered);
    }
  };

  return (
    <div className="board-page-container">
      {/* 사이드바 (오늘 조회한 레시피) */}
      <ViewedRecipes />

      <div className="board-main-content">
        {/* 페이지 제목 */}
        <h1 className="board-title">베스트 레시피</h1>

        {/* 서치바 (검색 기능) */}
        <SearchBar setSearchResults={handleSearchResults} />

        {/* 카테고리 버튼 목록 */}
        <div className="category-buttons-container">
          {categories.map((category, index) => (
            <button
              key={index}
              className={`category-button ${selectedCategory === category ? 'active' : ''}`}
              onClick={() => handleCategoryChange(category)}
            >
              {category}
            </button>
          ))}
        </div>

        {/* 글 등록 버튼 */}
        <div className="create-post-button-container">
          <button className="create-post-button" onClick={handleCreatePost}>글 등록</button>
        </div>

        {/* 게시글 리스트 */}
        <div className="post-list">
          {/* 필터링된 게시글을 보여줌 */}
          {filteredPosts.length > 0 ? (
            filteredPosts.map((post) => (
              <RecipeCard 
                key={post.recipeNum}
                recipe={post} 
                onClick={() => handlePostClick(post.recipeNum)} 
                userNickname={post.userName}
              />
            ))
          ) : (
            <div>검색된 레시피가 없습니다.</div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Bestboard;
