package com.photo.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT * FROM image WHERE userId IN (SELECT toUserId FROM follow WHERE fromUserId = :sessionId)", nativeQuery = true)
    Page<Image> cFeed (@Param(value = "sessionId") int sessionId, @Param(value = "pageable") Pageable pageable);

    @Query(value = "SELECT i.* FROM image i INNER JOIN (SELECT imageId, COUNT(imageId) likesCount FROM likes GROUP BY imageId) c ON i.id = c.imageId ORDER BY likesCount DESC", nativeQuery = true)
    List<Image> cPopularImages();

}
