package com.photo.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Modifying
    @Query(value = "INSERT INTO follow(from_user_id,to_user_id,created_date) VALUES(:fromUserId,:toUserId,now())", nativeQuery = true)
    void cFollow(@Param(value = "fromUserId") Long fromUserId, @Param(value = "toUserId") Long toUserId);

    @Modifying
    @Query(value = "DELETE FROM follow WHERE from_user_id =:fromUserId AND to_user_id =:toUserId", nativeQuery = true)
    void cUnFollow(@Param(value = "fromUserId") Long fromUserId, @Param(value = "toUserId") Long toUserId);

    @Query(value = "SELECT COUNT(*) FROM follow WHERE from_user_id = :sessionId AND to_user_id = :pageUserId", nativeQuery = true)
    int cFollowState(@Param(value = "sessionId") Long sessionId, @Param(value = "pageUserId") Long pageUserId);

    @Query(value = "SELECT COUNT(*) FROM follow WHERE from_user_id = :pageUserId", nativeQuery = true)
    int cFollowCount(@Param(value = "pageUserId") Long pageUserId);

}