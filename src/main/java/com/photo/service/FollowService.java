package com.photo.service;

import com.photo.domain.follow.FollowRepository;
import com.photo.handler.exception.CustomApiException;
import com.photo.web.dto.follow.FollowInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;

    @PersistenceContext
    EntityManager em;

    @Transactional(readOnly = true)
    public List<FollowInfoDto> followInfoList(Long sessionId, Long pageUserId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profile_image_url, ");
        sb.append("if((SELECT 1 FROM follow WHERE from_user_id=? AND to_user_id= u.id), 1, 0) followState, ");
        sb.append("if((?=u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN follow f ");
        sb.append("ON u.id = f.to_user_id ");
        sb.append("WHERE f.from_user_id=?");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, sessionId)
                .setParameter(2, sessionId)
                .setParameter(3, pageUserId);

        List<Object[]> results = query.getResultList();
        List<FollowInfoDto> followInfoDtos = results.stream()
                .map(o -> new FollowInfoDto(o))
                .collect(Collectors.toList());
        return followInfoDtos;
    }

    @Transactional
    public void follow(Long fromUserId, Long toUserId) {
        try{
            followRepository.cFollow(fromUserId,toUserId);
        }catch (Exception e) {
            throw new CustomApiException("이미 팔로우 하고 있는 유저입니다.");
        }
    }

    @Transactional
    public void unFollow(Long fromUserId, Long toUserId) {
        followRepository.cUnFollow(fromUserId,toUserId);
    }
}
