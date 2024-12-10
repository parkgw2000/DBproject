package com.example.dbproject.Repository;

import com.example.dbproject.Entity.Recipe;
import com.example.dbproject.Entity.History;
import com.example.dbproject.Entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    // usernum으로 History를 조회
    List<History> findByUser_usernum(Long usernum);
    List<History> findByRecipenum(Recipe recipenum);

    @Modifying
    @Query(value = "DELETE FROM history WHERE USER_NUM = :usernum AND RECIPE_NUM = :recipenum", nativeQuery = true)
    void deleteByUserUsernumAndRecipenumRecipeNum(Long usernum, Long recipenum);

}
