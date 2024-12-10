import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';  // useNavigate 추가
import axios from 'axios';
import "./SearchBar.css";

const SearchBar = ({ setSearchResults }) => {
  const [searchQuery, setSearchQuery] = useState('');
  const [searchClassification, setSearchClassification] = useState('name');
  const navigate = useNavigate();  // navigate 훅 추가

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      if (searchQuery) {
        handleSearchChange();
      } else {
        setSearchResults([]);
      }
    }, 500);

    return () => clearTimeout(timeoutId);
  }, [searchQuery]);

  const handleSearchChange = async () => {
    if (searchQuery) {
      try {
        const response = await axios.get(`http://localhost:8080/api/recipes/search`, {
          params: {
            query: searchQuery,
            classification: searchClassification,
          },
        });

        setSearchResults(response.data);
      } catch (error) {
        console.error('Error searching for recipes:', error);
      }
    }
  };

  const handleClassificationChange = (e) => {
    setSearchClassification(e.target.value);
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    handleSearchChange();
    // 검색 후 SearchPage로 이동
    navigate(`/search?query=${searchQuery}&classification=${searchClassification}`);
  };

  return (
    <form className="search-container" onSubmit={handleSearchSubmit}>
      <div className="search-wrapper">
        <select 
          className="search-classification" 
          value={searchClassification} 
          onChange={handleClassificationChange}
        >
          <option value="name">이름</option>
          <option value="ingredient">재료</option>
          <option value="content">내용</option>
        </select>
        <div className="search-input-wrapper">
          <input
            type="text"
            className="search-input"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="레시피를 검색해보세요"
          />
          <button type="submit" className="search-button">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <circle cx="11" cy="11" r="8"></circle>
              <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
            </svg>
          </button>
        </div>
      </div>
    </form>
  );
};

export default SearchBar;
