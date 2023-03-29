package com.photo.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "INSERT INTO likes(image_id, user_id, created_date) VALUES(:imageId, :sessionId, now())", nativeQuery = true)
    int cLikes(@Param(value = "imageId") Long imageId, @Param(value = "sessionId") Long sessionId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE image_id =:imageId AND user_id =:sessionId", nativeQuery = true)
    int cUnLikes(@Param(value = "imageId") Long imageId, @Param(value = "sessionId") Long sessionId);


}
