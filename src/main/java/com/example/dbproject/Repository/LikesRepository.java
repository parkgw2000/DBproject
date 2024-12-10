package com.example.dbproject.Repository;

import com.example.dbproject.Entity.Likes;
import com.example.dbproject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByUserAndItemIdAndItemType(User user, String itemId, String itemType);
    Long countByItemIdAndItemType(String itemId, String itemType);

    Long countByItemIdAndItemTypeAndLiked(String itemId, String itemType, int liked);

}
