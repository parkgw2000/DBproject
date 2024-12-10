package com.example.dbproject.Controller;

import com.example.dbproject.Entity.RecipeListResponse; // RecipeListResponse를 import
import com.example.dbproject.Service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class SearchController {

    @Autowired
    private SearchService searchService;

    // 검색 API
    @GetMapping("/recipes/search")
    public ResponseEntity<List<RecipeListResponse>> searchRecipes(
            @RequestParam String query,
            @RequestParam String classification) {
        try {
            // SearchService에서 RecipeListResponse 타입을 반환하도록 수정
            List<RecipeListResponse> recipes = searchService.searchRecipes(query, classification);
            if (recipes.isEmpty()) {
                log.warn("No recipes found for query: {} and classification: {}", query, classification);
                // 빈 배열을 반환하면서 200 OK 상태 코드를 반환
                return ResponseEntity.ok(recipes);
            }
            log.info("Search results: {}", recipes);
            return ResponseEntity.ok(recipes);
        } catch (Exception e) {
            log.error("Error searching for recipes: {}", e.getMessage());
            return ResponseEntity.status(500).body(null); // 서버 에러 발생 시 500 상태 반환
        }
    }
}
