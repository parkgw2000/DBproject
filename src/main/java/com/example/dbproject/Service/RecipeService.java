package com.example.dbproject.Service;

import com.example.dbproject.Entity.*;
import com.example.dbproject.Repository.CookRepository;
import com.example.dbproject.Repository.LikesRepository;
import com.example.dbproject.Repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final CookRepository cookRepository;
    private final LikesRepository likesRepository;

    public Recipe createRecipe(Recipe recipe, List<Cook> cookSteps) {
        try {
            // 1. 레시피 저장
            Recipe savedRecipe = recipeRepository.save(recipe);
            log.info("레시피 기본 정보 저장 완료 - ID : {}", savedRecipe.getRecipeNum());

            // 2. 조리 과정 저장
            if (cookSteps != null && !cookSteps.isEmpty()) {
                for (Cook cook : cookSteps) {
                    cook.setRecipe(savedRecipe);  // Cook -> Recipe 연관관계 설정
                    Cook savedCook = cookRepository.save(cook);
                    log.info("조리과정 저장 완료 - 순서: {}, 설명: {}",
                            cook.getIndex(), cook.getDetails());
                }
                savedRecipe.getCookSteps().addAll(cookSteps); // Recipe -> Cook 연관관계 설정
            }

            return recipeRepository.save(savedRecipe); // 변경된 레시피 다시 저장
        } catch (Exception e) {
            log.error("레시피 저장 중 오류 발생", e);
            e.printStackTrace(); // 상세 에러 확인을 위해 추가
            throw new RuntimeException("레시피 저장 실패: " + e.getMessage(), e);
        }
    }

    // 전체 게시판 조회
    public List<RecipeListResponse> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        for (Recipe recipe : recipes) {
            log.info("Recipe ID: {}, Image null?: {}",
                    recipe.getRecipeNum(),
                    recipe.getMainImage() == null);
        }
        return recipes.stream()
                .map(recipe -> new RecipeListResponse(
                        recipe.getRecipeNum(),
                        recipe.getRecipeName(),
                        recipe.getCategory(),
                        recipe.getIntro(),
                        recipe.getMainImage(),
                        recipe.getUser().getNickname(),
                        recipe.getToday()
                ))
                .collect(Collectors.toList());
    }

    // getBestRecipes의 중복된 매핑 로직을 제거하고 getRecipesByType으로 재사용
    public List<RecipeListResponse> getBestRecipes() {
        return getRecipesByType("best");
    }

    // getRecipesByType 메서드를 사용하여 "best"와 "honor" 처리
    public List<RecipeListResponse> getRecipesByType(String type) {
        List<Recipe> recipes = switch (type) {
            case "best" -> recipeRepository.findBestRecipes();
            case "honor" -> recipeRepository.findHonorRecipes();
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        };

        return recipes.stream()
                .map(recipe -> new RecipeListResponse(
                        recipe.getRecipeNum(),
                        recipe.getRecipeName(),
                        recipe.getCategory(),
                        recipe.getIntro(),
                        recipe.getMainImage(),
                        recipe.getUser().getNickname(),
                        recipe.getToday()
                ))
                .collect(Collectors.toList());
    }

    // 레시피 조회
    public Recipe getRecipe(Long recipeNum) {
        return recipeRepository.findById(recipeNum)
                .orElseThrow(() -> new EntityNotFoundException("not found."));
    }

    // 레시피 상세 조회
    public RecipeDetailResponse getRecipeWithDetails(Long recipeNum) {
        Recipe recipe = recipeRepository.findById(recipeNum)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        List<Cook> cookSteps = cookRepository.findByRecipe_RecipeNumOrderByIndexAsc(recipeNum);

        return RecipeDetailResponse.builder()
                .recipeNum(recipe.getRecipeNum())
                .recipeName(recipe.getRecipeName())
                .category(recipe.getCategory())
                .material(recipe.getMaterial())
                .count(recipe.getCount())
                .intro(recipe.getIntro())
                .tool(recipe.getTool())
                .mainImage(recipe.getMainImage())
                .likesdNum(recipe.getLikesdNum())
                .userName(recipe.getUser().getNickname())
                .createdDate(recipe.getToday())
                .cookSteps(cookSteps.stream()
                        .map(cook -> CookStepResponse.builder()
                                .cookNum(cook.getCookNum())
                                .index(cook.getIndex())
                                .details(cook.getDetails())
                                .filename(cook.getFilename())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    // 레시피 좋아요 업데이트
    @Transactional
    public void updateRecipeLikes() {
        List<Recipe> recipes = recipeRepository.findAll();

        for (Recipe recipe : recipes) {
            String itemId = String.valueOf(recipe.getRecipeNum());
            Long likesCount = likesRepository.countByItemIdAndItemTypeAndLiked(itemId, "recipe", 1);

            recipe.setLikesdNum(likesCount.intValue());
        }

        recipeRepository.saveAll(recipes);
    }

//    // "honor" 레시피 조회
//    public List<RecipeListResponse> getHonorRecipes() {
//        return recipeRepository.findHonorRecipes().stream()
//                .map(recipe -> new RecipeListResponse(
//                        recipe.getRecipeNum(),
//                        recipe.getRecipeName(),
//                        recipe.getCategory(),
//                        recipe.getIntro(),
//                        recipe.getMainImage(),
//                        recipe.getUser().getNickname(),
//                        recipe.getToday()
//                ))
//                .collect(Collectors.toList());
//    }
    //honor 레시피
    public List<RecipeListResponse> gethonorRecipes() {
        return getRecipesByType("honor");
    }
}