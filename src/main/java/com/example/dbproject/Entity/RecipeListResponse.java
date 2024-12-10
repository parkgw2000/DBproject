package com.example.dbproject.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class RecipeListResponse {
    private Long recipeNum;
    private String recipeName;
    private String category;
    private String intro;
    private byte[] mainImage;
    private String userName;
    private LocalDateTime today;

    public static RecipeListResponse from(Recipe recipe) {
        return RecipeListResponse.builder()
                .recipeNum(recipe.getRecipeNum())
                .recipeName(recipe.getRecipeName())
                .category(recipe.getCategory())
                .intro(recipe.getIntro())
                .mainImage(recipe.getMainImage())
                .userName(recipe.getUser().getNickname())
                .today(recipe.getToday())
                .build();
    }
}
