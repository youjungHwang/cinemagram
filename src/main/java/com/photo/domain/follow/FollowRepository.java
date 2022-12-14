package com.photo.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Integer> {

    @Modifying
    @Query(value = "INSERT INTO follow(fromUserId,toUserId,createdDate) VALUES(:fromUserId,:toUserId,now())", nativeQuery = true)
    void cFollow(@Param(value = "fromUserId") Integer fromUserId, @Param(value = "toUserId") Integer toUserId);

    @Modifying
    @Query(value = "DELETE FROM follow WHERE fromUserId =:fromUserId AND toUserId =:toUserId", nativeQuery = true)
    void cUnFollow(@Param(value = "fromUserId") Integer fromUserId, @Param(value = "toUserId") Integer toUserId);

    @Query(value = "SELECT COUNT(*) FROM follow WHERE fromUserId = :sessionId AND toUserId = :pageUserId", nativeQuery = true)
    int cFollowState(@Param(value = "sessionId") int sessionId, @Param(value = "pageUserId") int pageUserId);

    @Query(value = "SELECT COUNT(*) FROM follow WHERE fromUserId = :pageUserId", nativeQuery = true)
    int cFollowCount(@Param(value = "pageUserId") int pageUserId);

}