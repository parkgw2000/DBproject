package com.example.dbproject.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RecipeDetailResponse {
    private Long recipeNum;
    private String recipeName;
    private String category;
    private String material;
    private String count;
    private String intro;
    private String tool;
    private byte[] mainImage;
    private String userName;
    private Integer likesdNum;
    private LocalDateTime createdDate;
    private List<CookStepResponse> cookSteps;

    public static RecipeDetailResponse from(Recipe recipe, List<Cook> cookSteps) {
        return RecipeDetailResponse.builder()
                .recipeNum(recipe.getRecipeNum())
                .recipeName(recipe.getRecipeName())
                .category(recipe.getCategory())
                .material(recipe.getMaterial())
                .count(recipe.getCount())
                .intro(recipe.getIntro())
                .tool(recipe.getTool())
                .mainImage(recipe.getMainImage())
                .userName(recipe.getUser().getNickname())
                .likesdNum(recipe.getLikesdNum())
                .cookSteps(cookSteps.stream()
                        .map(CookStepResponse::from)
                        .toList())
                .build();
    }
}
