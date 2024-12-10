package com.example.dbproject.Service;

import com.example.dbproject.Entity.*;
import com.example.dbproject.Repository.HistoryRepository;
import com.example.dbproject.Repository.*;
import com.example.dbproject.Repository.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;

    // 히스토리를 저장하는 메서드
    public History addHistory(Long usernum, Long recipenum, LocalDate openday) {
        // 오늘 날짜를 자동으로 설정
        LocalDate today = LocalDate.now();

        // Recipe와 User 객체 조회
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipenum);
        Optional<User> userOpt = userRepository.findById(usernum);

        if (recipeOpt.isPresent() && userOpt.isPresent()) {
            // 먼저 중복된 히스토리 삭제
            historyRepository.deleteByUserUsernumAndRecipenumRecipeNum(usernum, recipenum);

            History history = new History();
            history.setUser(userOpt.get());  // User 객체 설정
            history.setRecipenum(recipeOpt.get());  // Recipe 객체 설정
            history.setOpenday(openday);  // 전달받은 openday
            history.setToday(today);  // 오늘 날짜

            return historyRepository.save(history);  // 히스토리 저장
        } else {
            throw new RuntimeException("User or Recipe not found");
        }
    }

    // 사용자별 히스토리 조회
    public List<RecipeListResponse> getHistory(Long usernum) {
        // usernum을 기반으로 히스토리 데이터 조회
        List<History> historyList = historyRepository.findByUser_usernum(usernum);

        // 각 히스토리에서 레시피 정보를 추출하여 RecipeListResponse 객체로 변환하고 반환
        return historyList.stream()
                .map(history -> {
                    // history에서 recipenum을 가져와서 해당 Recipe 객체를 조회
                    Recipe recipe = history.getRecipenum();  // 'recipenum' 사용
                    return RecipeListResponse.builder()
                            .recipeNum(recipe.getRecipeNum())      // 레시피 번호
                            .recipeName(recipe.getRecipeName())     // 레시피 이름
                            .category(recipe.getCategory())         // 카테고리
                            .intro(recipe.getIntro())               // 소개
                            .mainImage(recipe.getMainImage())       // 메인 이미지
                            .userName(recipe.getUser().getNickname()) // 사용자 닉네임
                            .today(recipe.getToday())               // 오늘 날짜 (Recipe 객체에 today 필드가 있다면)
                            .build();
                })
                .filter(Objects::nonNull) // null을 제외하고 필터링
                .collect(Collectors.toList());  // RecipeListResponse 객체 목록 반환
    }
}
