package com.example.dbproject.Controller;

import com.example.dbproject.Entity.CommentResponse;
import com.example.dbproject.Entity.Comments;
import com.example.dbproject.Entity.User;
import com.example.dbproject.Repository.UserRepository;
import com.example.dbproject.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 특정 레시피에 대한 댓글 목록을 가져오는 API
    @GetMapping("/{recipeNum}/getcomments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long recipeNum) {
        try {
            List<Comments> comments = commentService.getCommentsByRecipeId(recipeNum);

            // 가공
            List<CommentResponse> response = comments.stream()
                    .map(comment -> {
                        User user = comment.getUser();  // 댓글 작성자
                        return new CommentResponse(
                                user.getNickname(),   // 작성자의 닉네임
                                comment.getCommennt(), // 댓글 내용
                                comment.getDates(),    // 댓글 작성 일시
                                comment.getGood()
                        );
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response); // 댓글 목록을 반환
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // 예외 처리 시 500 에러 반환
        }
    }

    // 댓글 작성 API
    @PostMapping("/{recipeNum}/comments")
    public ResponseEntity<Comments> addComment(@PathVariable Long recipeNum,
                                               @RequestBody Map<String, Object> requestBody) {
        try {
            // Map에서 userNum과 comment 추출
            Long userNum = Long.parseLong(requestBody.get("userNum").toString());  // userNum
            String comment = (String) requestBody.get("commentText");  // comment

            // 댓글 추가 서비스 호출
            Comments newComment = commentService.addComment(recipeNum, userNum, comment);
            return ResponseEntity.ok(newComment);  // 댓글 저장 후 반환
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // 오류 발생 시 500 상태 코드 반환
        }
    }


    // 댓글 추천 API
    @PostMapping("/comments/{commentId}/like")
    public Comments likeComment(@PathVariable Long commentId) {
        return commentService.likeComment(commentId);
    }

}
