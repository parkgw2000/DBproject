package com.example.dbproject.Controller;

import com.example.dbproject.Service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    // 좋아요 토글 엔드포인트
    @PostMapping("/toggle")
    public boolean toggleLike(@RequestParam Long userNum,
                              @RequestParam Long itemId,
                              @RequestParam String itemType) {
        return likesService.toggleLike(userNum, itemId, itemType);
    }

    // 특정 아이템의 좋아요 수 가져오기
    @GetMapping("/count")
    public Long getLikeCount(@RequestParam String itemId,
                             @RequestParam String itemType) {
        return likesService.getLikeCount(itemId, itemType);
    }

    // 특정 사용자가 특정 아이템에 좋아요를 눌렀는지 여부 확인
    @GetMapping("/status")
    public boolean checkLikeStatus(@RequestParam Long userNum,
                                   @RequestParam String itemId,
                                   @RequestParam String itemType) {
        return likesService.checkLikeStatus(userNum, itemId, itemType);
    }
}
