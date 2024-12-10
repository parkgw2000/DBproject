package com.example.dbproject.Repository;

import com.example.dbproject.Entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    // 특정 레시피에 대한 댓글 리스트를 조회
    List<Comments> findByRecipenum_RecipeNum(Long recipeNum);

    // 특정 유저가 작성한 댓글을 조회
    List<Comments> findByUser_Usernum(Long usernum);
}
