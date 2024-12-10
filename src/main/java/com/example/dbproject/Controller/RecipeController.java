package com.example.dbproject.Controller;

import com.example.dbproject.Entity.*;
import com.example.dbproject.Repository.UserRepository;
import com.example.dbproject.Service.FileService;
import com.example.dbproject.Service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final FileService fileService;
    private final UserRepository userRepository;

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> createRecipe(
            @RequestPart(value = "main_image") MultipartFile mainImage,
            @RequestParam(value = "category_code") String categoryCode,
            @RequestParam("recipe_name") String recipeName,
            @RequestParam("user_num") Long userNum,
            @RequestParam("count") String count,
            @RequestParam("material") String material,
            @RequestParam("tool") String tool,
            @RequestParam("intro") String intro,
            @RequestParam(value = "type",required = false) String type,
            MultipartHttpServletRequest request
    ) throws IOException {
        log.info("=== 전송된 데이터 확인 시작 ===");
        log.info("폼 데이터:");
        log.info("레시피 이름: {}", recipeName);
        log.info("재료: {}", material);
        log.info("분량: {}", count);
        log.info("소개: {}", intro);
        log.info("도구: {}", tool);
        log.info("유저 번호: {}", userNum);
        log.info("메인 이미지 이름: {}", mainImage.getOriginalFilename());
        log.info("메인 이미지 크기: {} bytes", mainImage.getSize());
        log.info("카테고리: {}", categoryCode);

        String savedMainImage = fileService.saveFile(mainImage);

        Recipe recipe = new Recipe();
        recipe.setRecipeName(recipeName);
        recipe.setMaterial(material);
        recipe.setCount(count);
        recipe.setIntro(intro);
        recipe.setTool(tool);
        recipe.setMainImage(fileService.convertToByte(mainImage));
        recipe.setType(type);
        recipe.setToday(LocalDateTime.now());
        recipe.setCategory(categoryCode);
        User user = userRepository.findById(userNum).get();
        recipe.setUser(user);

        List<Cook> cookSteps = new ArrayList<>();
        int stepCount = 0;

        for (int i = 0; i < 10; i++) {
            String stepOrderKey = "steps[" + i + "].step_order";
            String detailsKey = "steps[" + i + "].details";
            String imageKey = "steps[" + i + "].image";

            String stepOrder = request.getParameter(stepOrderKey);
            if (stepOrder != null) {
                MultipartFile stepImage = request.getFile(imageKey);
                String details = request.getParameter(detailsKey);
                log.info("- Step {} -", (i + 1));
                log.info("순서 번호: {}", stepOrder);
                log.info("설명: {}", request.getParameter(detailsKey));
                if (stepImage != null) {
                    log.info("이미지 이름: {}", stepImage.getOriginalFilename());
                    log.info("이미지 크기: {} bytes", stepImage.getSize());
                }

                String savedFileName = fileService.saveFile(stepImage);
                Cook cook = new Cook();
                cook.setFilename(fileService.convertToByte(stepImage));
                cook.setDetails(details);
                cook.setIndex(Integer.parseInt(stepOrder));
                cookSteps.add(cook);

                stepCount++;
                if (stepCount >= 10) {
                    break;
                }
            }
        }

        Recipe savedRecipe = recipeService.createRecipe(recipe, cookSteps);
        return ResponseEntity.ok(savedRecipe);
    }

    @GetMapping("/Allboard")
    public ResponseEntity<List<RecipeListResponse>> getAllRecipes() {
        log.info("getAllRecipes called");
        List<RecipeListResponse> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/recipes/{recipeNum}")
    public ResponseEntity<RecipeDetailResponse> getRecipe(@PathVariable Long recipeNum) {
        return ResponseEntity.ok(recipeService.getRecipeWithDetails(recipeNum));
    }

    @GetMapping("/special/{type}")
    public ResponseEntity<List<RecipeListResponse>> getSpecialRecipes(@PathVariable String type) {
        return ResponseEntity.ok(recipeService.getRecipesByType(type));
    }
    @PutMapping("/update-likes")
    public ResponseEntity<Void> updateLikesCount() {
        recipeService.updateRecipeLikes();
        return ResponseEntity.ok().build();
    }

    /**
     * 베스트 레시피 조회
     * 작성일 기준 30일 이내의 레시피 중 추천수 상위 10개 조회
     */
    @GetMapping("/best")
    public ResponseEntity<List<RecipeListResponse>> getBestRecipes() {
        log.info("getBestRecipes called");
        List<RecipeListResponse> recipes = recipeService.getBestRecipes();
        return ResponseEntity.ok(recipes);
    }
//    @GetMapping("/best")
//    public ResponseEntity<List<Recipe>> getBestRecipes() {
//        log.info("getBestRecipes called");
//        return ResponseEntity.ok(recipeService.getBestRecipes());
//    }

    /**
     * 명예의 전당 레시피 조회
     * 작성일 기준 30일 이후의 레시피 중 추천수 상위 10개 조회
     */
    @GetMapping("/honor")
    public ResponseEntity<List<RecipeListResponse>> getHonorRecipes() {
        log.info("gethonorRecipes called");
        List<RecipeListResponse> recipes = recipeService.gethonorRecipes();
        return ResponseEntity.ok(recipes);
    }


}
