package com.example.dbproject.Service;

import com.example.dbproject.Entity.Recipe;
import com.example.dbproject.Entity.RecipeListResponse;
import com.example.dbproject.Repository.SearchRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class SearchService {
    @Autowired
    private SearchRepository searchRepository;

    // 검색 로직
    public List<RecipeListResponse> searchRecipes(String query, String classification) {
        List<Recipe> recipes = null;

        // 검색 조건에 따라 적절한 레시피 리스트를 검색
        switch (classification) {
            case "name":
                recipes = searchRepository.findByRecipeNameContainingIgnoreCase(query);
                break;
            case "ingredient":
                recipes = searchRepository.findByMaterialContainingIgnoreCase(query);
                break;
            case "content":
                recipes = searchRepository.findByIntroContainingIgnoreCase(query);
                break;
            default:
                throw new IllegalArgumentException("Invalid classification: " + classification);
        }

        // 레시피 리스트가 비어있으면 빈 리스트 반환
        if (recipes == null || recipes.isEmpty()) {
            return List.of();
        }

        // RecipeListResponse 객체로 변환
        return recipes.stream()
                .map(recipe -> RecipeListResponse.builder() // RecipeListResponse 빌더 사용
                        .recipeNum(recipe.getRecipeNum())     // 레시피 번호
                        .recipeName(recipe.getRecipeName())   // 레시피 이름
                        .category(recipe.getCategory())       // 카테고리
                        .intro(recipe.getIntro())             // 소개
                        .mainImage(recipe.getMainImage())     // 메인 이미지
                        .userName(recipe.getUser().getNickname()) // 사용자 닉네임
                        .today(recipe.getToday())             // 오늘 날짜 (Recipe 객체에 today 필드가 있다면)
                        .build())
                .collect(Collectors.toList());
    }
}
