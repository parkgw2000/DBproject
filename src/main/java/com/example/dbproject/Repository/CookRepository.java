package com.example.dbproject.Repository;

import com.example.dbproject.Entity.Cook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CookRepository extends JpaRepository<Cook, Long> {
    /**
     * 레시피 번호로 조리과정 목록 조회
     * Recipe 엔티티와의 관계를 통해 조회하며 index 순으로 정렬
     */
    List<Cook> findByRecipe_RecipeNumOrderByIndexAsc(Long recipeNum);
}