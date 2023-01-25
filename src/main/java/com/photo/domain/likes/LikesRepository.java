package com.photo.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Integer> {

    @Modifying
    @Query(value = "INSERT INTO likes(imageId, userId, createdDate) VALUES(:imageId, :sessionId, now())", nativeQuery = true)
    int cLikes(@Param(value = "imageId") int imageId, @Param(value = "sessionId") int sessionId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE imageId =:imageId AND userId =:sessionId", nativeQuery = true)
    int cUnLikes(@Param(value = "imageId") int imageId, @Param(value = "sessionId") int sessionId);


}
