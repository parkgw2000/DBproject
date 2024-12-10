package com.example.dbproject.Service;

import com.example.dbproject.Entity.Likes;
import com.example.dbproject.Entity.Recipe;
import com.example.dbproject.Entity.User;
import com.example.dbproject.Repository.LikesRepository;
import com.example.dbproject.Repository.RecipeRepository;
import com.example.dbproject.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    // private final CommentRepository commentRepository;

    public boolean toggleLike(Long userNum, Long itemId, String itemType) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Likes> existingLike = likesRepository.findByUserAndItemIdAndItemType(user, itemId.toString(), itemType);

        if (existingLike.isPresent()) {
            likesRepository.delete(existingLike.get());
            updateLikesCount(itemId, itemType);
            return false;
        } else {
            Likes newLike = new Likes();
            newLike.setUser(user);
            newLike.setItemId(itemId.toString());
            newLike.setItemType(itemType);
            newLike.setLiked(1);
            likesRepository.save(newLike);
            updateLikesCount(itemId, itemType);
            return true;
        }
    }

    public Long getLikeCount(String itemId, String itemType) {
        return likesRepository.countByItemIdAndItemTypeAndLiked(itemId, itemType, 1);
    }

    public boolean checkLikeStatus(Long userNum, String itemId, String itemType) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return likesRepository.findByUserAndItemIdAndItemType(user, itemId, itemType)
                .isPresent();
    }

    private void updateLikesCount(Long itemId, String itemType) {
        if (itemType.equals("recipe")) {
            Recipe recipe = recipeRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));
            Long likesCount = getLikeCount(itemId.toString(), itemType);
            recipe.setLikesdNum(likesCount.intValue());
            recipeRepository.save(recipe);
        } else if (itemType.equals("comment")) {
//            Comment comment = commentRepository.findById(itemId)
//                    .orElseThrow(() -> new RuntimeException("Comment not found"));
//            Long likesCount = getLikeCount(itemId, itemType);
//            comment.setLikedNum(likesCount.intValue());
//            commentRepository.save(comment);
        }
    }
}