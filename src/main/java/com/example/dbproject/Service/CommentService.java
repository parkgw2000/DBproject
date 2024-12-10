package com.example.dbproject.Service;

import com.example.dbproject.Entity.Comments;
import com.example.dbproject.Entity.Recipe;
import com.example.dbproject.Entity.User;
import com.example.dbproject.Repository.CommentRepository;
import com.example.dbproject.Repository.RecipeRepository;
import com.example.dbproject.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    // 레시피에 달린 댓글 목록을 가져오는 메서드
    public List<Comments> getCommentsByRecipeId(Long recipeId) {
        return commentRepository.findByRecipenum_RecipeNum(recipeId); // 레시피 ID로 댓글 조회
    }

    // 댓글 작성 메서드
    public Comments addComment(Long recipeId, Long userNum, String comment) {
        // 레시피 객체와 사용자 객체를 해당 ID로 조회합니다.
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 새로운 댓글 객체 생성
        Comments newComment = new Comments();
        newComment.setRecipenum(recipe); // 레시피 객체 설정
        newComment.setUser(user); // 사용자 설정
        newComment.setCommennt(comment); // 댓글 내용 설정
        newComment.setGood(0L); // 초기 추천수는 0
        newComment.setDates(LocalDateTime.now());
        log.info("댓글 저장 완료 - 레시피번호 : {} 유저번호 : {} 작성일 : {} 댓글내용 :{}",
                recipe.getRecipeNum(),   // 레시피 번호
                user.getUser_num(),      // 유저 번호
                newComment.getDates(),  // 댓글 작성일
                newComment.getCommennt()); // 댓글 내용
        // 댓글 저장
        return commentRepository.save(newComment);
    }

    // 댓글의 추천수 증가 메서드
    public Comments likeComment(Long commentId) {
        Comments comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setGood(comment.getGood() + 1);
        return commentRepository.save(comment);
    }
}
