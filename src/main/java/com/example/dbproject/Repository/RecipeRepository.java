package com.example.dbproject.Repository;

import com.example.dbproject.Entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findById(Long recipeId);


    /**
     * 베스트 레시피 조회
     * 작성일 기준 30일 이내의 레시피 중 추천수 상위 10개 조회
     */
    @Query(value =
        "SELECT * FROM (" +
                "SELECT a.* FROM recipe a "+
                "WHERE a.today >= SYSDATE - 30"+
                "ORDER BY a.likesd_num DESC" +
                ") WHERE ROWNUM <= 10",
            nativeQuery = true)
    List<Recipe> findBestRecipes();

    /**
     * 명예의 전당 레시피 조회
     * 작성일 기준 30일 이후의 레시피 중 추천수 상위 10개 조회
     */
    @Query(value =
            "SELECT * FROM (" +
                    "SELECT a.* FROM recipe a " +
                    "WHERE a.today < SYSDATE - 30" +
                    "ORDER BY a.likesd_num DESC" +
                    ") WHERE ROWNUM <= 10",
            nativeQuery = true)
    List<Recipe> findHonorRecipes();
}

