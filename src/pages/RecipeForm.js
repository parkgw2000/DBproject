import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import "./RecipeForm.css";

const RecipeForm = () => {
  const [recipeName, setRecipeName] = useState('');
  const [material, setMaterial] = useState('');
  const [count, setCount] = useState('');
  const [intro, setIntro] = useState('');
  const [tool, setTool] = useState('');
  const [mainImage, setMainImage] = useState(null);
  const [category, setCategory] = useState('');
  const [steps, setSteps] = useState([
    { stepOrder: 1, image: null, description: '' },
  ]);

  const categories = [
    '족발보쌈', '찜탕', '돈까쓰회', '피자', '치킨', '중식', '한식', 
    '양식', '일식', '카페 디저트', '패스트푸드'
  ];

  const navigate = useNavigate(); // useNavigate 훅을 사용하여 페이지 이동

  const addStep = () => {
    if (steps.length < 10) {
      setSteps([...steps, { stepOrder: steps.length + 1, image: null, description: '' }]);
    } else {
      alert('최대 10개까지 조리 순서를 추가할 수 있습니다.');
    }
  };

  const handleStepChange = (index, event) => {
    const updatedSteps = steps.map((step, i) =>
      i === index ? { ...step, [event.target.name]: event.target.value } : step
    );
    setSteps(updatedSteps);
  };

  const handleStepImageChange = (index, event) => {
    const updatedSteps = [...steps];
    updatedSteps[index].image = event.target.files[0];
    setSteps(updatedSteps);
  };

  const handleMainImageChange = (event) => {
    setMainImage(event.target.files[0]);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = new FormData();

    const user = JSON.parse(localStorage.getItem("user"));
    const userNum = user ? user.user_num : null;  // user_num 값 추출

    // erd랑 맞춤
    formData.append('recipe_name', recipeName);
    formData.append('material', material);
    formData.append('count', count);
    formData.append('intro', intro);
    formData.append('tool', tool);
    formData.append('category_code', category);   
    formData.append('user_num', userNum);
    if (mainImage) formData.append('main_image', mainImage);  

    steps.forEach((step, index) => {
      formData.append(`steps[${index}].step_order`, step.stepOrder);   
      formData.append(`steps[${index}].details`, step.description);    
      if (step.image) {
        formData.append(`steps[${index}].image`, step.image);
      }
    });

    // FormData 로그 출력
    for (let pair of formData.entries()) {
      console.log(pair[0] + ": " + pair[1]);
    }

    try {
      const response = await axios.post('http://localhost:8080/api/recipes', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      // 성공 시 Allboard 페이지로 이동
      console.log('레시피 등록 성공:', response.data);
      navigate('/allboard'); // 등록 성공 후 Allboard 페이지로 이동
    } catch (error) {
      console.error('레시피 등록 오류:', error);
    }
  };

  return (
    <div className="recipe-form-container">
      <div className="recipe-form-header">
        <h2 className="recipe-form-title">새로운 레시피 등록</h2>
        <p className="recipe-form-subtitle">맛있는 요리의 시작, 레시피를 공유해주세요</p>
      </div>

      <form className="recipe-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label>레시피 이름</label>
          <input
            type="text"
            placeholder="요리 이름을 입력해주세요"
            value={recipeName}
            onChange={(e) => setRecipeName(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label>재료</label>
          <input
            type="text"
            placeholder="사용된 재료를 입력해주세요"
            value={material}
            onChange={(e) => setMaterial(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label>분량</label>
          <input
            type="text"
            placeholder="몇 인분인지 알려주세요"
            value={count}
            onChange={(e) => setCount(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label>요리 소개</label>
          <textarea
            placeholder="이 요리의 매력을 소개해주세요"
            value={intro}
            onChange={(e) => setIntro(e.target.value)}
            required
            rows="3"
          />
        </div>

        <div className="form-group">
          <label>요리 도구</label>
          <input
            type="text"
            placeholder="필요한 조리 도구를 알려주세요"
            value={tool}
            onChange={(e) => setTool(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label>대표 이미지</label>
          <div className="custom-file-input">
            <input
              type="file"
              onChange={handleMainImageChange}
              required
            />
            <div className="file-select">
              {mainImage ? mainImage.name : '대표 이미지를 선택해주세요'}
            </div>
            {mainImage && <img src={URL.createObjectURL(mainImage)} alt="대표 이미지 미리보기" className="image-preview" />}
          </div>
        </div>

        <div className="form-group">
          <label>카테고리</label>
          <select 
            value={category} 
            onChange={(e) => setCategory(e.target.value)} 
            required
          >
            <option value="">카테고리를 선택해주세요</option>
            {categories.map((cat) => (
              <option key={cat} value={cat}>{cat}</option>
            ))}
          </select>
        </div>

        <div className="steps-section">
          <h3>조리 순서</h3>
          {steps.map((step, index) => (
            <div key={index} className="step-item">
              <span className="step-number">Step {step.stepOrder}</span>

              <div className="form-group">
                <label>이미지</label>
                <div className="custom-file-input">
                  <input
                    type="file"
                    name="image"
                    onChange={(e) => handleStepImageChange(index, e)}
                  />
                  <div className="file-select">
                    {step.image ? step.image.name : '단계 이미지 선택'}
                  </div>
                  {step.image && <img src={URL.createObjectURL(step.image)} alt={`조리 순서 ${step.stepOrder}`} className="image-preview" />}
                </div>
              </div>

              <div className="form-group">
                <label>설명</label>
                <textarea
                  name="description"
                  placeholder={`Step ${step.stepOrder}의 조리 방법을 설명해주세요`}
                  value={step.description}
                  onChange={(e) => handleStepChange(index, e)}
                  required
                  rows="3"
                />
              </div>
            </div>
          ))}

          <button 
            type="button" 
            className="add-step-btn" 
            onClick={addStep}
            disabled={steps.length >= 10} // 10개 이상 안되게
          >
            + 조리 순서 추가
          </button>
        </div>

        <button 
          type="submit" 
          className="submit-btn"
        >
          레시피 등록
        </button>
      </form>
    </div>
  );
};

export default RecipeForm;
