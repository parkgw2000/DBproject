package com.example.dbproject.Controller;

import com.example.dbproject.Entity.History;
import com.example.dbproject.Entity.HistoryRequest;
import com.example.dbproject.Entity.*;
import com.example.dbproject.Entity.RecipeListResponse;
import com.example.dbproject.Repository.HistoryRepository;
import com.example.dbproject.Repository.RecipeRepository;
import com.example.dbproject.Repository.UserRepository;
import com.example.dbproject.Service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @PostMapping(path = "/addhistory")
    public ResponseEntity<History> addHistory(@RequestBody HistoryRequest historyRequest) {
        // 받아온 usernum, recipenum, openday 값을 사용하여 히스토리 저장
        Long usernum = historyRequest.getUsernum();
        Long recipenum = historyRequest.getRecipenum();
        LocalDate openday = historyRequest.getOpenday();

        // 히스토리 저장 (서비스에서 저장)
        History history = historyService.addHistory(usernum, recipenum, openday);

        // 확인용 로그
        log.info("History added for user {}: Recipe {} on {}", usernum, recipenum, openday);

        // 성공적으로 저장된 히스토리 반환
        return ResponseEntity.ok(history);
    }

    @GetMapping(path = "/gethistory")
    public ResponseEntity<List<RecipeListResponse>> getHistory(@RequestParam Long usernum) {
        try {
            List<RecipeListResponse> historyData = historyService.getHistory(usernum);
            log.info("History Data for user {}: {}", usernum, historyData);
            return ResponseEntity.ok(historyData);
        } catch (Exception e) {
            log.error("Error searching for recipes: {}", e.getMessage());
            return ResponseEntity.status(500).body(Collections.emptyList()); // 빈 리스트 반환
        }
    }

}
