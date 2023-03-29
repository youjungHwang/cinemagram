package com.photo.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "SELECT * FROM image WHERE user_id IN (SELECT to_user_id FROM follow WHERE from_user_id = :sessionId)", nativeQuery = true)
    Page<Image> cFeed (@Param(value = "sessionId") Long sessionId, @Param(value = "pageable") Pageable pageable);

    @Query(value = "SELECT i.* FROM image i INNER JOIN (SELECT image_id, COUNT(image_id) likesCount FROM likes GROUP BY image_id) c ON i.id = c.image_id ORDER BY likesCount DESC", nativeQuery = true)
    List<Image> cPopularImages();

    List<Image> findByUserId(Long userId);
}
